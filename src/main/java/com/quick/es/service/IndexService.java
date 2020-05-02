package com.quick.es.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.quick.es.entity.Address;
import com.quick.es.entity.Employ;
import com.quick.es.entity.Student;
import com.quick.es.entity.Subject;
import com.quick.es.model.CityGeo;
import com.quick.es.utils.ChineseName;
import com.quick.es.utils.NetChinaCi;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 *
 * @author wangxc
 * @date: 2020/4/20 下午10:37
 *
 */
@Service
@Slf4j
public class IndexService {

	private final static String DEFAULT_INDEX = "student";

	private final static String[] DEFAULT_SUBJECTS = {"语文", "数学", "外语", "生物", "物理", "化学"};

	@Autowired
	private IndexManagerService indexManagerService;

	@Autowired
	private TransportClient transportClient;

	public void sampleSearch() {
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.termQuery("name", "version-test"));
		SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch("sf-test").setTypes("doc")
				.setQuery(boolQueryBuilder).setSize(10);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		SearchHit[] hits = searchResponse.getHits().getHits();
		for (SearchHit hit : hits) {
			System.out.println(hit.getIndex());
			System.out.println(hit.getType());
			System.out.println(hit.getShard());
			System.out.println(hit.docId());
			System.out.println(hit.getSourceAsMap());
		}
	}

	public void batchInsertData() {
		String content = FileUtil
				.readString(new File("/Users/wangxc/Code/Github/elasticsearch-quick/doc/geo/chinaCity.json"),
						Charset.defaultCharset());
		List<CityGeo> cityGeos = JSONUtil.toList(JSONUtil.parseArray(content), CityGeo.class);
		List<Student> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Student student = new Student();
			student.setUid(UUID.fastUUID().toString());
			student.setUserName(ChineseName.generateName());
			student.setAge(RandomUtil.randomInt(15, 25));
			student.setBirthday(DateUtil.format(RandomUtil
					.randomDate(DateUtil.parse("1995-01-01 19:41:17", NORM_DATETIME_PATTERN), DateField.MINUTE, 0, 13104000),NORM_DATETIME_PATTERN));

			List<File> files = FileUtil.loopFiles("/Users/wangxc/Code/Github/elasticsearch-quick/doc/cis/");
			student.setContent(FileUtil.readString(RandomUtil.randomEle(files),Charset.defaultCharset()));
			Address address = new Address();
			CityGeo cityGeo = RandomUtil.randomEle(cityGeos);
			address.setProvince(cityGeo.getName());
			CityGeo.ChildrenBean childrenBean = RandomUtil.randomEle(cityGeo.getChildren());
			address.setCity(childrenBean.getName());
			address.setLocation(new Address.Location(childrenBean.getLat(),childrenBean.getLog()));
			student.setAddress(address);

			int total = RandomUtil.randomInt(1, DEFAULT_SUBJECTS.length);
			List<Subject> subjects = new ArrayList<>();
			for (int j = 0; j < total; j++) {
				Subject subject = new Subject();
				subject.setName(RandomUtil.randomEle(DEFAULT_SUBJECTS));
				subject.setScore(RandomUtil.randomDouble(100));
				subjects.add(subject);
			}
			student.setSubjects(subjects);
			student.setTotalScore(subjects.stream().mapToDouble(Subject::getScore).sum());
			list.add(student);
		}
		indexManagerService.bucketInsert(DEFAULT_INDEX, list);
		list.clear();
	}


	public void createIndex() {
		String settingStr =
				"{\n" + "  \"index\": {\n" + "    \"number_of_shards\": 3,\n" + "    \"number_of_replicas\": 1\n"
						+ "  }\n" + "}";
		CreateIndexRequestBuilder createIndexRequestBuilder = transportClient.admin().indices().prepareCreate(DEFAULT_INDEX)
				.setSettings(settingStr, XContentType.JSON);
		log.info("create index DSL: \n {}", createIndexRequestBuilder.toString());
		CreateIndexResponse createIndexResponse = createIndexRequestBuilder.execute().actionGet();
		log.info("createIndexResponse.isAcknowledged() {}", createIndexResponse.isAcknowledged());
	}

	public void createMapping() {
		//language=JSON
		String mappingStr =
				"{\n" + "  \"properties\": {\n" + "    \"uid\": {\n" + "      \"type\": \"keyword\"\n" + "    },\n"
						+ "    \"userName\":{\n" + "      \"type\": \"keyword\"\n" + "    },\n" + "    \"age\": {\n"
						+ "      \"type\": \"integer\"\n" + "    },\n" + "    \"birthday\": {\n"
						+ "      \"type\": \"date\",\n" + "      \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" + "    },\n"
						+ "    \"content\": {\n" + "      \"type\": \"text\"\n" + "    },\n" + "    \"address\": {\n"
						+ "      \"properties\": {\n" + "        \"provice\": {\n" + "          \"type\": \"keyword\"\n"
						+ "        },\n" + "        \"city\": {\n" + "          \"type\": \"keyword\"\n"
						+ "        },\n" + "        \"location\": {\n" + "          \"type\": \"geo_point\"\n"
						+ "        }\n" + "      }\n" + "    },\n" + "    \"subjects\": {\n"
						+ "      \"type\": \"nested\"\n" + "    },\n" + "    \"totalScore\": {\n"
						+ "      \"type\": \"double\"\n" + "    }\n" + "  }\n" + "}";
		PutMappingRequestBuilder putMappingRequestBuilder = transportClient.admin().indices()
				.preparePutMapping(DEFAULT_INDEX).setType("doc").setSource(mappingStr, XContentType.JSON);
		log.info("create Mapping DSL \n {}", putMappingRequestBuilder.toString());
		AcknowledgedResponse response = putMappingRequestBuilder.execute().actionGet();
		log.info("createMapping resposne : {} ", response.isAcknowledged());
	}

	public void getMapping() {
		GetMappingsResponse studentMapping = transportClient.admin().indices().prepareGetMappings(DEFAULT_INDEX).execute()
				.actionGet();
		log.info("get Mapping: \n {}", studentMapping.toString());

	}

	public void deleteIndex() {
		AcknowledgedResponse response = transportClient.admin().indices().prepareDelete(DEFAULT_INDEX).execute()
				.actionGet();
		log.info("delete index : {}", response.isAcknowledged());

	}
}
