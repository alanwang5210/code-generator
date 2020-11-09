package com.alan.cloud.codegenerator.common.conf.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2018-2020
 * FileName: Swagger2Properties
 * Author:   10067
 * Date:     2020/7/22 8:48
 * Description: 属性配置文件
 */
@Data
@ConfigurationProperties("swagger2")
@Component("swagger2Properties")
public class Swagger2Properties {

    /**
     * swagger 会解析的包路径
     */
    private String basePackage;
    /**
     * version
     */
    private String version;

    /**
     * title
     */
    private String title;

    /**
     * swagger 组相关说明
     */
    private String description;

}