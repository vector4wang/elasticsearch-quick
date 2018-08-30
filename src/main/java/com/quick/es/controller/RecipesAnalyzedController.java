package com.quick.es.controller;

import com.quick.es.entity.Recipes;
import com.quick.es.service.RecipesAnalyzedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/recipesAnalyzed")
@Api(value = "使用分词搜索", tags = "RecipesAnalyzedController")
public class RecipesAnalyzedController {

	@Resource
	private RecipesAnalyzedService recipesAnalyzedService;

	@RequestMapping(value = "/termQuery", method = RequestMethod.POST)
	public List<Recipes> termMatch(@RequestParam("field") String field, @RequestParam("keyword") String keyword) {
		return recipesAnalyzedService.termQuery(field, keyword);
	}

	@RequestMapping(value = "/matchQuery", method = RequestMethod.POST)
	public List<Recipes> matchQuery(@RequestParam("field") String field, @RequestParam("keyword") String keyword) {
		return recipesAnalyzedService.matchQuery(field, keyword);
	}

	@RequestMapping(value = "/booleanQuery", method = RequestMethod.POST)
	public List<Recipes> booleanQuery(@RequestParam("keyword") String keyword) {
		return recipesAnalyzedService.booleanQuery(keyword);
	}

	@RequestMapping(value = "/highlightQuery", method = RequestMethod.POST)
	@ApiOperation(value = "高亮搜索", tags = "高亮搜索")
	public List<Recipes> highlightQuery(@RequestParam("keyword") String keyword) throws IOException {
		return recipesAnalyzedService.highlightQuery(keyword);
	}

}
