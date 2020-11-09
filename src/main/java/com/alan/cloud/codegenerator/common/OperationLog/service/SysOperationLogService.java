package com.alan.cloud.codegenerator.common.OperationLog.service;

import com.alan.cloud.codegenerator.common.OperationLog.model.SysOperationLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * #操作日志 服务类
 * </p>
 *
 * @author citybrain
 * @since 2019-04-24
 */
public interface SysOperationLogService extends IService<SysOperationLog> {

    /**
     * 新增
     */
    @Override
    boolean save(SysOperationLog sysOperationLog);

    /**
     * 通过id删除
     */
    boolean delete(SysOperationLog sysOperationLog);

    /**
     * 修改
     */
    boolean update(SysOperationLog sysOperationLog);


    /**
     * 查询列表
     */
    List<SysOperationLog> selectByCondition(SysOperationLog sysOperationLog);

    /**
     * 分页查询
     */
    List<SysOperationLog> selectByPage(Page<SysOperationLog> page, SysOperationLog sysOperationLog);
}
