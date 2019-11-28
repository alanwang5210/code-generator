package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.model.SysUser;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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

    /**
     * 显示数据库列表
     */
    @RequestMapping(value = "/database-page")
    public String databaseList() {
        return "/views/db/database";
    }

    /**
     * 显示数据表
     */
    @GetMapping("/table-page")
    public String toTableList(Model model, String databaseId) {
        model.addAttribute("databaseId", databaseId);
        return "/views/db/table";
    }

    /**
     * 显示字段列表
     */
    @GetMapping("/column-page")
    public String toColumnList(Model model, String tableName, String databaseId) {
        model.addAttribute("tableName", tableName);
        model.addAttribute("databaseId", databaseId);
        return "/views/db/column";
    }

    @ResponseBody
    @GetMapping("/session")
    public String login12(HttpSession session) {
        return JSON.toJSONString(session);
    }

}
