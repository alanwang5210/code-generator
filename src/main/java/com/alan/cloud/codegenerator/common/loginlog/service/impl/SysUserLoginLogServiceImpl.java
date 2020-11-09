package com.alan.cloud.codegenerator.common.loginlog.service.impl;

import com.alan.cloud.codegenerator.common.loginlog.mapper.SysUserLoginLogMapper;
import com.alan.cloud.codegenerator.common.loginlog.model.SysUserLoginLog;
import com.alan.cloud.codegenerator.common.loginlog.service.SysUserLoginLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * ##登录日志 服务实现类
 * </p>
 *
 * @author citybrain
 * @since 2019-04-23
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserLoginLogServiceImpl extends ServiceImpl<SysUserLoginLogMapper, SysUserLoginLog> implements SysUserLoginLogService {

    private SysUserLoginLogMapper sysUserLoginLogMapper;

    /**
     * 查询列表
     */
    @Override
    public List<SysUserLoginLog> selectByCondition(SysUserLoginLog sysUserLoginLog) {
        return sysUserLoginLogMapper.selectByCondition(sysUserLoginLog);
    }

    /**
     * 分页查询
     */
    @Override
    public List<SysUserLoginLog> selectByPage(Page page, SysUserLoginLog sysUserLoginLog) {
        return sysUserLoginLogMapper.selectByPage(page, sysUserLoginLog);
    }
}
