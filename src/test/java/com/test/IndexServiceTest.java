package com.test;

import com.quick.es.Application;
import com.quick.es.service.IndexService;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author wangxc
 * @date: 2020/4/21 上午9:41
 *
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexServiceTest {
	@Autowired
	private IndexService indexService;

	@Test
	public void testCreateIndex() {
		indexService.createIndex();
	}

	@Test
	public void testDeleteIndex() {
		indexService.deleteIndex();
	}

	@Test
	public void testCreateMapping() {
		indexService.createMapping();
	}

	@Test
	public void testGetMapping() {
		indexService.getMapping();
	}


	@Test
	public void batchTest() {
		indexService.batchInsertData();
	}

	@Test
	public void testSampleSearch() {
		indexService.sampleSearch();
	}

	@Test
	public void aggTest1() {
		StatsAggregationBuilder age = AggregationBuilders.stats("age");
//		AggregationBuilder termsAggregation = result.getAggregations().getTermsAggregation("total");
	}
}
