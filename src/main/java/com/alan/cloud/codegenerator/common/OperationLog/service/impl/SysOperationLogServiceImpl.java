package com.alan.cloud.codegenerator.common.OperationLog.service.impl;

import com.alan.cloud.codegenerator.common.OperationLog.mapper.SysOperationLogMapper;
import com.alan.cloud.codegenerator.common.OperationLog.model.SysOperationLog;
import com.alan.cloud.codegenerator.common.OperationLog.service.SysOperationLogService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * #操作日志 服务实现类
 * </p>
 *
 * @author citybrain
 * @since 2019-04-24
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements SysOperationLogService {

    private SysOperationLogMapper sysOperationLogMapper;

    /**
     * 新增
     */
    @Override
    public boolean save(SysOperationLog sysOperationLog) {
        return sysOperationLogMapper.save(sysOperationLog);
    }

    /**
     * 通过id删除
     */
    @Override
    public boolean delete(SysOperationLog sysOperationLog) {
        return sysOperationLogMapper.delete(sysOperationLog);
    }

    /**
     * 修改
     */
    @Override
    public boolean update(SysOperationLog sysOperationLog) {
        return sysOperationLogMapper.update(sysOperationLog);
    }


    /**
     * 查询列表
     */
    @Override
    public List<SysOperationLog> selectByCondition(SysOperationLog sysOperationLog) {
        return sysOperationLogMapper.selectByCondition(sysOperationLog);
    }

    ;

    /**
     * 分页查询
     */
    @Override
    public List<SysOperationLog> selectByPage(Page page, SysOperationLog sysOperationLog) {
        return sysOperationLogMapper.selectByPage(page, sysOperationLog);
    }

    ;
}
