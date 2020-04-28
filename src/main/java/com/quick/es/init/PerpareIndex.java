package com.quick.es.init;

import com.quick.es.config.IndexConstants;
import com.quick.es.service.IndexManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

/**
 *
 * @author wangxc
 * @date: 2020/3/18 下午10:13
 *
 */
//@Component
@Slf4j
public class PerpareIndex {

	@Resource
	private IndexManagerService indexManagerService;

	@PostConstruct
	public void prepareIndex() throws IOException {
		boolean exist = indexManagerService.isExistIndexTemplate(IndexConstants.ORDER_PREFIX);
		if(!exist){
			indexManagerService.createIndexTemplate(IndexConstants.ORDER_PREFIX);
			indexManagerService.createWriteIndexAlias(IndexConstants.ORDER_PREFIX);
		}
	}
}
