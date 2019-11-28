package com.alan.cloud.codegenerator.service.impl;

import com.alan.cloud.codegenerator.mapper.DbConfigMapper;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.service.DbConfigService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 数据库信息表 服务实现类
 *
 * @author wanghe
 * @since 2019-11-10
 */
@Slf4j
@Service
@AllArgsConstructor
public class DbConfigServiceImpl extends ServiceImpl<DbConfigMapper, DbConfig> implements DbConfigService {

    private DbConfigMapper dbConfigMapper;

    /**
     * 新增数据库配置信息
     *
     * @param dbConfig 数据库配置信息
     * @return int 返回主键
     * @author 王合
     * @exception/throws
     */
    @Override
    public Integer insert(DbConfig dbConfig) {
        return dbConfigMapper.insert(dbConfig);
    }

    /**
     * 通过id删除数据库配置信息
     *
     * @param dbConfig 数据库配置信息
     * @return boolean 删除状态
     * @author 王合
     * @exception/throws
     */
    @Override
    public boolean delete(DbConfig dbConfig) {
        return dbConfigMapper.delete(dbConfig);
    }

    /**
     * 更新数据库配置信息
     *
     * @param dbConfig 数据库配置信息
     * @return boolean 更新状态
     * @author 王合
     * @exception/throws
     */
    @Override
    public boolean update(DbConfig dbConfig) {
        return dbConfigMapper.update(dbConfig);
    }

    /**
     * 分页查询数据库配置列表
     *
     * @param page     分页
     * @param dbConfig 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.alan.cloud.codegenerator.model.DbConfig> 数据库配置列表
     * @author 王合
     * @exception/throws
     */
    @Override
    public IPage<DbConfig> query(Page<DbConfig> page, DbConfig dbConfig) {
        return dbConfigMapper.query(page, dbConfig);
    }
}
