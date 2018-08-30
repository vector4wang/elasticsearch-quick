package com.quick.es.service.impl;

import com.quick.es.entity.Recipes;
import com.quick.es.service.RecipesAnalyzedService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
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
public class RecipesAnalyzedServiceImpl implements RecipesAnalyzedService {


	private static final Logger LOGGER = LoggerFactory.getLogger(RecipesAnalyzedService.class);

	@Autowired
	private JestClient jestClient;

	/**
	 * https://www.elastic.co/guide/cn/elasticsearch/guide/current/analysis-intro.html
	 *
	 * term是代表完全匹配，即不进行分词器分析，文档中必须包含整个搜索的词汇
	 *
	 * 奶油鲍鱼汤
	 * standard:奶,油,鲍,鱼,汤
	 * ik_smart:奶油,鲍,鱼汤
	 * @param field
	 * @param keyword
	 * @return
	 */
	@Override
	public List<Recipes> termQuery(String field, String keyword) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.termQuery(field, keyword));
		System.out.println(searchSourceBuilder.toString());
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(Recipes.INDEX_NAME)
				.addType(Recipes.TYPE).build();
		return generateResult(search);
	}

	@Override
	public List<Recipes> matchQuery(String field, String keyword) {
		//		searchSourceBuilder.query(QueryBuilders.matchQuery(field, keyword));  // TODO: 2018/8/5 自带的matchquery组装请求参数中带有type，暂不明白
		String script = "{\"query\":{\"match\":{\"name4Standard\":{\"query\":\"奶油\"}}}}";
		Search search = new Search.Builder(script).addIndex(Recipes.INDEX_NAME).addType(Recipes.TYPE).build();
		return generateResult(search);
	}

	@Override
	public List<Recipes> booleanQuery(String keyword) {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name4Standard", keyword))
				.must(QueryBuilders.rangeQuery("rating").gte(3.5));
		searchSourceBuilder.query(queryBuilder);
		System.out.println(searchSourceBuilder.toString());
		Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex(Recipes.INDEX_NAME)
				.addType(Recipes.TYPE).build();
		return generateResult(build);
	}

	@Override
	public List<Recipes> highlightQuery(String keyword) throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name4Standard", keyword));
		searchSourceBuilder.query(queryBuilder);
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("name4Standard");//高亮title
		highlightBuilder.preTags("<em>").postTags("</em>");//高亮标签
		highlightBuilder.fragmentSize(500);//高亮内容长度
		searchSourceBuilder.highlight(highlightBuilder);
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(Recipes.INDEX_NAME).addType(Recipes.TYPE).build();
		SearchResult result = jestClient.execute(search);
		List<Recipes> recipes = new ArrayList<>();
		List<SearchResult.Hit<Recipes, Void>> hits = result.getHits(Recipes.class);
		for (SearchResult.Hit<Recipes, Void> hit : hits) {
			Recipes source = hit.source;

			Map<String, List<String>> highlight = hit.highlight;
			List<String> name4IKView = highlight.get("name4Standard");
			if (name4IKView != null) {
				source.setName4IK(name4IKView.get(0));
			}
			recipes.add(source);
		}
		return recipes;
	}

	private List<Recipes> generateResult(Search search) {
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
