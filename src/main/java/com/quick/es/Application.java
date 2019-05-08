package com.quick.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.quick.es.mapper")
public class Application {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.quick.es.controller")) //扫描API的包路径
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("ES使用api示例") // 标题
				.description("api接口的文档整理，支持在线测试") // 描述
				.contact(new Contact("vector.wang", "http://blog.wangxc.club/", "vector4wang@qq.com"))
				.version("1.0") // 版本号
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
