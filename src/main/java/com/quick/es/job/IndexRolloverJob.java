package com.quick.es.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.quick.es.config.IndexCommon;
import com.quick.es.config.IndexConstants;
import com.quick.es.entity.Order;
import com.quick.es.service.IndexManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 *
 * @author wangxc
 * @date: 2020/3/22 下午5:23
 *
 */
@Component
@Slf4j
public class IndexRolloverJob {

	@Resource
	private IndexManagerService indexManagerService;

	@Scheduled(cron = "30 * * * * *")
	public void insert() throws IOException, InterruptedException {
		log.info("test insert 10w datas");
		List<Order> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			Order order = new Order();
			order.setUid(RandomUtil.randomString(10));
			order.setNick(RandomUtil.randomString(5));
			order.setChatTime(DateUtil.format(new Date(), NORM_DATETIME_PATTERN));
			list.add(order);
		}
		indexManagerService.bucketInsert(IndexCommon.getWriteIndex(IndexConstants.ORDER_PREFIX), list);
		list.clear();

	}

	@Scheduled(cron = "0 */5 * * * *")
	public void rollover() throws IOException, InterruptedException {
		log.info("index rollover start ");
		indexManagerService.rollover(IndexConstants.ORDER_PREFIX);
		log.info("index rollover end ");

	}
}
