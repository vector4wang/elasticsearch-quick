package com.quick.es.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 *
 * @author wangxc
 * @date: 2020/1/6 下午10:09
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentServiceTest {

	@Resource
	private IStudentService studentService;

	@Test
	public void testCreateIndex() {
		studentService.createIndex();
	}

	@Test
	public void testCreateMapping() {
		studentService.createIndexMapping();
	}

	@Test
	public void testBulkInsert() {
		studentService.bulkInsert();
	}

	@Test
	public void testTruncateIndex() {
		studentService.truncateIndex();
	}

	@Test
	public void testAvgAge() throws IOException {
		System.out.println(studentService.avgAge());
	}

	@Test
	public void testGetTotalTopN() throws IOException {
		studentService.getTotalTopN();
	}

	/**
	 * 取出每个人最高分的学科
	 */
	@Test
	public void testGetPerTopClass() {
		studentService.getPerTopClass();
	}
}
