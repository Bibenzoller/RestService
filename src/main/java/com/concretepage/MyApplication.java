package com.concretepage;
import com.concretepage.jewelry.controller.JewelryController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
/*@Configuration
@EnableAutoConfiguration
@ComponentScan*/
@EnableSwagger2
@EnableCaching

public class MyApplication {

	private static Logger log = LoggerFactory.getLogger(MyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
    }
	private Logger logger = LoggerFactory.getLogger(JewelryController.class);


	@Bean
	public CacheManager cacheManagerJewelry() {
		 logger.info("Enable Caching");
		return new ConcurrentMapCacheManager("jewelry");


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

	@Bean
	public Docket mailApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("mail")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/mail.*"))
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
	}

}