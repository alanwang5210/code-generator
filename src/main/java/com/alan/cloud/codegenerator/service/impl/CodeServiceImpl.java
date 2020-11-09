package com.alan.cloud.codegenerator.service.impl;

import com.alan.cloud.codegenerator.common.enmu.ExceptionTypeEnum;
import com.alan.cloud.codegenerator.common.errorhandler.BaseException;
import com.alan.cloud.codegenerator.model.ColumnInfo;
import com.alan.cloud.codegenerator.model.DatabaseInfo;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.model.TableInfo;
import com.alan.cloud.codegenerator.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @王合
 * @2019-10-14 13:45:01
 */
@Service
@Slf4j
public class CodeServiceImpl implements CodeService {

    private Map<String, Connection> connCache = new HashMap<>();

    /**
     * 获取数据库所有表信息
     *
     * @param dbConfig 数据库配置信息
     * @return java.util.List<com.alan.cloud.codegenerator.model.TableInfo> 数据库所有表信息
     * @author 王合
     * @exception/throws
     */
    @Override
    public List<TableInfo> getAllTables(DbConfig dbConfig) {
        List<TableInfo> tableList = new ArrayList<TableInfo>();

        Connection conn = getConnection(dbConfig);
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String strSql = "";
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select table_name,TABLE_COMMENT from information_schema.tables where table_schema='" + dbConfig.getSchemaName() + "'";
            } else {
                strSql = "select table_name,comments from user_tab_comments where table_type='TABLE' order by table_name";
            }
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                TableInfo table = new TableInfo();
                table.setTableName(rs.getString(1));
                table.setComments(rs.getString(2));
                tableList.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("execute sql occer error", e);
        } finally {
            try {
                closeConnection(conn, stmt, rs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return tableList;
    }

    /**
     * 获取数据库表字段信息
     *
     * @param tableName 表明
     * @param dbConfig  数据库连接配置
     * @return com.alan.cloud.codegenerator.model.TableInfo 返回参数说明
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    @Override
    public TableInfo getFullTableInfo(String tableName, DbConfig dbConfig) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);

        Connection conn = getConnection(dbConfig);
        Statement stmt = null;
        ResultSet rs = null;
        String strSql = "";
        try {
            stmt = conn.createStatement();

            //得到表注解
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select TABLE_COMMENT from information_schema.tables where table_name='" + tableName + "' and table_schema='" + dbConfig.getSchemaName() + "'";
            } else {
                strSql = "select comments from user_tab_comments where table_name='" + tableName + "'";
            }
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                tableInfo.setComments(rs.getString(1));
            }

            //得到字段注解
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select column_name,column_comment,column_type,is_nullable,extra,column_default from Information_schema.columns where table_Name = '" + tableName + "' and table_schema='" + dbConfig.getSchemaName() + "' ORDER BY ORDINAL_POSITION";
            } else {
                strSql = "select z.COLUMN_NAME,c.comments,z.data_type from user_tab_columns z,user_col_comments c where z.TABLE_NAME=c.table_name and z.COLUMN_NAME=c.column_name and z.Table_Name='" + tableName + "'";
            }
            List<ColumnInfo> colList = new ArrayList<>();
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                ColumnInfo colInfo = new ColumnInfo();
                colInfo.setColName(rs.getString(1));
                colInfo.setComments(rs.getString(2));
                if (dbConfig.getUrl().indexOf("mysql") > 0) {
                    colInfo.setColType(rs.getString(3));
                    colInfo.setDefaultValue(rs.getString(6));
                    colInfo.setNullable("YES".equals(rs.getString(4)));
                } else {
                    colInfo.setColType(rs.getString(3));
                }
                if (!StringUtils.isEmpty(rs.getString(5))) {
                    colInfo.setExtra(rs.getString(5));
                } else {
                    colInfo.setExtra("");
                }
                colList.add(colInfo);
            }
            tableInfo.setListColumn(colList);
        } catch (SQLException e) {
            throw new BaseException(ExceptionTypeEnum.SYSTEM_EXCEPTION, String.format("Execute sql  %s error", strSql), e);
        } finally {
            closeConnection(conn, stmt, rs);
        }

        return tableInfo;
    }

    /**
     * 获取数据库连接信息
     *
     * @param dbConfig 数据库配置信息
     * @return java.lang.Boolean 验证信息
     * @author 王合
     * @exception/throws
     */
    @Override
    public DatabaseInfo getDatabaseInfo(DbConfig dbConfig) {
        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setDriver(dbConfig.getDriver());
        databaseInfo.setUrl(dbConfig.getUrl());
        databaseInfo.setDbType(dbConfig.getDbType());
        databaseInfo.setSchemaName(dbConfig.getSchemaName());
        databaseInfo.setUsername(dbConfig.getUsername());

        Connection conn = getConnection(dbConfig);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            String strSql;
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select version()";
                rs = stmt.executeQuery(strSql);

                while (rs.next()) {
                    databaseInfo.setVersion(rs.getString(1));
                }
            } else {
                strSql = "select * from product_component_version";
                rs = stmt.executeQuery(strSql);
                while (rs.next()) {
                    databaseInfo.setVersion(rs.getString(2));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("execute sql occer error", e);
        } finally {
            closeConnection(conn, stmt, rs);
        }
        return databaseInfo;
    }

    /**
     * 获取数据库连接的函数
     *
     * @param dbConfig 数据库配置信息
     * @return java.lang.Boolean 验证信息
     * @author 王合
     * @exception/throws
     */
    private Connection getConnection(DbConfig dbConfig) {
        Connection con = null;
        try {
            Class.forName(dbConfig.getDriver());
            con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());

        } catch (Exception e) {
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con;
    }

    /**
     * 验证数据库连接
     *
     * @param dbConfig 数据库配置信息
     * @return java.lang.Boolean 验证信息
     * @author 王合
     * @exception/throws
     */
    @Override
    public String validateConnection(DbConfig dbConfig) {
        //创建用于连接数据库的Connection对象
        Connection con = null;
        try {
            // 加载数据驱动
            Class.forName(dbConfig.getDriver());

            con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
            if (con == null) {
                return "数据库连接失败！";
            }
        } catch (SQLNonTransientConnectionException e) {
            String state = e.getSQLState();
            if ("08001".equals(state)) {
                return "数据库链接地址缺失serverTimezone！";
            }
            return "数据库连接失败！";
        } catch (ClassNotFoundException e) {
            return "数据库驱动不存在！ 请联系管理员！";
        } catch (SQLException throwables) {
            return "数据库连接失败！";
        } finally {
            closeConnection(con, null, null);
        }
        return "数据库连接成功！";
    }

    /**
     * 关闭数据库连接
     *
     * @param conn 数据库连接
     * @param st   statement
     * @param rs   结果集
     * @return void
     * @author 王合
     * @exception/throws
     */
    public static void closeConnection(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
