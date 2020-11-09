package com.alan.cloud.codegenerator.common.safetymanager;

import com.alan.cloud.codegenerator.common.safetymanager.interceptor.CommonInterceptor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王合
 * @date 2019-10-18 16:10:37
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     *
     * @param registry registry
     * @return void
     * @author 王合
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor());
    }

    /**
     * 添加静态资源解析器， 添加页面cache
     *
     * @param registry 静态资源拦截处理
     * @return void 返回参数说明
     * @author 王合
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl.empty().cachePrivate();
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
                .setCacheControl(cacheControl).setCacheControl(cacheControl).setCachePeriod(2592000)
                .resourceChain(true);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.先定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2.添加fastjson的配置信息，比如：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        //处理中文乱码问题(不然出现中文乱码)
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        //3.在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //4.将convert添加到converters当中
        converters.add(fastConverter);
    }
}
