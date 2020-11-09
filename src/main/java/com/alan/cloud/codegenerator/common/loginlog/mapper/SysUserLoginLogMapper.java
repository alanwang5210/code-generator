package com.alan.cloud.codegenerator.common.loginlog.mapper;

import com.alan.cloud.codegenerator.common.loginlog.model.SysUserLoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * ##登录日志 Mapper 接口
 * </p>
 *
 * @author citybrain
 * @since 2019-04-23
 */
public interface SysUserLoginLogMapper extends BaseMapper<SysUserLoginLog> {

    /**
     * 查询列表
     */
    List<SysUserLoginLog> selectByCondition(SysUserLoginLog entity);

    /**
     * 分页查询
     */
    List<SysUserLoginLog> selectByPage(Page page, SysUserLoginLog entity);

}
