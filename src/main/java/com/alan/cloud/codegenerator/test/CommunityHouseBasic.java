package com.alan.cloud.codegenerator.test;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author: 10100
 * @License: (C) Copyright 2020-2050,  Corporation Limited.
 * @Contact: wang.he@cpe-smartcity.com
 * @Date: 2020/7/20 16:44
 * @Description: 房屋基本信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("community_house_basic")
public class CommunityHouseBasic extends SuperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 房屋编号 关联小区楼栋信息表房屋编号
     */
    @TableField("house_num")
    private String houseNum;

    /**
     * 房屋性质 0单元楼 1自建房 2别墅 3其他
     */
    @TableField("house_property")
    private String houseProperty;

    /**
     * 居住现状 0自住 1混住 2出租 3闲置 4其他
     */
    @TableField("live_condition")
    private String liveCondition;

    /**
     * 房间数(室数)
     */
    @TableField("rooms")
    private Integer rooms;

    /**
     * 房屋厅数
     */
    @TableField("halls")
    private Integer halls;

    /**
     * 房屋面积(平方)
     */
    @TableField("areas")
    private BigDecimal areas;

    /**
     * 状态 1-待完善 2-待审核 3-待网格员复核 4-待公众核实 5-待处理 6-已归档
     */
    @TableField("status")
    private String status;

    /**
     * 是否为安置房 0-不是 1-是
     */
    @TableField("is_arrange_house")
    private String isArrangeHouse;

    /**
     * 是否存在安全隐患
     */
    @TableField("is_safe")
    private String isSafe;

    /**
     * 居住人数
     */
    @TableField("live_person_num")
    private Integer livePersonNum;

    /**
     * 实际分隔间数
     */
    @TableField("act_sep_rooms")
    private Integer actSepRooms;

    /**
     * 实际居住间数
     */
    @TableField("act_live_num")
    private Integer actLiveNum;

    /**
     * 车位号
     */
    @TableField("car_seat_num")
    private String carSeatNum;

    /**
     * 户主身份证号
     */
    @TableField("hz_id_card")
    private String hzIdCard;

    /**
     * 户主
     */
    @TableField("hz_name")
    private String hzName;

    /**
     * 户主联系方式
     */
    @TableField("hz_contact_way")
    private String hzContactWay;

    /**
     * 所属街道id
     */
    @TableField("street_id")
    private String streetId;

    /**
     * 所属社区id
     */
    @TableField("community_id")
    private String communityId;

    /**
     * 所属村庄id
     */
    @TableField("country_id")
    private String countryId;

    /**
     * 所属小区id
     */
    @TableField("area_id")
    private String areaId;
}