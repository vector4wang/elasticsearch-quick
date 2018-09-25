package com.quick.es.controller;

import com.quick.es.entity.BossJdInfo;
import com.quick.es.service.BossJdInfoService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/search")
@Api(value = "使用分词搜索", tags = "RecipesAnalyzedController")
public class BossJdSearchController {

	@Resource
	private BossJdInfoService bossJdInfoService;

	@RequestMapping(value = "/termQuery", method = RequestMethod.POST)
	public List<BossJdInfo> termMatch(@RequestParam("field") String field, @RequestParam("keyword") String keyword) {
		return bossJdInfoService.termQuery(field, keyword);
	}

	@RequestMapping(value = "/matchQuery", method = RequestMethod.POST)
	public List<BossJdInfo> matchQuery(@RequestParam("field") String field, @RequestParam("keyword") String keyword) {
		return bossJdInfoService.matchQuery(field, keyword);
	}

	@RequestMapping(value = "/booleanQuery", method = RequestMethod.POST)
	public List<BossJdInfo> booleanQuery(@RequestParam("keyword") String keyword) {
		return bossJdInfoService.booleanQuery(keyword);
	}

}
