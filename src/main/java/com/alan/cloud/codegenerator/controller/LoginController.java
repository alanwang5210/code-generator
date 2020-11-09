package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.model.SysUser;
import com.alan.cloud.codegenerator.utils.QRCodeUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 *
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String home() {
        return "/views/index/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "/views/index/login";
    }

    @RequestMapping("/index")
    public String init(Model model) {
        try {
            SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("users", sysUser);
        } catch (Exception e) {
            return "redirect:/login";
        }
        return "/views/index/index";
    }

    @ResponseBody
    @RequestMapping("/login-success")
    public Boolean loginSuccess() {
        try {
            SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @ResponseBody
    @GetMapping("/session")
    public String login12(HttpSession session) {
        return JSON.toJSONString(session);
    }

    /**
     * 二维码
     *
     * @param request
     * @param response
     */
    @RequestMapping("/qrcode")
    public void qrcode(HttpServletRequest request, HttpServletResponse response) {
        StringBuffer url = request.getRequestURL();
        // 域名
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();

        // 再加上请求链接
        String requestUrl = tempContextUrl + "/index";
        try {
            OutputStream os = response.getOutputStream();
            BufferedImage bufferedImage = QRCodeUtil.encode(requestUrl, "D:\\testerweima.png", os, true);

            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
