package com.quick.es.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quick.es.entity.Student;
import com.quick.es.mapper.StudentMapper;
import com.quick.es.model.EsStudent;
import com.quick.es.service.IStudentService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.core.search.aggregation.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.index.engine.Engine;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vector4wang
 * @since 2020-01-06
 */
@Service
//@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	@Resource
	private JestClient jestClient;

	@Override
	public void createIndex() {
		CreateIndex createIndex = new CreateIndex.Builder(Student.INDEX_NAME).build();
		try {
			JestResult jestResult = jestClient.execute(createIndex);
			logger.info("create index resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			logger.error("create index fail. msg:{}", e.getMessage());
		}
	}

	@Override
	public void createIndexMapping() {
		/**
		 * 这里可以自己写个json处理
		 */
		String source = "{\"" + Student.TYPE_NAME
				+ "\":{\"properties\":{\"age\":{\"type\":\"short\"},\"courseScores\":{\"properties\":{\"className\":{\"type\":\"keyword\"},\"score\":{\"type\":\"double\"}},\"type\":\"nested\"},\"id\":{\"type\":\"integer\"},\"name\":{\"type\":\"keyword\"}}}}";
		logger.info("source:{}", source);
		PutMapping putMapping = new PutMapping.Builder(Student.INDEX_NAME, Student.TYPE_NAME, source).build();
		try {
			JestResult jestResult = jestClient.execute(putMapping);
			logger.info("createIndexMapping resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			logger.error("createIndexMapping fail. msg:{}", e.getMessage());
		}
	}

	@Override
	public void bulkInsert() {
		List<EsStudent> studentList = getStudentScore();
		List<Index> indices = studentList.stream()
				.map(item -> new Index.Builder(item).id(UUID.randomUUID().toString()).build())
				.collect(Collectors.toList());
		Bulk bulk = new Bulk.Builder().defaultIndex(Student.INDEX_NAME).defaultType(Student.TYPE_NAME)
				.addAction(indices).build();
		try {
			BulkResult bulkResult = jestClient.execute(bulk);
			logger.info("bulkInsert resp: {}", bulkResult.getJsonString());
		} catch (IOException e) {
			logger.error("bulkInsert fail. msg:{}", e.getMessage());
		}

	}

	@Override
	public void truncateIndex() {
		;
		DeleteByQuery build = new DeleteByQuery.Builder(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()).toString())
				.addIndex(Student.INDEX_NAME).addType(Student.TYPE_NAME).build();
		try {
			JestResult jestResult = jestClient.execute(build);
			logger.info("truncateIndex resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			logger.error("truncateIndex fail. msg:{}", e.getMessage());
		}
	}

	/**
	 * {
	 *   "query": {
	 *     "match_all": {}
	 *   },
	 *   "size": 0,
	 *   "aggs": {
	 *     "avg_age": {
	 *       "avg": {
	 *         "field": "age"
	 *       }
	 *     }
	 *   }
	 * }
	 *
	 * 等价于 SELECT AVG(age) FROM student
	 * @return
	 * @throws IOException
	 */
	@Override
	public Double avgAge() throws IOException {
		String query = "{\"query\":{\"match_all\":{}},\"size\":0,\"aggs\":{\"avg_age\":{\"avg\":{\"field\":\"age\"}}}}";
		Search search = new Search.Builder(query)
				.addIndex(Student.INDEX_NAME)
				.addType(Student.TYPE_NAME)
				.build();
		SearchResult result = jestClient.execute(search);
		AvgAggregation average = result.getAggregations().getAvgAggregation("avg_age");
		return average.getAvg();
	}

	/**
	 * {
	 *   "query": {
	 *     "match_all": {}
	 *   },
	 *   "size": 0,
	 *   "aggs": {
	 *     "total": {
	 *       "terms": {
	 *         "field": "id",
	 *         "order": {
	 *           "top>sumAgg": "desc"
	 *         }
	 *       },
	 *       "aggs": {
	 *         "top": {
	 *           "nested": {
	 *             "path": "courseScores"
	 *           },
	 *           "aggs": {
	 *             "sumAgg": {
	 *               "sum": {
	 *                 "field": "courseScores.score"
	 *               }
	 *             }
	 *           }
	 *         }
	 *       }
	 *     }
	 *   }
	 * }
	 * 等效于SELECT s.id,sum(cs.score) as total FROM student s LEFT JOIN course_score cs 	on s.id =	 cs.stu_id GROUP BY s.Id ORDER BY total DESC
	 */
	@Override
	public void getTotalTopN() throws IOException {
		String query = "{\"query\":{\"match_all\":{}},\"size\":0,\"aggs\":{\"total\":{\"terms\":{\"field\":\"id\","
				+ "\"order\":{\"top>sumAgg\":\"desc\"}},\"aggs\":{\"top\":{\"nested\":{\"path\":\"courseScores\"},"
				+ "\"aggs\":{\"sumAgg\":{\"sum\":{\"field\":\"courseScores.score\"}}}}}}}}";
		Search search = new Search.Builder(query)
				.addIndex(Student.INDEX_NAME)
				.addType(Student.TYPE_NAME)
				.build();
		SearchResult result = jestClient.execute(search);
		TermsAggregation termsAggregation = result.getAggregations().getTermsAggregation("total");
		List<TermsAggregation.Entry> buckets = termsAggregation.getBuckets();
		for (TermsAggregation.Entry bucket : buckets) {
			ChildrenAggregation top = bucket.getChildrenAggregation("top");
			SumAggregation sumAgg = top.getSumAggregation("sumAgg");
			Double sum = sumAgg.getSum();
			System.out.println(bucket.getKeyAsString() + ": " + sum);
		}
	}

	@Override
	public void getPerTopClass() {

	}

	private List<EsStudent> getStudentScore() {
		return baseMapper.selectAllStudentScore();
	}
}
