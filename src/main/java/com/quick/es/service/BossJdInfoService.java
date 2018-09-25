package com.quick.es.service;

import com.quick.es.entity.BossJdInfo;

import java.io.IOException;
import java.util.List;

public interface BossJdInfoService {

	List<BossJdInfo> termQuery(String field, String keyword);

	List<BossJdInfo> matchQuery(String field, String keyword);

	List<BossJdInfo> booleanQuery(String keyword);

//	List<BossJdInfo> highlightQuery(String keyword) throws IOException;
}
