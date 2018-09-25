package com.quick.es.init;

import com.quick.es.service.EsService;
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
	private EsService recipesService;

	@Override
	public void run(String... strings) throws Exception {
		recipesService.createIndex();
		/**
		 * 可自主选择单个插入还是批量插入
		 */
		recipesService.bulkInsert();
	}
}
