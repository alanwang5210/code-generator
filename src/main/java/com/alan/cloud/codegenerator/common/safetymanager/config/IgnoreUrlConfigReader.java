package com.alan.cloud.codegenerator.common.safetymanager.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 不需要验证地址
 *
 * @author 王合
 * @date 2019-08-07 15:07:11
 */
@Data
@Configuration
@ConditionalOnExpression("!'${ignore}'.isEmpty()")
@ConfigurationProperties(prefix = "ignore")
public class IgnoreUrlConfigReader {
    private List<String> urls = new ArrayList<>();
}
