package com.alan.cloud.codegenerator;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author 10100
 */
@MapperScan(basePackages = {"com.alan.cloud.codegenerator.mapper", "com.alan.cloud.codegenerator.common.*.mapper"})
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableScheduling
public class CodeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApplication.class, args);
    }
}