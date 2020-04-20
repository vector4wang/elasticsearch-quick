package com.quick.es.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.quick.es.config.IndexCommon;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.rollover.RolloverRequest;
import org.elasticsearch.action.admin.indices.rollover.RolloverResponse;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesRequest;
import org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse;
import org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author wangxc
 * @date: 2020/3/22 下午5:38
 *
 */
@Service
@Slf4j
public class IndexManagerService {

	@Autowired
	private TransportClient transportClient;

	public boolean createWriteIndexAlias(String indexPrefix) {
		String physicalIndex = IndexCommon.getCurrentFormatIndex(indexPrefix);
		CreateIndexRequest request = new CreateIndexRequest(physicalIndex);
		String writeIndex = IndexCommon.getWriteIndex(indexPrefix);
		;
		request.alias(new Alias(writeIndex));
		CreateIndexResponse response = transportClient.admin().indices().create(request).actionGet();
		log.info(response.toString());
		return response.isAcknowledged();
	}

	public boolean createIndexTemplate(String indexPrefix) throws IOException {
		String indexTemplate = IndexCommon.getIndexTemplate(indexPrefix);
		String indexPattern = IndexCommon.getIndexPattern(indexPrefix);
		String searchIndex = IndexCommon.getSearchIndex(indexPrefix);
		PutIndexTemplateRequest request = new PutIndexTemplateRequest(indexTemplate);
		request.patterns(new ArrayList<String>() {{add(indexPattern);}});
		request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 1));
		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("_source")
				.field("enabled", true).endObject().startObject("properties").startObject("uid").field("type", "keyword")
				.endObject().startObject("nick").field("type", "keyword").endObject().startObject("chatTime")
				.field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss").endObject().endObject().endObject();
		request.mapping("doc", builder).alias(new Alias(searchIndex));
		AcknowledgedResponse response = transportClient.admin().indices().putTemplate(request).actionGet();
		return response.isAcknowledged();
	}

	public boolean deleteIndexTemplate(String indexPrefix) {
		String indexTemplate = IndexCommon.getIndexTemplate(indexPrefix);
		;//xxx-template
		DeleteIndexTemplateRequest reqeust = new DeleteIndexTemplateRequest(indexTemplate);
		AcknowledgedResponse response = transportClient.admin().indices().deleteTemplate(reqeust).actionGet();
		return response.isAcknowledged();
	}

	public boolean isExistIndexTemplate(String indexPrefix) {
		String indexTemplate = IndexCommon.getIndexTemplate(indexPrefix);
		;//xxx-template
		GetIndexTemplatesRequest request = new GetIndexTemplatesRequest(indexTemplate);
		GetIndexTemplatesResponse response = transportClient.admin().indices().getTemplates(request).actionGet();
		List<IndexTemplateMetaData> indexTemplates = response.getIndexTemplates();
		if (CollectionUtil.isEmpty(indexTemplates)) {
			log.info("not exist " + indexTemplate + "!");
			return false;
		}
		return true;


	}

	public boolean rollover(String indexPrefix) {
		String newPhysicalIndex = IndexCommon.getCurrentFormatIndex(indexPrefix);
		RolloverRequest request = new RolloverRequest(IndexCommon.getWriteIndex(indexPrefix), newPhysicalIndex);
		request.addMaxIndexAgeCondition(new TimeValue(10, TimeUnit.SECONDS));// 前一个索引只要超过10min即可rollover
		boolean rolledOver = true;
		do {
			RolloverResponse response = transportClient.admin().indices().rolloversIndex(request).actionGet();
			log.info("rollover.....");
			rolledOver = response.isRolledOver();
		} while (!rolledOver);
		log.info(newPhysicalIndex + " rollover ok");
		return true;
	}

	public <T> void bucketInsert(String writeIndex, List<T> list) {
		BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();

		for (T data : list) {
			bulkRequestBuilder.add(transportClient.prepareIndex(writeIndex, "doc").setSource(JSONUtil.toJsonStr(data),
					XContentType.JSON));
		}

		BulkResponse bulkResponse = bulkRequestBuilder.get();
		if (bulkResponse.hasFailures()) {
			log.error("bulk insert err, {}",bulkResponse.buildFailureMessage());
		}

	}

}
