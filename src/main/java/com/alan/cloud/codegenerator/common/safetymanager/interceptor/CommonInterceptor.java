package com.alan.cloud.codegenerator.common.safetymanager.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @王合
 * @2019-10-15 16:22:16
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o) throws Exception {
//
//        if (!StringUtils.equals(request.getServletPath(), "/login")) {
//            try {
//                SysUser user = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//                if (user == null) {
//                    httpServletResponse.sendRedirect("/login");
//                    return false;
//                }
//            } catch (Exception e) {
//                httpServletResponse.sendRedirect("/login");
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

        List<Integer> errorCodeList = Arrays.asList(404);
        int status = response.getStatus();

        if (errorCodeList.contains(status)) {
            System.out.println("[error: " + request.getRequestURI() + "]");
//            modelAndView.addObject("code", status);
//            modelAndView.setViewName("/views/error");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
