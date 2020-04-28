package com.quick.es.job;

import com.quick.es.config.IndexConstants;
import com.quick.es.service.IndexManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;

/**
 *
 * @author wangxc
 * @date: 2020/3/22 下午5:23
 *
 */
//@Component
@Slf4j
public class IndexRolloverJob {

	@Resource
	private IndexManagerService indexManagerService;


	@Scheduled(cron = "0 */5 * * * *")
	public void rollover() throws IOException, InterruptedException {
		log.info("index rollover start ");
		indexManagerService.rollover(IndexConstants.ORDER_PREFIX);
		log.info("index rollover end ");

	}
}
