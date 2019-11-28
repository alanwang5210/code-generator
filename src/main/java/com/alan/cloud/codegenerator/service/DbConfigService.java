package com.alan.cloud.codegenerator.service;

import com.alan.cloud.codegenerator.model.DbConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * #数据库信息表 服务类
 * </p>
 *
 * @author wanghe
 * @since 2019-11-10
 */
public interface DbConfigService extends IService<DbConfig> {

    /**
     * 新增数据库配置信息
     *
     * @param dbConfig 数据库配置信息
     * @return int 返回主键
     * @author 王合
     * @exception/throws
     */
    Integer insert(DbConfig dbConfig);

    /**
     * 通过id删除数据库配置信息
     *
     * @param dbConfig 数据库配置信息
     * @return boolean 删除状态
     * @author 王合
     * @exception/throws
     */
    boolean delete(DbConfig dbConfig);

    /**
     * 更新数据库配置信息
     *
     * @param dbConfig 数据库配置信息
     * @return boolean 更新状态
     * @author 王合
     * @exception/throws
     */
    boolean update(DbConfig dbConfig);

    /**
     * 分页查询数据库配置列表
     *
     * @param page     分页
     * @param dbConfig 查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.alan.cloud.codegenerator.model.DbConfig> 数据库配置列表
     * @author 王合
     * @exception/throws
     */
    IPage<DbConfig> query(Page<DbConfig> page, DbConfig dbConfig);
}
