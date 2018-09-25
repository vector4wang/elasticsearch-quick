package com.quick.es.util;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class EsUtil {
	public static void main(String[] args) {
		buildMapping();
	}

	public static XContentBuilder buildMapping() {
		String result = "";
		XContentBuilder mapping = null;
		try {
			mapping = jsonBuilder()
					.startObject()
					.field("job_detail") // type name
					.startObject()
					.field("properties")
					.startObject()
					.field("id").startObject().field("type","keyword").endObject()
					.field("jobName").startObject().field("type", "text").endObject()
					.field("address").startObject().field("type", "text").endObject()
					.field("detailAddress").startObject().field("type", "text").endObject()
					.field("salary").startObject().field("type", "text").endObject()
					.field("degree").startObject().field("type", "text").endObject()
					.field("jobDesc").startObject().field("type", "text").endObject()
					.field("jobHead").startObject().field("type", "text").endObject()
					.field("hireHead").startObject().field("type", "text").endObject()
					.field("hireHeadPosition").startObject().field("type", "text").endObject()
					.field("jobHeadPosition").startObject().field("type", "text").endObject()
					.field("jobTags").startObject().field("type", "text").endObject()
					.field("yearOfExpe").startObject().field("type", "text").endObject()
					.field("teamDesc").startObject().field("type", "text").endObject()
					.field("teamTags").startObject().field("type", "text").endObject()
					.field("companyName").startObject().field("type", "text").endObject()
					.field("companyUrl").startObject().field("type", "keyword").endObject()
					.field("financing").startObject().field("type", "text").endObject()
					.field("industry").startObject().field("type", "text").endObject()
					.field("scale").startObject().field("type", "text").endObject()
					.field("companyNature").startObject().field("type", "text").endObject()
					.field("url").startObject().field("type", "text").endObject()
					.field("insertTime").startObject().field("type", "date").endObject()
					.field("publishDate").startObject().field("type", "date").endObject()
					.field("jobName4Kibana").startObject().field("type", "keyword").endObject()
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
