package com.quick.es.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quick.es.entity.Student;

import java.io.IOException;

/**
 *
 * @author wangxc
 * @date: 2020/1/6 下午9:38
 *
 */
public interface IStudentService extends IService<Student> {

	void createIndex();
	void createIndexMapping();
	void bulkInsert();
	void truncateIndex();

	Double avgAge() throws IOException;

	void getTotalTopN() throws IOException;

	void getPerTopClass();
}
