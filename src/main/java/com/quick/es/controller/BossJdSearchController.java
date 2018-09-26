package com.quick.es.controller;

import com.quick.es.entity.BossJdInfo;
import com.quick.es.service.BossJdInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
@Api(value = "简单搜索", tags = "RecipesAnalyzedController")
public class BossJdSearchController {

	@Resource
	private BossJdInfoService bossJdInfoService;

	@RequestMapping(value = "/termQuery", method = RequestMethod.POST)
	@ApiOperation(value = "termQuery", notes = "指定字段精确搜索")
	public List<BossJdInfo> termMatch(@RequestParam("field") String field, @RequestParam("keyword") String keyword) {
		return bossJdInfoService.termQuery(field, keyword);
	}

	@RequestMapping(value = "/matchQuery", method = RequestMethod.POST)
	@ApiOperation(value = "matchQuery", notes = "指定字段'模糊'搜索")
	public List<BossJdInfo> matchQuery(@RequestParam("field") String field, @RequestParam("keyword") String keyword) {
		return bossJdInfoService.matchQuery(field, keyword);
	}

	@RequestMapping(value = "/booleanQuery", method = RequestMethod.POST)
	@ApiOperation(value = "booleanQuery", notes = "默认搜索职位")
	public List<BossJdInfo> booleanQuery(@RequestParam("keyword") String keyword) {
		return bossJdInfoService.booleanQuery(keyword);
	}

}
