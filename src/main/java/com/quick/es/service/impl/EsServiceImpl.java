package com.quick.es.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quick.es.entity.BossJdInfo;
import com.quick.es.entity.JobDetail;
import com.quick.es.mapper.BossJdInfoMapper;
import com.quick.es.service.EsService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.mockito.internal.util.collections.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vector
 * @Data 2018-8-1 16:21:39
 * @Description jest api 的简单示例
 */
@Service
public class EsServiceImpl implements EsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsService.class);

	@Autowired
	private BossJdInfoMapper bossJdInfoMapper;

	@Autowired
	private JestClient jestClient;

	@Override
	public void deleteIndex() {
		DeleteIndex build = new DeleteIndex.Builder(BossJdInfo.INDEX_NAME).build();
		try {
			JestResult jestResult = jestClient.execute(build);
			LOGGER.info("delete index resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			LOGGER.error("delete index fail. msg:{}", e.getMessage());
		}

	}

	@Override
	public void createIndex() {
		CreateIndex createIndex = new CreateIndex.Builder(BossJdInfo.INDEX_NAME).build();
		try {
			JestResult jestResult = jestClient.execute(createIndex);
			LOGGER.info("create index resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			LOGGER.error("create index fail. msg:{}", e.getMessage());
		}
	}

	@Override
	public void createIndexMapping() {
		/**
		 * 这里可以自己写个json处理
		 */
		String source = "{\""+BossJdInfo.TYPE+"\":{\"properties\":{\"id\":{\"type\":\"keyword\"},\"jobName\":{\"type\":\"text\"},\"address\":{\"type\":\"text\"},\"detailAddress\":{\"type\":\"text\"},\"salary\":{\"type\":\"text\"},\"degree\":{\"type\":\"text\"},\"jobDesc\":{\"type\":\"text\"},\"jobHead\":{\"type\":\"text\"},\"hireHead\":{\"type\":\"text\"},\"hireHeadPosition\":{\"type\":\"text\"},\"jobHeadPosition\":{\"type\":\"text\"},\"jobTags\":{\"type\":\"text\"},\"yearOfExpe\":{\"type\":\"text\"},\"teamDesc\":{\"type\":\"text\"},\"teamTags\":{\"type\":\"text\"},\"companyName\":{\"type\":\"text\"},\"companyUrl\":{\"type\":\"text\"},\"financing\":{\"type\":\"text\"},\"industry\":{\"type\":\"text\"},\"scale\":{\"type\":\"text\"},\"companyNature\":{\"type\":\"text\"},\"url\":{\"type\":\"text\"},\"insertTime\":{\"type\":\"date\"},\"publishDate\":{\"type\":\"date\"},\"title4Kibana\":{\"type\":\"keyword\"},\"company4Kibana\":{\"type\":\"keyword\"},\"degree4Kibana\":{\"type\":\"keyword\"},\"companyFinancing4Kibana\":{\"type\":\"keyword\"},\"companyScale4Kibana\":{\"type\":\"keyword\"},\"salary4Kibana\":{\"type\":\"keyword\"}}}}\n";
		LOGGER.info("source:{}", source);
		PutMapping putMapping = new PutMapping.Builder(BossJdInfo.INDEX_NAME, BossJdInfo.TYPE, source).build();
		try {
			JestResult jestResult = jestClient.execute(putMapping);
			LOGGER.info("createIndexMapping resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			LOGGER.error("createIndexMapping fail. msg:{}", e.getMessage());
		}
	}



	@Override
	public void insert() {

	}

	@Override
	public void bulkInsert() {
		List<BossJdInfo> bossJdInfoList = bossJdInfoMapper.selectAll();
		List<Index> indices = new ArrayList<>();
		/**
		 * 注意：测试数据就1w条，所以一次性同步了，如果量很大，可以考虑分组多线程同步
		 */
		for (BossJdInfo bossJdInfo : bossJdInfoList) {
			JobDetail jobDetail = new JobDetail(bossJdInfo);
			indices.add(new Index.Builder(jobDetail).id(jobDetail.getId() + "").build());
		}
		List<List<Index>> lists = CollectionUtils.eagerPartition(indices, 1000);
		for (List<Index> list : lists) {
			Bulk bulk = new Bulk.Builder().defaultIndex(BossJdInfo.INDEX_NAME).defaultType(BossJdInfo.TYPE)
					.addAction(list).build();

			try {
				BulkResult bulkResult = jestClient.execute(bulk);
				LOGGER.info("bulkInsert resp: {}", bulkResult.getJsonString());
			} catch (IOException e) {
				LOGGER.error("bulkInsert fail. msg:{}", e.getMessage());
			}
		}

	}

	@Override
	public boolean save(BossJdInfo recipes) {
		Index index = new Index.Builder(recipes).index(BossJdInfo.INDEX_NAME).type(BossJdInfo.TYPE).build();
		try {
			DocumentResult execute = jestClient.execute(index);
			LOGGER.info("ES 插入完成");
			return execute.isSucceeded();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public boolean deleteByDocId(String id) {
		Delete delete = new Delete.Builder(id).index(BossJdInfo.INDEX_NAME).type(BossJdInfo.TYPE).build();
		DocumentResult dr = null;
		try {
			dr = jestClient.execute(delete);
			LOGGER.info("deleteByDocId resp: {}", dr.getJsonString());
		} catch (IOException e) {
			LOGGER.error("deleteByDocId fail. msg:{}", e.getMessage());
		}
		if (dr.isSucceeded()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateByDocId(BossJdInfo recipes) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("doc", recipes);
		Update update = new Update.Builder(jsonObject.toJSONString()).index(BossJdInfo.INDEX_NAME).type(BossJdInfo.TYPE)
				.id(recipes.getId() + "").build();
		try {
			JestResult result = jestClient.execute(update);
			LOGGER.info(result.getJsonString());
			return result.isSucceeded();
		} catch (IOException e) {
			LOGGER.error("updateByDocId fail. msg:{}", e.getMessage());
		}
		return false;
	}

	@Override
	public List<BossJdInfo> search(String content) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery("name", content));
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(BossJdInfo.INDEX_NAME)
				.addType(BossJdInfo.TYPE).build();
		try {
			JestResult result = jestClient.execute(search);
			return result.getSourceAsObjectList(BossJdInfo.class);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



}
