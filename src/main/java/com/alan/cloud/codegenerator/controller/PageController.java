package com.alan.cloud.codegenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 王合
 * @date 2020-02-14 11:34:33
 */
@Controller
public class PageController {

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

}
