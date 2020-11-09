package com.alan.cloud.codegenerator.common.loginlog.controller;

import com.alan.cloud.codegenerator.common.loginlog.model.SysUserLoginLog;
import com.alan.cloud.codegenerator.common.loginlog.service.SysUserLoginLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * ##登录日志 前端控制器
 * </p>
 *
 * @author citybrain
 * @since 2019-04-23
 */
@RestController
@RequestMapping("/sysUserLoginLog")
public class SysUserLoginLogController {

    private final SysUserLoginLogService sysUserLoginLogService;

    public SysUserLoginLogController(SysUserLoginLogService sysUserLoginLogService) {
        this.sysUserLoginLogService = sysUserLoginLogService;
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public boolean save(SysUserLoginLog sysUserLoginLog) {
        return sysUserLoginLogService.save(sysUserLoginLog);
    }

    /**
     * 通过id删除
     */
    @PostMapping("/delete")
    public boolean delete(String id) {
        return sysUserLoginLogService.removeById(id);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public boolean update(SysUserLoginLog sysUserLoginLog) {
        return sysUserLoginLogService.updateById(sysUserLoginLog);
    }

    /**
     * 查询列表
     */
    @GetMapping("/list")
    public List<SysUserLoginLog> list(SysUserLoginLog sysUserLoginLog) {
        return sysUserLoginLogService.selectByCondition(sysUserLoginLog);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public List<SysUserLoginLog> page(Page<SysUserLoginLog> page, SysUserLoginLog sysUserLoginLog) {
        return sysUserLoginLogService.selectByPage(page, sysUserLoginLog);
    }
}

