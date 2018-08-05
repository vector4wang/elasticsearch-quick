package com.quick.es.service;

import com.quick.es.entity.Recipes;

import java.util.List;

public interface RecipesAnalyzedService {
	List<Recipes> termQuery(String field, String keyword);

	List<Recipes> matchQuery(String field, String keyword);

	List<Recipes> booleanQuery(String keyword);
}
