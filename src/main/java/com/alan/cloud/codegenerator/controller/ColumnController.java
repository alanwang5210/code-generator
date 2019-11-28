package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.common.entity.HttpResult;
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

/**
 * @王合
 * @2019-11-24 20:20:37
 */
@RestController
@RequestMapping("/column")
@Api(tags = "数据库表字段")
public class ColumnController {


    @Autowired
    private CodeService codeService;

    @Autowired
    private DbConfigService dbConfigService;

    /**
     * 获取数据库表所有字段信息
     *
     * @param tableName 数据库表名
     * @param id        数据库配置信息
     * @return HttpResult<com.alan.cloud.codegenerator.model.TableInfo> 数据库表信息
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    @GetMapping("/list")
    @ApiOperation(value = "数据库配置列表", notes = "获取所有数据库配置列表")
    public HttpResult<TableInfo> columnList(String tableName, Long id) {
        DbConfig dbConfig = dbConfigService.getById(id);
        TableInfo tableInfo = codeService.getAllColumns(tableName, dbConfig);
        return HttpResult.success(tableInfo);
    }
}
