package com.alan.cloud.codegenerator.common.OperationLog.controller;

import com.alan.cloud.codegenerator.common.OperationLog.model.SysOperationLog;
import com.alan.cloud.codegenerator.common.OperationLog.service.SysOperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * #操作日志 前端控制器
 * </p>
 *
 * @author citybrain
 * @since 2019-04-24
 */
@RestController
@RequestMapping("/sysOperationLog")
public class SysOperationLogController {


    @Autowired
    private SysOperationLogService sysOperationLogService;


    /**
     * 新增
     */
    @PostMapping("/save")
    public boolean save(SysOperationLog sysOperationLog) {
        return sysOperationLogService.save(sysOperationLog);
    }

    /**
     * 通过id删除
     */
    @PostMapping("/delete")
    public boolean delete(SysOperationLog sysOperationLog) {
        return sysOperationLogService.delete(sysOperationLog);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public boolean update(SysOperationLog sysOperationLog) {
        return sysOperationLogService.update(sysOperationLog);
    }


    /**
     * 查询列表
     */
    @GetMapping("/list")
    public List<SysOperationLog> list(SysOperationLog sysOperationLog) {
        return sysOperationLogService.selectByCondition(sysOperationLog);
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public List<SysOperationLog> page(Page<SysOperationLog> page, SysOperationLog sysOperationLog) {
        return sysOperationLogService.selectByPage(page, sysOperationLog);
    }

}

