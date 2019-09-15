package com.quick.es.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.quick.es.entity.ResumeFeatureSimHashIndexModel;
import com.quick.es.mapper.ResumeFeatureSimhashMapper;
import com.quick.es.service.RecipesService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.apache.commons.collections4.ListUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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

    @Autowired
    private ResumeFeatureSimhashMapper resumeFeatureSimhashMapper;

    @Override
    public void createIndex() {
        CreateIndex createIndex = new CreateIndex.Builder(ResumeFeatureSimHashIndexModel.INDEX_NAME).build();
        try {
            JestResult jestResult = jestClient.execute(createIndex);
            LOGGER.info("create index resp: {}", jestResult.getJsonString());
        } catch (IOException e) {
            LOGGER.error("create index fail. msg:{}", e.getMessage());
        }
    }

    @Override
    public void createIndexMapping() {
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("resumeId").field("type", "keyword").endObject()
                    .startObject("pinyinName").field("type", "keyword").endObject()
                    .startObject("realName").field("type", "keyword").endObject()
                    .startObject("lastName").field("type", "keyword").endObject()
                    .startObject("gender").field("type", "integer").endObject()
                    .startObject("age").field("type", "integer").endObject()
                    .startObject("degree").field("type", "integer").endObject()
                    .startObject("school").field("type", "keyword").endObject()
                    .startObject("major").field("type", "keyword").endObject()
                    .startObject("company").field("type", "keyword").endObject()
                    .startObject("title").field("type", "keyword").endObject()
                    .startObject("realNameSim").field("type", "text").endObject()
                    .startObject("pinyinNameSim").field("type", "text").endObject()
                    .startObject("companySim").field("type", "text").endObject()
                    .startObject("majorSim").field("type", "text").endObject()
                    .startObject("schoolSim").field("type", "text").endObject()
                    .startObject("titleSim").field("type", "text").endObject()
                    .startObject("workStartDate").field("type", "date").field("format", "yyyy-MM-dd").endObject()
                    .startObject("eduStartDate").field("type", "date").field("format", "yyyy-MM-dd").endObject()
                    .startObject("eduEndDate").field("type", "date").field("format", "yyyy-MM-dd").endObject()
                    .startObject("companyListSim").field("type", "text").endObject()
                    .startObject("unionId").field("type", "keyword").endObject()
                    .startObject("isOperations").field("type", "boolean").endObject()
                    .startObject("firmId").field("type", "keyword").endObject()
                    .endObject()
                    .endObject();
            PutMapping putMapping = new PutMapping.Builder(ResumeFeatureSimHashIndexModel.INDEX_NAME,
                    ResumeFeatureSimHashIndexModel.TYPE, mapping.string()).build();
            JestResult jestResult = jestClient.execute(putMapping);
            LOGGER.info("createIndexMapping resp: {}", jestResult.getJsonString());
        } catch (IOException e) {
            LOGGER.error("createIndexMapping fail. msg:{}", e.getMessage());
        }
    }

    @Override
    public void insert() {
        int offset = 10000;
        for (int i = 1; i <= 500; i++) {

            List<String> rids = resumeFeatureSimhashMapper.selectIdByPage((i - 1) * offset, offset);

            List<Index> bulkActions = rids.stream()
                    .map(resumeFeatureSimhashMapper::selectIndexModelById)
                    .filter(Objects::nonNull).map(item -> {
                        Index build = new Index.Builder(item).id(item.getResumeId()).build();
                        return build;
                    }).collect(Collectors.toList());


            List<List<Index>> partition = ListUtils.partition(bulkActions, 100);

            for (List<Index> indexList : partition) {
                Bulk bulkResp = new Bulk.Builder()
                        .defaultIndex(ResumeFeatureSimHashIndexModel.INDEX_NAME)
                        .defaultType(ResumeFeatureSimHashIndexModel.TYPE)
                        .addAction(indexList).build();

                try {
                    BulkResult bulkResult = jestClient.execute(bulkResp);
                    LOGGER.info("bulkInsert resp: {}", bulkResult.getJsonString());
                } catch (IOException e) {
                    LOGGER.error("bulkInsert fail. msg:{}", e.getMessage());
                }
            }



//            for (String rid : rids) {
//                ResumeFeatureSimHashIndexModel indexModel = resumeFeatureSimhashMapper.selectIndexModelById(rid);
//                if (Objects.nonNull(indexModel)) {
//                    Index index = new Index.Builder(indexModel)
//                            .index(ResumeFeatureSimHashIndexModel.INDEX_NAME)
//                            .type(ResumeFeatureSimHashIndexModel.TYPE)
//                            .id(indexModel.getResumeId())
//                            .build();
//                    try {
//                        JestResult jestResult0 = jestClient.execute(index);
//                        if (!jestResult0.isSucceeded()) {
//                            LOGGER.error("insert:{}", jestResult0.getJsonString());
//                        }else{
//                            LOGGER.info("indecis {} ok!",rid);
//                        }
//                    } catch (IOException e) {
//                        LOGGER.error("insert fail. msg:{}", e.getMessage());
//                    }
//                }
//            }
        }

    }

    @Override
    public void bulkInsert() {

    }

    @Override
    public boolean save(ResumeFeatureSimHashIndexModel recipes) {
        Index index = new Index.Builder(recipes).index(ResumeFeatureSimHashIndexModel.INDEX_NAME).type(ResumeFeatureSimHashIndexModel.TYPE).build();
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
        Delete delete = new Delete.Builder(id).index(ResumeFeatureSimHashIndexModel.INDEX_NAME).type(ResumeFeatureSimHashIndexModel.TYPE).build();
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
    public boolean updateByDocId(ResumeFeatureSimHashIndexModel resumeFeatureSimHash) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doc", resumeFeatureSimHash);
        Update update = new Update.Builder(jsonObject.toJSONString()).index(ResumeFeatureSimHashIndexModel.INDEX_NAME).type(ResumeFeatureSimHashIndexModel.TYPE)
                .id(resumeFeatureSimHash.getResumeId() + "").build();
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
    public List<ResumeFeatureSimHashIndexModel> search(String content) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", content));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(ResumeFeatureSimHashIndexModel.INDEX_NAME)
                .addType(ResumeFeatureSimHashIndexModel.TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            return result.getSourceAsObjectList(ResumeFeatureSimHashIndexModel.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getBigData() {
        long s = System.nanoTime();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(14858);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(ResumeFeatureSimHashIndexModel.INDEX_NAME)
                .addType(ResumeFeatureSimHashIndexModel.TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            List<ResumeFeatureSimHashIndexModel> sourceAsObjectList = result.getSourceAsObjectList(ResumeFeatureSimHashIndexModel.class);
            System.out.println(sourceAsObjectList.size());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        long e = System.nanoTime();
        System.out.println("--->"+(e-s)/1_000_000);
    }

}
