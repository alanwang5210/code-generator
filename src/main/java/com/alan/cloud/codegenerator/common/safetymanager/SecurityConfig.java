package com.alan.cloud.codegenerator.common.safetymanager;

import com.alan.cloud.codegenerator.common.safetymanager.config.IgnoreUrlConfigReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author 王合
 * @date 2019-10-14 13:45:01
 * spring-security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final IgnoreUrlConfigReader ignoreUrlConfigReader;

    private final CustomAuthenticationProvider provider;

    public SecurityConfig(IgnoreUrlConfigReader ignoreUrlConfigReader, CustomAuthenticationProvider provider) {
        this.ignoreUrlConfigReader = ignoreUrlConfigReader;
        this.provider = provider;
    }

    /**
     * 添加自定义验证
     *
     * @param auth 认证
     * @return void
     * @author 王合
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    /**
     * 配置拦截URL、设置权限等安全控制
     * 配置登录登出页面及跳过安全验证页面
     *
     * @param http Spring Security 安全配置
     * @return void
     * @author 王合
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //允许使用iframe 嵌套
        //表单登录，permitAll()表示这个不需要验证 登录页面，登录失败页面
        http.headers().frameOptions().disable();
        // 任意访问
        http
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login/process")
                // 自定义登入失败页面，前端可以通过url中是否有error来提供友好的用户登入提示
                .failureUrl("/login?error")
                .successForwardUrl("/login-success").permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .csrf().disable();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();

        /* 不需要验证的url */
        ignoreUrlConfigReader.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated();
    }
}