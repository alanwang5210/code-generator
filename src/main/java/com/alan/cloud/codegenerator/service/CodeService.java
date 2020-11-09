package com.alan.cloud.codegenerator.service;

import com.alan.cloud.codegenerator.model.DatabaseInfo;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.model.TableInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @王合
 * @2019-10-14 13:45:01
 */
public interface CodeService {

    /**
     * 获取数据库所有表信息
     *
     * @param dbConfig 数据库配置信息
     * @return java.util.List<com.alan.cloud.codegenerator.model.TableInfo> 数据库所有表信息
     * @author 王合
     * @exception/throws
     */
    List<TableInfo> getAllTables(DbConfig dbConfig);

    /**
     * 获取数据库表所有字段信息
     *
     * @param dbConfig  数据库配置信息
     * @param tableName 数据库表名
     * @return com.alan.cloud.codegenerator.model.TableInfo 数据库表信息
     * @author 王合
     * @exception/throws
     */
    TableInfo getFullTableInfo(String tableName, DbConfig dbConfig);

    /**
     * 验证数据库连接
     *
     * @param dbConfig 数据库配置信息
     * @return java.lang.Boolean 验证信息
     * @author 王合
     * @exception/throws
     */
    String validateConnection(DbConfig dbConfig);

    /**
     * 获取数据库信息
     *
     * @param dbConfig 数据库配置信息
     * @return DatabaseInfo 数据库信息
     * @author 王合
     * @exception/throws
     */
    DatabaseInfo getDatabaseInfo(DbConfig dbConfig);
}