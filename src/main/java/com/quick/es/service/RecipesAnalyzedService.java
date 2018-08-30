package com.quick.es.service;

import com.quick.es.entity.Recipes;

import java.io.IOException;
import java.util.List;

public interface RecipesAnalyzedService {
	List<Recipes> termQuery(String field, String keyword);

	List<Recipes> matchQuery(String field, String keyword);

	List<Recipes> booleanQuery(String keyword);

	List<Recipes> highlightQuery(String keyword) throws IOException;
}
