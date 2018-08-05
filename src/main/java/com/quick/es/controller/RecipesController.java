package com.quick.es.controller;

import com.alibaba.fastjson.JSON;
import com.quick.es.entity.Recipes;
import com.quick.es.service.RecipesService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author vector
 * @Data 2018/8/1 0001
 * @Description
 */
@RestController
@RequestMapping("/recipes")
@Api(description = "ElasticSearch 教科书般的Code", tags = "RecipesController")
public class RecipesController {

	@Resource
	private RecipesService recipesService;

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestBody Recipes recipes) {
		return recipesService.save(recipes) ? "插入成功!" : "插入失败";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam("keyword") String keyword) {
		List<Recipes> search = recipesService.search(keyword);
		return JSON.toJSONString(search);
	}


	@RequestMapping(value = "/deleteByDocId", method = RequestMethod.GET)
	public String deleteByDocId(@RequestParam("id") String id) {
		return recipesService.deleteByDocId(id) ? "文档已删除！" : "删除失败";
	}

	@RequestMapping(value = "/updateByDocId", method = RequestMethod.POST)
	public String updateByDocId(@RequestBody Recipes recipes) {

		return recipesService.updateByDocId(recipes) ? "更新成功！" : "更新失败";
	}
}
