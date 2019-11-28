package com.alan.cloud.codegenerator.safetymanager;

import com.alan.cloud.codegenerator.safetymanager.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @王合
 * @2019-10-18 16:10:37
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 注册拦截器
     *
     * @param registry registry
     * @return void
     * @author 王合
     * @exception/throws
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor());
        super.addInterceptors(registry);
    }

    /**
     * 添加静态资源解析器， 添加页面cache
     *
     * @param registry 静态资源拦截处理
     * @return void 返回参数说明
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        CacheControl cacheControl = CacheControl.empty().cachePrivate();
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
                .setCacheControl(cacheControl).setCacheControl(cacheControl).setCachePeriod(2592000)
                .resourceChain(true);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}
