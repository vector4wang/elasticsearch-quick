package com.quick.es.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.quick.es.entity.Recipes;
import com.quick.es.service.RecipesService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author vector
 * @Data 2018-8-1 16:21:39
 * @Description jest api 的简单示例
 */
@Service
public class RecipesServiceImpl implements RecipesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecipesService.class);

	@Autowired
	private JestClient jestClient;

	@Override
	public void createIndex() {
		try {
			IndicesExists indicesExists = new IndicesExists.Builder(Recipes.INDEX_NAME).build();
			JestResult existResp = jestClient.execute(indicesExists);
			if (existResp.getResponseCode() == HttpStatus.SC_OK) {
				LOGGER.warn("{} 已存在!", Recipes.INDEX_NAME);
				return;
			}
			CreateIndex createIndex = new CreateIndex.Builder(Recipes.INDEX_NAME).build();
			JestResult jestResult = jestClient.execute(createIndex);
			LOGGER.info("create index resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			LOGGER.error("create index fail. msg:{}", e.getMessage());
		}
	}

	@Override
	public void createIndexMapping() {
		try {
			GetMapping getMapping = new GetMapping.Builder().addIndex(Recipes.INDEX_NAME).build();
			JestResult getResp = jestClient.execute(getMapping);
			JsonObject mappingsObj = getResp.getJsonObject().getAsJsonObject(Recipes.INDEX_NAME)
					.getAsJsonObject("mappings");
			if (mappingsObj.size() > 0) {
				LOGGER.warn("{} 索引已存在mapping,为{}", Recipes.INDEX_NAME, getResp.getJsonString());
				return;
			}
			/**
			 * 这里可以自己写个json处理
			 */
			String source = "{\"properties\":{\"name\":{\"type\":\"text\"},\"rating\":{\"type\":\"float\"},\"type\":{\"type\":\"keyword\"}}}";
			LOGGER.info("source:{}", source);
			// 注意， 此处去掉了type 后面所有用到type的地方也都一并去除
			PutMapping putMapping = new PutMapping.Builder(Recipes.INDEX_NAME, "", source).build();
			JestResult jestResult = jestClient.execute(putMapping);
			LOGGER.info("createIndexMapping resp: {}", jestResult.getJsonString());
		} catch (IOException e) {
			LOGGER.error("createIndexMapping fail. msg:{}", e.getMessage());
		}
	}

	@Override
	public void insert() {
		Recipes recipes0 = new Recipes(0L, "剁椒鱼头", 3, "湘菜");
		Recipes recipes1 = new Recipes(1L, "鲫鱼汤（辣）", 3.5f, "湘菜");
		Recipes recipes2 = new Recipes(2L, "鲫鱼汤（变态辣）", 2, "湘菜");
		Recipes recipes3 = new Recipes(3L, "鱼香肉丝", 4, "川菜");
		Recipes recipes4 = new Recipes(4L, "清蒸鱼头", 3, "湘菜");
		Recipes recipes5 = new Recipes(5L, "奶油鲍鱼汤", 2, "西菜");
		Recipes recipes6 = new Recipes(6L, "红烧鲫鱼", 4.2f, "湘菜");
		Recipes recipes7 = new Recipes(7L, "广式鲫鱼汤", 5, "粤菜");
		Recipes recipes8 = new Recipes(8L, "鲫鱼汤（微辣）", 4, "湘菜");
		Recipes recipes9 = new Recipes(9L, "京酱肉丝", 5, "北方菜");
		Index index0 = new Index.Builder(recipes0).index(Recipes.INDEX_NAME)
				.id(recipes0.getId() + "").build();
		Index index1 = new Index.Builder(recipes1).index(Recipes.INDEX_NAME)
				.id(recipes1.getId() + "").build();
		Index index2 = new Index.Builder(recipes2).index(Recipes.INDEX_NAME)
				.id(recipes2.getId() + "").build();
		Index index3 = new Index.Builder(recipes3).index(Recipes.INDEX_NAME)
				.id(recipes3.getId() + "").build();
		Index index4 = new Index.Builder(recipes4).index(Recipes.INDEX_NAME)
				.id(recipes4.getId() + "").build();
		Index index5 = new Index.Builder(recipes5).index(Recipes.INDEX_NAME)
				.id(recipes5.getId() + "").build();
		Index index6 = new Index.Builder(recipes6).index(Recipes.INDEX_NAME)
				.id(recipes6.getId() + "").build();
		Index index7 = new Index.Builder(recipes7).index(Recipes.INDEX_NAME)
				.id(recipes7.getId() + "").build();
		Index index8 = new Index.Builder(recipes8).index(Recipes.INDEX_NAME)
				.id(recipes8.getId() + "").build();
		Index index9 = new Index.Builder(recipes9).index(Recipes.INDEX_NAME)
				.id(recipes9.getId() + "").build();
		try {
			JestResult jestResult0 = jestClient.execute(index0);
			LOGGER.info("insert:{}", jestResult0.getJsonString());
			JestResult jestResult1 = jestClient.execute(index1);
			LOGGER.info("insert:{}", jestResult1.getJsonString());
			JestResult jestResult2 = jestClient.execute(index2);
			LOGGER.info("insert:{}", jestResult2.getJsonString());
			JestResult jestResult3 = jestClient.execute(index3);
			LOGGER.info("insert:{}", jestResult3.getJsonString());
			JestResult jestResult4 = jestClient.execute(index4);
			LOGGER.info("insert:{}", jestResult4.getJsonString());
			JestResult jestResult5 = jestClient.execute(index5);
			LOGGER.info("insert:{}", jestResult5.getJsonString());
			JestResult jestResult6 = jestClient.execute(index6);
			LOGGER.info("insert:{}", jestResult6.getJsonString());
			JestResult jestResult7 = jestClient.execute(index7);
			LOGGER.info("insert:{}", jestResult7.getJsonString());
			JestResult jestResult8 = jestClient.execute(index8);
			LOGGER.info("insert:{}", jestResult8.getJsonString());
			JestResult jestResult9 = jestClient.execute(index9);
			LOGGER.info("insert:{}", jestResult9.getJsonString());
		} catch (IOException e) {
			LOGGER.error("insert fail. msg:{}", e.getMessage());
		}

	}

	@Override
	public void bulkInsert() {
		Recipes recipes0 = new Recipes(0L, "剁椒鱼头", 3, "湘菜");
		Recipes recipes1 = new Recipes(1L, "鲫鱼汤（辣）", 3.5f, "湘菜");
		Recipes recipes2 = new Recipes(2L, "鲫鱼汤（变态辣）", 2, "湘菜");
		Recipes recipes3 = new Recipes(3L, "鱼香肉丝", 4, "川菜");
		Recipes recipes4 = new Recipes(4L, "清蒸鱼头", 3, "湘菜");
		Recipes recipes5 = new Recipes(5L, "奶油鲍鱼汤", 2, "西菜");
		Recipes recipes6 = new Recipes(6L, "红烧鲫鱼", 4.2f, "湘菜");
		Recipes recipes7 = new Recipes(7L, "广式鲫鱼汤", 5, "粤菜");
		Recipes recipes8 = new Recipes(8L, "鲫鱼汤（微辣）", 4, "湘菜");
		Recipes recipes9 = new Recipes(9L, "京酱肉丝", 5, "北方菜");
		// 注意，这里去掉了type
		Bulk bulk = new Bulk.Builder().defaultIndex(Recipes.INDEX_NAME).addAction(
				Arrays.asList(new Index.Builder(recipes0).id(recipes0.getId() + "").build(),
						new Index.Builder(recipes1).id(recipes1.getId() + "").build(),
						new Index.Builder(recipes2).id(recipes2.getId() + "").build(),
						new Index.Builder(recipes3).id(recipes3.getId() + "").build(),
						new Index.Builder(recipes4).id(recipes4.getId() + "").build(),
						new Index.Builder(recipes5).id(recipes5.getId() + "").build(),
						new Index.Builder(recipes6).id(recipes6.getId() + "").build(),
						new Index.Builder(recipes7).id(recipes7.getId() + "").build(),
						new Index.Builder(recipes8).id(recipes8.getId() + "").build(),
						new Index.Builder(recipes9).id(recipes9.getId() + "").build())).build();
		try {
			BulkResult bulkResult = jestClient.execute(bulk);
			LOGGER.info("bulkInsert resp: {}", bulkResult.getJsonString());
		} catch (IOException e) {
			LOGGER.error("bulkInsert fail. msg:{}", e.getMessage());
		}
	}

	@Override
	public boolean save(Recipes recipes) {
		Index index = new Index.Builder(recipes).index(Recipes.INDEX_NAME).build();
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
		Delete delete = new Delete.Builder(id).index(Recipes.INDEX_NAME).build();
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
	public boolean updateByDocId(Recipes recipes) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("doc", recipes);
		Update update = new Update.Builder(jsonObject.toJSONString()).index(Recipes.INDEX_NAME)
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
	public List<Recipes> search(String content) {
		String searchScript = "{\"query\":{\"match\":{\"name\":{\"query\":\"" + content + "\"}}}}";
		Search search = new Search.Builder(searchScript).addIndex(Recipes.INDEX_NAME)
				.build();
		try {
			JestResult result = jestClient.execute(search);
			return result.getSourceAsObjectList(Recipes.class);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
