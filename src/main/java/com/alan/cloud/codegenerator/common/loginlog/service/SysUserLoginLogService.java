package com.alan.cloud.codegenerator.common.loginlog.service;

import com.alan.cloud.codegenerator.common.loginlog.model.SysUserLoginLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * ##登录日志 服务类
 * </p>
 *
 * @author citybrain
 * @since 2019-04-23
 */
public interface SysUserLoginLogService extends IService<SysUserLoginLog> {




        /**
        * 查询列表
        */
         List<SysUserLoginLog> selectByCondition(SysUserLoginLog sysUserLoginLog);

        /**
        * 分页查询
        */
         List<SysUserLoginLog> selectByPage(Page<SysUserLoginLog> page, SysUserLoginLog sysUserLoginLog);
}
