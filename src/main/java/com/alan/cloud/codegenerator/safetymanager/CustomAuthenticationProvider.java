package com.alan.cloud.codegenerator.safetymanager;

import com.alan.cloud.codegenerator.model.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * @王合
 * @2019-10-15 16:22:16
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * 自定义登录验证
     *
     * @param authentication
     * @return org.springframework.security.core.Authentication
     * @author 王合
     * @exception/throws
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 获取表单输入中返回的用户名;
        String userName = authentication.getName();
        // 表单中输入的密码
        String password = (String) authentication.getCredentials();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new BadCredentialsException("用户名密码为空!");
        }
        // 这里构建来判断用户是否存在和密码是否正确
        SysUser sysUser = new SysUser();
        sysUser.setUsername(userName);
        sysUser.setPassword(password);
        return new UsernamePasswordAuthenticationToken(sysUser, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
