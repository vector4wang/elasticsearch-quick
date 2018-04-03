package com.quick.es.client;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quick.es.util.ESUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class EsClient {


    public static void main(String[] args) {
        try {
            /**
             * 创建索引 createIndex("test_index");
             */

            /**
             * 生成并创建mapping createMapping("test_index");
             */

            /**
             * 索引数据 putDocument("test_index","job");
             */

            /**
             * 删除某个文档 方法很多，根据_id删除，queryDelete 等等
             * deleteDocument("test_index","job","");
             */

            /**
             * 比较重要 搜索 search();
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以先创建索引，然后在创建mapping的时候指定type
     *
     * @param indexName
     * @throws UnknownHostException
     */
    private static void createIndex(String indexName) throws UnknownHostException {
        TransportClient client = ESUtils.getClient();
        CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate(indexName).get();
        System.out.println(createIndexResponse.isShardsAcked());
        client.close();
    }

    /**
     * 确保已经安装了对应的分词插件，如IK、Jieba
     * 生成mapping，并创建 注意一下es（5.4）支持的类型 String,Number,Date,Boolean,Binary,Rande  https://www.jianshu.com/p/e68c3720c6e7
     */
    private static void createMapping(String index) {
        XContentBuilder xContentBuilder = ESUtils.buildMapping();
        PutMappingRequest source = Requests.putMappingRequest(index).type("job").source(xContentBuilder);
        TransportClient client = ESUtils.getClient();
        PutMappingResponse putMappingResponse = client.admin().indices().putMapping(source).actionGet();
        System.out.println(putMappingResponse.isAcknowledged());
        client.close();
    }

    /**
     * 索引部分数据
     * @throws IOException
     * @param indexName
     * @param type
     */
    private static void putDocument(String indexName, String type) throws IOException {
        TransportClient client = ESUtils.getClient();
        /**
         * 文件比较大可在这里下载 https://download.csdn.net/download/qqhjqs/10324928
         */
        String txt = IOUtils.toString(new FileInputStream(new File("d:\\boss.txt")));
        JSONArray jsonArray = JSONArray.parseArray(txt);
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        for (int i = 1; i <= jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i-1);
            /**
             * 指定了_id 的取值，可实现存在更新不存在插入的功能，性能可能会降低一些
             */
            bulkRequest.add(client.prepareIndex(indexName, type, DigestUtils.md5Hex(jsonObject.getString("url"))).setSource(jsonObject.toJSONString(), XContentType.JSON));
            if (i % 1000 == 0) {
                BulkResponse bulkResponse = bulkRequest.get();
                if (bulkResponse.hasFailures()) {
                    System.out.println("插入失败");
                }else{
                    System.out.println(bulkResponse.status());
                }
                bulkRequest = client.prepareBulk();
            }
        }
    }


    /**
     * 根据_id删除
     * @param indexName
     * @param type
     * @param id
     * @throws UnknownHostException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void deleteDocument(String indexName,String type,String id) throws UnknownHostException, ExecutionException, InterruptedException {
        TransportClient client = ESUtils.getClient();
        DeleteResponse deleteResponse = client.prepareDelete(indexName, type, id).execute().get();
        System.out.println(deleteResponse.status().getStatus());
    }
//
//
//    private static void search() throws UnknownHostException {
//        TransportClient client = ESClientConfig.getClient();
//
//
////        GetResponse response = client.prepareGet("world", "city", "AVx9ZrO1r3OKqmuL07ZS")
////                .setOperationThreaded(false)
////                .get();
////        System.out.println(response.getSourceAsString());
//
////        QueryBuilder qb = QueryBuilders.matchAllQuery();// 全部
////        QueryBuilder qb = QueryBuilders.matchQuery("name", "ar");
////        QueryBuilder qb = QueryBuilders.multiMatchQuery("herat","name","district"); // OR 多个字段
////        QueryBuilder qb = QueryBuilders.rangeQuery("population").gte(100000).lt(250000); // RANGER
////        QueryBuilder qb = QueryBuilders.termQuery("name","herat").boost(10);// 准确
////        QueryBuilder qb = QueryBuilders.fuzzyQuery("name","ar");// 模糊查询
////        QueryBuilder qb = QueryBuilders.termsQuery(); // 含有多个词条
//
////        QueryBuilder qb = QueryBuilders.matchQuery("name","ar").operator(Operator.AND);
////        QueryBuilder qb1 = QueryBuilders.matchQuery("name", "Qandahar");
//        //                .must(QueryBuilders.termQuery("title","android"))
////                .mustNot(QueryBuilders.fuzzyQuery("location","广东"))
////                .must(QueryBuilders.matchQuery("degree","本科"))
////                .mustNot(QueryBuilders.matchQuery("company","腾讯"))// 不在这家公司
////                .mustNot(QueryBuilders.matchQuery("id","9F5AE922-344A-4036-84EF-ADB45292A2B7"))
////                .mustNot(QueryBuilders.matchQuery("id","a55d0234-3a76-4962-bab6-0b563e81ac1a"))
//                /*.must(QueryBuilders.rangeQuery("yearofexperience").gte(3).lte(5))*/
//        System.out.println(client.nodeName());
//        long start = System.currentTimeMillis();
//
////        String[] fid = {"a7990254-20c1-468a-a196-3d15beed1f70","a79a0444-ce18-4b90-acb4-3b0897fa2982","a79c0384-6eb4-47c6-96f7-6fed5f95847b","a79a03cc-7588-4d3c-ba79-a4149cddde83",
////                "a79203e6-f288-4de0-b454-bc887b895991","a79203e6-dae5-4971-898b-fd1c5303a1f1","a79203e6-f111-465e-b6c7-aad80bf09af9"};
////        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
////        queryBuilder
////                .must(QueryBuilders.matchQuery("title", "cto"))
////                .mustNot(QueryBuilders.matchQuery("owner_id","a7850376-9bf7-4ad0-9bfd-997806dac036"));
//////
////        List<String> strings = Arrays.asList(fid);
////        strings.forEach(t->queryBuilder.mustNot(QueryBuilders.matchQuery("talent_id",t)));
//
////        QueryBuilders.boolQuery().should(QueryBuilders.matchPhraseQuery("title", "产品经理").slop(2))
////                .should(QueryBuilders.matchQuery("title", "java"))
////                .should(QueryBuilders.matchQuery("title", "android"));
//
////        BoolQueryBuilder mobile = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("mobile", "13072130095");
//
//        // QueryBuilders.matchPhraseQuery("title","产品经理").slop(5)
//
////        MatchPhraseQueryBuilder slop = QueryBuilders.matchPhraseQuery("title", "技术总监").slop(2);
//
//        SearchResponse scrollResp1 = client
//                .prepareSearch(INDEX)
//                .setTypes(TYPE) //
//                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("title", "产品设计").slop(2)))
////                .setFrom(10)
//                .setSize(1000)
////                .addSort(SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER))
//                .addSort(SortBuilders.fieldSort("year_of_experience").order(SortOrder.DESC))
//                .get();
//
//        System.out.println(scrollResp1.getHits().totalHits);
//
//
//        SearchHits searchHits1 = scrollResp1.getHits();
//        long end = System.currentTimeMillis();
//        //共搜到:" + searchHits.getTotalHits() + "条结果!共
//        System.out.println("耗时" + (double) (end - start) / 1000 + "s。");
//        //遍历结果
//        for (SearchHit hit : searchHits1) {
//            JSONObject jsonObject = JSON.parseObject(hit.getSourceAsString());
//
////            System.out.println(jsonObject.getInteger("year_of_experience"));
//        }
//
//    }


}
