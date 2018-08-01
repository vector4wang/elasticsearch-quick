package com.quick.es.controller;

import com.alibaba.fastjson.JSON;
import com.quick.es.entity.Recipes;
import com.quick.es.service.RecipesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author vector
 * @Data 2018/8/1 0001
 * @Description
 */
@RestController
@RequestMapping("/recipes")
public class RecipesController {

	@Resource
	private RecipesService recipesService;


	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam("keyword") String keyword) {
		List<Recipes> search = recipesService.search(keyword);
		return JSON.toJSONString(search);
	}
}
