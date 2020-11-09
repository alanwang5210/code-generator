package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.common.commonresponse.HttpResult;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.model.TableInfo;
import com.alan.cloud.codegenerator.service.CodeService;
import com.alan.cloud.codegenerator.service.DbConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @王合
 * @2019-11-24 20:19:57
 */
@RestController
@RequestMapping("/table")
@Api(tags = "数据库表操作")
public class TableController {

    @Autowired
    private CodeService codeService;

    @Autowired
    private DbConfigService dbConfigService;

    /**
     * 获取数据库所有表信息
     *
     * @param id 数据库配置ID
     * @return HttpResult<java.util.List < com.alan.cloud.codegenerator.model.TableInfo>> 返回参数说明
     * @author 王合
     * @exception/throws
     */
    @GetMapping("/list")
    @ApiOperation(value = "数据表列表", notes = "获取所有数据库列表")
    public HttpResult<List<TableInfo>> tableList(String id) {
        List<TableInfo> tableList;
        try {
            DbConfig dbConfig = dbConfigService.getById(id);
            tableList = codeService.getAllTables(dbConfig);

        } catch (Exception e) {
            return HttpResult.error("数据库链接失败");
        }
        return HttpResult.success(tableList);
    }
}