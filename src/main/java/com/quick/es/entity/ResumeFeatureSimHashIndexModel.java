package com.quick.es.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vector
 * @Data 2018/8/1 0001
 * @Description 文档类型
 */
@Data
public class ResumeFeatureSimHashIndexModel implements Serializable {

	public static final String INDEX_NAME = "anonymous_match_data_index";
	public static final String TYPE = "resume_feature_simHash";

    private String resumeId;
    private String pinyinName;
    private String realName;
    private String lastName;
    private int gender;
    private int age;
    private int degree;
    private String school;
    private String major;
    private String company;
    private String title;
    private long realNameSim;
    private long pinyinNameSim;
    private long companySim;
    private long majorSim;
    private long schoolSim;
    private long titleSim;
    // 日期较特殊，用字符串索引进ES
    private String workStartDate;
    private String eduStartDate;
    private String eduEndDate;
    private String companyListSim;
    private String unionId;
    private boolean isOperations;
    private String firmId;

}
