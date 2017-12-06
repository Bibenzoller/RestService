package com.concretepage;
import com.concretepage.acpects.LoggerAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2

	public class MyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
    }

	@Bean
	public Docket jewelryApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("jewelry")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/jewelry.*"))
				.build();
	}

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("user")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/user.*"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring Boot REST API")
				.description("Spring Boot REST API for BaltSilverApp")
				.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
				.contact("Mikhail Khokhlov")
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
				.version("2.0")
				.build();
	}}