package com.quick.es.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created with IDEA
 * User: vector
 * Data: 2018/4/3 0003
 * Time: 10:55
 * Description:
 */
public class ESUtils {

    private static Logger logger = LogManager.getLogger(ESUtils.class);

    private static String clusterNodes = "120.55.168.18:8830";

    private static String clusterNames = "data_es";

    private static TransportClient transportClient;

    public static void main(String[] args) {
        buildMapping();
    }

    public static TransportClient getClient() {
        if (transportClient == null) {
            String InetSocket[] = clusterNodes.split(":");
            String address = InetSocket[0];
            Integer port = Integer.valueOf(InetSocket[1]);
            Settings settings = Settings.builder().put("cluster.name", "data_es").build();
            try {
                transportClient = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), port));
            } catch (UnknownHostException e) {
                logger.error("cant find es host");
                e.printStackTrace();
            }
        }
        return transportClient;
    }


    /**
     * 根据自己的需要配置mapping
     * @return
     */
    public static XContentBuilder buildMapping() {
        String result = "";
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .field("job") // type name
                    .startObject()
                    .field("properties")
                    .startObject()
                    .field("jobName").startObject().field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .field("address").startObject().field("type", "text").endObject()
                    .field("detailAddress").startObject().field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .field("salary").startObject().field("type", "text").endObject()
                    .field("degree").startObject().field("type", "text").endObject()
                    .field("jobDesc").startObject().field("type", "text").endObject()
                    .field("hireHead").startObject().field("type", "text").endObject()
                    .field("hireHeadPosition").startObject().field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .field("jobTags").startObject().field("type", "text").endObject()
                    .field("yearOfExpe").startObject().field("type", "text").endObject()
                    .field("teamDesc").startObject().field("type", "text").endObject()
                    .field("teamTags").startObject().field("type", "text").endObject()
                    .field("companyName").startObject().field("type", "text").field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()
                    .field("companyUrl").startObject().field("type", "text").endObject()
                    .field("companyFinancing").startObject().field("type", "text").endObject()
                    .field("companyIndustry").startObject().field("type", "text").endObject()
                    .field("companyScale").startObject().field("type", "text").endObject()
                    .field("companyNature").startObject().field("type", "text").endObject()
                    .field("crawlUrl").startObject().field("type", "text").endObject()
                    .field("crawlDate").startObject().field("type", "date").endObject()
                    .field("title4Kibana").startObject().field("type", "keyword").endObject()
                    .field("company4Kibana").startObject().field("type", "keyword").endObject()
                    .field("degree4Kibana").startObject().field("type", "keyword").endObject()
                    .field("companyFinancing4Kibana").startObject().field("type", "keyword").endObject()
                    .field("companyScale4Kibana").startObject().field("type", "keyword").endObject()
                    .field("salary4Kibana").startObject().field("type", "keyword").endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            result = mapping.string();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapping;
    }
}
