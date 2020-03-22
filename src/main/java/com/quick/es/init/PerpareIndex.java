package com.quick.es.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.gson.Gson;
import com.quick.es.entity.Order;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.ElasticsearchVersion;
import io.searchbox.core.Index;
import io.searchbox.indices.Rollover;
import org.elasticsearch.common.collect.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 *
 * @author wangxc
 * @date: 2020/3/18 下午10:13
 *
 */
@Component
public class PerpareIndex {

	@Autowired
	private JestClient jestClient;


	@Scheduled(cron = "30 * * * * *")
	public void insert() throws IOException, InterruptedException {
		for (int i = 0; i < 4; i++) {
			String format = DateUtil.format(new Date(), NORM_DATETIME_PATTERN);
			Order order = new Order(RandomUtil.randomString(5), RandomUtil.randomString(10), format);
			Index index0 = new Index.Builder(order).index("order-write").type("_doc").build();
			JestResult jestResult0 = jestClient.execute(index0);
			System.out.println("insert " + format + "--->" + jestResult0.getJsonString());
			Thread.sleep(5000);
		}

	}

//	@Scheduled(cron = "00 * * * * *")
	public void rollover() throws IOException, InterruptedException {
		Map<String, Object> rolloverConditions = new MapBuilder<String, Object>().put("max_age", "1m").immutableMap();
		Rollover rollover = new Rollover.Builder("order-write").conditions(rolloverConditions).build();
		boolean rolledOver = true;
		JestResult execute = null;
		do {
			execute = jestClient.execute(rollover);
			rolledOver = (boolean) execute.getValue("rolled_over");
			System.out.println("rollover....");
		} while (!rolledOver);
		System.out.println("rollover " + DateUtil.date() + "--->" + execute.getJsonString());

	}
}
