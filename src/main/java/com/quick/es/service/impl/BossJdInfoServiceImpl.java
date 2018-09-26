package com.quick.es.service.impl;

import com.quick.es.entity.BossJdInfo;
import com.quick.es.service.BossJdInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BossJdInfoServiceImpl implements BossJdInfoService {


	private static final Logger LOGGER = LoggerFactory.getLogger(BossJdInfoService.class);


	@Autowired
	private JestClient jestClient;

	/**
	 * @param field
	 * @param keyword
	 * @return
	 */
	@Override
	public List<BossJdInfo> termQuery(String field, String keyword) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery(field, keyword));
		System.out.println(searchSourceBuilder.toString());
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(BossJdInfo.INDEX_NAME)
				.addType(BossJdInfo.TYPE).build();
		return generateResult(search);
	}

	@Override
	public List<BossJdInfo> matchQuery(String field, String keyword) {
		//		searchSourceBuilder.query(QueryBuilders.matchQuery(field, keyword));  // TODO: 2018/8/5 自带的matchquery组装请求参数中带有type，暂不明白
		String script = "{\"query\":{\"match\":{\""+field+"\":{\"query\":\""+keyword+"\"}}}}";
		Search search = new Search.Builder(script).addIndex(BossJdInfo.INDEX_NAME).addType(BossJdInfo.TYPE).build();
		return generateResult(search);
	}

	@Override
	public List<BossJdInfo> booleanQuery(String keyword) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("jobName", keyword));
		searchSourceBuilder.query(queryBuilder);
		System.out.println(searchSourceBuilder.toString());
		Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex(BossJdInfo.INDEX_NAME)
				.addType(BossJdInfo.TYPE).build();
		return generateResult(build);
	}

//	@Override
//	public List<BossJdInfo> highlightQuery(String keyword) throws IOException {
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name4Standard", keyword));
//		searchSourceBuilder.query(queryBuilder);
//		HighlightBuilder highlightBuilder = new HighlightBuilder();
//		highlightBuilder.field("name4Standard");//高亮title
//		highlightBuilder.preTags("<em>").postTags("</em>");//高亮标签
//		highlightBuilder.fragmentSize(500);//高亮内容长度
//		searchSourceBuilder.highlight(highlightBuilder);
//		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(BossJdInfo.INDEX_NAME).addType(BossJdInfo.TYPE).build();
//		SearchResult result = jestClient.execute(search);
//		List<BossJdInfo> BossJdInfo = new ArrayList<>();
//		List<SearchResult.Hit<BossJdInfo, Void>> hits = result.getHits(BossJdInfo.class);
//		for (SearchResult.Hit<BossJdInfo, Void> hit : hits) {
//			BossJdInfo source = hit.source;
//
//			Map<String, List<String>> highlight = hit.highlight;
//			List<String> name4IKView = highlight.get("name4Standard");
//			if (name4IKView != null) {
//				source.setName4IK(name4IKView.get(0));
//			}
//			BossJdInfo.add(source);
//		}
//		return BossJdInfo;
//	}

	private List<BossJdInfo> generateResult(Search search) {
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
