package com.quick.es.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.quick.es.entity.Employ;
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

import java.io.IOException;
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
		List<Employ> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Employ employ = new Employ();
			employ.setName(ChineseName.generateName());
			employ.setAge(RandomUtil.randomInt(100));
			employ.setBirthday(DateUtil.format(RandomUtil
							.randomDate(DateUtil.parse("1990-04-21 19:41:17", NORM_DATETIME_PATTERN), DateField.YEAR, 0, 10),
					NORM_DATETIME_PATTERN));
			employ.setRemark(NetChinaCi.generateChinaCi());
			list.add(employ);
			indexManagerService.bucketInsert("sf-test", list);
			list.clear();
		}
	}


	public void createIndex() {
		String settingStr =
				"{\n" + "  \"index\": {\n" + "    \"number_of_shards\": 3,\n" + "    \"number_of_replicas\": 1\n"
						+ "  }\n" + "}";
		CreateIndexRequestBuilder createIndexRequestBuilder = transportClient.admin()
				.indices().prepareCreate("student")
				.setSettings(settingStr, XContentType.JSON);
		log.info("create index DSL: \n {}", createIndexRequestBuilder.toString());
		CreateIndexResponse createIndexResponse = createIndexRequestBuilder.execute().actionGet();
		log.info("createIndexResponse.isAcknowledged() {}", createIndexResponse.isAcknowledged());
	}

	public void createMapping() {
		//language=JSON
		String mappingStr = "{\n" + "  \"properties\": {\n" + "    \"uid\": {\n" + "      \"type\": \"keyword\"\n" + "    },\n"
				+ "    \"userName\":{\n" + "      \"type\": \"keyword\"\n" + "    },\n" + "    \"age\": {\n" + "      \"type\": \"integer\"\n" + "    },\n"
				+ "    \"birthday\": {\n" + "      \"type\": \"date\",\n"
				+ "      \"format\": \"yyyy-MM-dd HH:mm:ss\"\n" + "    },\n" + "    \"content\": {\n"
				+ "      \"type\": \"text\"\n" + "    },\n" + "    \"address\": {\n" + "      \"properties\": {\n"
				+ "        \"provice\": {\n" + "          \"type\": \"keyword\"\n" + "        },\n"
				+ "        \"city\": {\n" + "          \"type\": \"keyword\"\n" + "        },\n"
				+ "        \"streets\": {\n" + "          \"type\": \"keyword\"\n" + "        },\n"
				+ "        \"location\": {\n" + "          \"type\": \"geo_point\"\n" + "        }\n" + "      }\n"
				+ "    },\n" + "    \"subjects\": {\n" + "      \"type\": \"nested\"\n" + "    }\n" + "  }\n" + "}";
		PutMappingRequestBuilder putMappingRequestBuilder = transportClient.admin().indices()
				.preparePutMapping("student").setType("doc").setSource(mappingStr, XContentType.JSON);
		log.info("create Mapping DSL \n {}",putMappingRequestBuilder.toString());
		AcknowledgedResponse response = putMappingRequestBuilder.execute().actionGet();
		log.info("createMapping resposne : {} ",response.isAcknowledged());
	}

	public void getMapping() {
		GetMappingsResponse studentMapping = transportClient.admin().indices().prepareGetMappings("student").execute()
				.actionGet();
		log.info("get Mapping: \n {}",studentMapping.toString());

	}
}
