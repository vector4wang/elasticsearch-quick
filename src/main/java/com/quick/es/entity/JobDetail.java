package com.quick.es.entity;

public class JobDetail extends BossJdInfo {
	private String jobName4Kibana;

	private String company4Kibana;

	private String degree4Kibana;

	private String companyFinancing4Kibana;

	private String companyScale4Kibana;

	private String salary4Kibana;

	public JobDetail() {
	}

	public JobDetail(BossJdInfo bossJdInfo) {
		this.setId(bossJdInfo.getId());
		this.setAddress(bossJdInfo.getAddress());
		this.setCompanyName(bossJdInfo.getCompanyName());
		this.setCompanyUrl(bossJdInfo.getCompanyUrl());
		this.setDegree(bossJdInfo.getDegree());
		this.setDetailAddress(bossJdInfo.getDetailAddress());
		this.setFinancing(bossJdInfo.getFinancing());
		this.setIndustry(bossJdInfo.getIndustry());
		this.setJobHead(bossJdInfo.getJobHead());
		this.setJobHeadPosition(bossJdInfo.getJobHeadPosition());
		this.setJobName(bossJdInfo.getJobName());
		this.setJobTags(bossJdInfo.getJobTags());
		this.setPublishDate(bossJdInfo.getPublishDate());
		this.setSalary(bossJdInfo.getSalary());
		this.setScale(bossJdInfo.getScale());
		this.setUrl(bossJdInfo.getUrl());
		this.setYearOfExpe(bossJdInfo.getYearOfExpe());
		this.setInsertTime(bossJdInfo.getInsertTime());
		this.setJobDesc(bossJdInfo.getJobDesc());
		this.setTeamDesc(bossJdInfo.getTeamDesc());

		this.setJobName4Kibana(bossJdInfo.getJobName());
		this.setCompany4Kibana(bossJdInfo.getCompanyName());
		this.setDegree4Kibana(bossJdInfo.getDegree());
		this.setCompanyFinancing4Kibana(bossJdInfo.getFinancing());
		this.setCompanyScale4Kibana(bossJdInfo.getScale());
		this.setSalary4Kibana(bossJdInfo.getSalary());
	}

	public String getJobName4Kibana() {
		return jobName4Kibana;
	}

	public void setJobName4Kibana(String jobName4Kibana) {
		this.jobName4Kibana = jobName4Kibana;
	}

	public String getCompany4Kibana() {
		return company4Kibana;
	}

	public void setCompany4Kibana(String company4Kibana) {
		this.company4Kibana = company4Kibana;
	}

	public String getDegree4Kibana() {
		return degree4Kibana;
	}

	public void setDegree4Kibana(String degree4Kibana) {
		this.degree4Kibana = degree4Kibana;
	}

	public String getCompanyFinancing4Kibana() {
		return companyFinancing4Kibana;
	}

	public void setCompanyFinancing4Kibana(String companyFinancing4Kibana) {
		this.companyFinancing4Kibana = companyFinancing4Kibana;
	}

	public String getCompanyScale4Kibana() {
		return companyScale4Kibana;
	}

	public void setCompanyScale4Kibana(String companyScale4Kibana) {
		this.companyScale4Kibana = companyScale4Kibana;
	}

	public String getSalary4Kibana() {
		return salary4Kibana;
	}

	public void setSalary4Kibana(String salary4Kibana) {
		this.salary4Kibana = salary4Kibana;
	}
}
