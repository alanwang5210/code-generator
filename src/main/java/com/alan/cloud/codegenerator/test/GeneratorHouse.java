package com.alan.cloud.codegenerator.test;

import com.alan.cloud.codegenerator.model.DbConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @Author: 10100
 * @License: (C) Copyright 2020-2050,  Corporation Limited.
 * @Contact: wang.he@cpe-smartcity.com
 * @Date: 2020/9/8 10:25
 * @Description:
 */
public class GeneratorHouse {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        DbConfig dbConfig = new DbConfig();
        dbConfig.setUrl("jdbc:mysql://10.10.120.121:3306/govern_community_smart?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true");
        dbConfig.setPassword("dahuacloud");
        dbConfig.setDriver("com.mysql.cj.jdbc.Driver");
        dbConfig.setUsername("root");
        List<Map<String, String>> maps = getAllAreaFloorMaps(dbConfig);

        List<CommunityHouseBasic> communityHouseBasics = new ArrayList<>();
        maps.forEach(m -> {
            Date date = new Date();
            CommunityHouseBasic communityHouseBasic = new CommunityHouseBasic();

            communityHouseBasic.setStreetId(m.get("streetId"));
            communityHouseBasic.setHouseNum(m.get("houseNum"));

            communityHouseBasic.setCommunityId(m.get("communityId"));
            communityHouseBasic.setAreaId(m.get("areaId"));
            communityHouseBasic.setCountryId("");

            communityHouseBasic.setId(UUID.randomUUID().toString().replaceAll("-", ""));

            int rooms = RANDOM.nextInt(4) + 2;
            communityHouseBasic.setRooms(rooms);
            communityHouseBasic.setHalls(RANDOM.nextInt(rooms - 1) + 1);
            communityHouseBasic.setActLiveNum(RANDOM.nextInt(rooms - 1) + 1);
            communityHouseBasic.setActSepRooms(RANDOM.nextInt(rooms - 1) + 1);
            communityHouseBasic.setLivePersonNum(RANDOM.nextInt(rooms) + 1);

            communityHouseBasic.setAreas(BigDecimal.valueOf(rooms * 30 + RANDOM.nextInt(20)));
            communityHouseBasic.setLiveCondition(String.valueOf(RANDOM.nextInt(5)));
            communityHouseBasic.setIsArrangeHouse(String.valueOf(RANDOM.nextInt(2)));
            communityHouseBasic.setHouseProperty(String.valueOf(RANDOM.nextInt(4)));
            communityHouseBasic.setCarSeatNum(new String[]{"A", "B", "C", "D"}[RANDOM.nextInt(4)] + RANDOM.nextInt(3000) + 1000);
            communityHouseBasic.setStatus("5");
            communityHouseBasic.setIsSafe("0");

            communityHouseBasic.setHzName(RandomValue.getRandomName());
            communityHouseBasic.setHzIdCard(RandomValue.getIdCard(RANDOM.nextInt(2) == 1));
            communityHouseBasic.setHzContactWay(RandomValue.getTel());

            communityHouseBasic.setRemarks("");
            communityHouseBasic.setCreateDate(date);
            communityHouseBasic.setCreateBy("c15bce2315b44b3c8210565a829ca3b6");
            communityHouseBasic.setUpdateDate(date);
            communityHouseBasic.setUpdateBy("c15bce2315b44b3c8210565a829ca3b6");
            communityHouseBasic.setDelFlag("0");

            communityHouseBasics.add(communityHouseBasic);
        });

        System.out.println();
    }

    /**
     * 获取数据库所有表信息
     *
     * @param dbConfig 数据库配置信息
     * @return java.util.List<com.alan.cloud.codegenerator.model.TableInfo> 数据库所有表信息
     * @author 王合
     * @exception/throws
     */
    public static List<Map<String, String>> getAllAreaFloorMaps(DbConfig dbConfig) {

        List<Map<String, String>> tableList = new ArrayList<>();

        Connection conn = getConnection(dbConfig);
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String strSql = "select street_id, community_id, area_id, house_num from  community_area_floor";
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("streetId", rs.getString(1));
                map.put("communityId", rs.getString(2));
                map.put("areaId", rs.getString(3));
                map.put("houseNum", rs.getString(4));
                tableList.add(map);
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
     * 获取数据库连接的函数
     *
     * @param dbConfig 数据库配置信息
     * @return java.lang.Boolean 验证信息
     * @author 王合
     * @exception/throws
     */
    private static Connection getConnection(DbConfig dbConfig) {
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
