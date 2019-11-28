package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.common.entity.HttpResult;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.model.qo.DbConfigQO;
import com.alan.cloud.codegenerator.service.CodeService;
import com.alan.cloud.codegenerator.service.DbConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * #数据库信息表 前端控制器
 * </p>
 *
 * @author wanghe
 * @since 2019-11-10
 */
@RestController
@RequestMapping("/database")
@Api(tags = "数据库配置")
public class DatabaseController {

    @Autowired
    private DbConfigService dbConfigService;

    @Autowired
    private CodeService codeService;

    /**
     * 分页查询数据库配置列表
     *
     * @param dbConfigQO 查询参数
     * @return HttpResult<IPage < DbConfig>> 数据库配置列表
     * @author 王合
     * @exception/throws
     */
    @PostMapping("/list")
    @ApiOperation(value = "数据库配置列表", notes = "获取所有数据库配置列表")
    public HttpResult<IPage<DbConfig>> getDbList(@RequestBody DbConfigQO dbConfigQO) {
        Page page = new Page();
        if (dbConfigQO.getSize() == null) {
            page.setSize(10);
        } else {
            page.setSize(dbConfigQO.getSize());
        }

        if (dbConfigQO.getCurrent() == null) {
            page.setCurrent(1);
        } else {
            page.setCurrent(dbConfigQO.getCurrent());
        }

        DbConfig dbConfig = new DbConfig();
        BeanUtils.copyProperties(dbConfigQO, dbConfig);

        QueryWrapper<DbConfig> wrapper = new QueryWrapper<>();
        wrapper.setEntity(dbConfig);
        IPage<DbConfig> dbConfigIPage = dbConfigService.query(page, dbConfig);
        return HttpResult.success(dbConfigIPage);
    }

    /**
     * 添加数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.Integer> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "添加数据库配置", notes = "添加数据库配置")
    public HttpResult<Integer> save(@RequestBody DbConfig dbConfig) {
        Integer id = dbConfigService.insert(dbConfig);
        return HttpResult.success(id);
    }

    /**
     * 根据ID获取数据库配置
     *
     * @param id 数据库配置主键ID
     * @return HttpResult<DbConfig> 数据库配置信息
     * @author 王合
     * @exception/throws
     */
    @GetMapping("/getByDbId")
    @ApiOperation(value = "根据ID获取数据库配置", notes = "根据ID获取数据库配置")
    public HttpResult<DbConfig> getByDbId(Long id) {
        DbConfig dbConfig = dbConfigService.getById(id);
        return HttpResult.success(dbConfig);
    }

    /**
     * 修改数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.String> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/edit")
    @ApiOperation(value = "修改数据库配置", notes = "修改数据库配置")
    public HttpResult<String> edit(@RequestBody DbConfig dbConfig) {
        dbConfigService.update(dbConfig);
        return HttpResult.success("修改成功");
    }

    /**
     * 删除数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.String> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除数据库配置", notes = "删除数据库配置")
    public HttpResult<String> delete(@RequestBody DbConfig dbConfig) {
        dbConfigService.delete(dbConfig);
        return HttpResult.success("删除成功");
    }

    /**
     * 测试数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.String> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/validateConnection")
    @ApiOperation(value = "测试数据库配置", notes = "测试数据库配置")
    public HttpResult<String> validateConnection(@RequestBody DbConfig dbConfig) {
        Boolean result = false;
        Long id = dbConfig.getId();
        if (id != null) {
            dbConfig = dbConfigService.getById(id);
            if (dbConfig == null) {
                return HttpResult.error("数据库配置不存在!");
            }
        }

        result = codeService.validateConnection(dbConfig);
        if (result) {
            return HttpResult.success("数据库连接成功");
        } else {
            return HttpResult.error("数据库连接失败");
        }
    }
}

