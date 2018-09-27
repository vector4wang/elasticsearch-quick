package com.quick.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BossJdInfo {

	public final static String INDEX_NAME = "boss_jd_info";

	public final static String TYPE = "job_detail";

	private Long id;

	private String address;

	private String companyName;

	private String companyUrl;

	private String degree;

	private String detailAddress;

	private String financing;

	private String industry;

	private String jobHead;

	private String jobHeadPosition;

	private String jobName;

	private String jobTags;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date publishDate;

	private String salary;

	private String scale;

	private String url;

	private String yearOfExpe;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date insertTime;

	private String jobDesc;

	private String teamDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl == null ? null : companyUrl.trim();
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree == null ? null : degree.trim();
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress == null ? null : detailAddress.trim();
	}

	public String getFinancing() {
		return financing;
	}

	public void setFinancing(String financing) {
		this.financing = financing == null ? null : financing.trim();
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry == null ? null : industry.trim();
	}

	public String getJobHead() {
		return jobHead;
	}

	public void setJobHead(String jobHead) {
		this.jobHead = jobHead == null ? null : jobHead.trim();
	}

	public String getJobHeadPosition() {
		return jobHeadPosition;
	}

	public void setJobHeadPosition(String jobHeadPosition) {
		this.jobHeadPosition = jobHeadPosition == null ? null : jobHeadPosition.trim();
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName == null ? null : jobName.trim();
	}

	public String getJobTags() {
		return jobTags;
	}

	public void setJobTags(String jobTags) {
		this.jobTags = jobTags == null ? null : jobTags.trim();
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary == null ? null : salary.trim();
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale == null ? null : scale.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getYearOfExpe() {
		return yearOfExpe;
	}

	public void setYearOfExpe(String yearOfExpe) {
		this.yearOfExpe = yearOfExpe == null ? null : yearOfExpe.trim();
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc == null ? null : jobDesc.trim();
	}

	public String getTeamDesc() {
		return teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc == null ? null : teamDesc.trim();
	}

	@Override
	public String toString() {
		return "BossJdInfo{" + "id=" + id + ", address='" + address + '\'' + ", companyName='" + companyName + '\''
				+ ", companyUrl='" + companyUrl + '\'' + ", degree='" + degree + '\'' + ", detailAddress='"
				+ detailAddress + '\'' + ", financing='" + financing + '\'' + ", industry='" + industry + '\''
				+ ", jobHead='" + jobHead + '\'' + ", jobHeadPosition='" + jobHeadPosition + '\'' + ", jobName='"
				+ jobName + '\'' + ", jobTags='" + jobTags + '\'' + ", publishDate=" + publishDate + ", salary='"
				+ salary + '\'' + ", scale='" + scale + '\'' + ", url='" + url + '\'' + ", yearOfExpe='" + yearOfExpe
				+ '\'' + ", insertTime=" + insertTime + ", jobDesc='" + jobDesc + '\'' + ", teamDesc='" + teamDesc
				+ '\'' + '}';
	}
}