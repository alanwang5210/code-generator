package com.alan.cloud.codegenerator.common.OperationLog.mapper;

import com.alan.cloud.codegenerator.common.OperationLog.model.SysOperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * #操作日志 Mapper 接口
 * </p>
 *
 * @author citybrain
 * @since 2019-04-24
 */
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
    /**
     * 新增
     */
    boolean save(SysOperationLog entity);

    /**
     * 通过id删除
     */
    boolean delete(SysOperationLog entity);

    /**
     * 修改
     */
    boolean update(SysOperationLog entity);


    /**
     * 查询列表
     */
    List<SysOperationLog> selectByCondition(SysOperationLog entity);

    /**
     * 分页查询
     */
    List<SysOperationLog> selectByPage(Page page, SysOperationLog entity);

}
