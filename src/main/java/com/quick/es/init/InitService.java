package com.quick.es.init;

import com.alibaba.fastjson.JSON;
import com.quick.es.entity.ResumeFeatureSimHashIndexModel;
import com.quick.es.entity.ResumeFeatureSimhash;
import com.quick.es.mapper.ResumeFeatureSimhashMapper;
import com.quick.es.service.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author vector
 * @Data 2018/8/1 0001
 * @Description 方便测试，配置好ES的链接信息后，启动项目，会直接创建索引，创建mapping，插入数据，方便测试
 */
@Component
@Order
public class InitService implements CommandLineRunner {

    @Autowired
    private ResumeFeatureSimhashMapper resumeFeatureSimhashMapper;

	@Autowired
	private RecipesService recipesService;

	@Override
	public void run(String... strings) throws Exception {

//		recipesService.createIndex();
//		recipesService.createIndexMapping();
//		/**
//		 * 可自主选择单个插入还是批量插入
//		 */
//		recipesService.insert();
//		recipesService.bulkInsert();
	}
}
