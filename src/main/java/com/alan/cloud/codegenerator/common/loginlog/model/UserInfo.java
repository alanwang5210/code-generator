package com.alan.cloud.codegenerator.common.loginlog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by behelit on 2019/6/13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    //用户编码
    private String userCode;
    //用户姓名
    private String name;
    //警员编号
    private String policeCode;
    //联系电话
    private String userTel;
    //联系电话
    private String virtualPhone;
    //话机号码
    private String userPhone;
    //终端号码
    private String terminalPhone;
    //头像
    private String userPicUrl;
    //备注
    private String userMemo;
    //所属部门
    private String deptCode;
    //所属部门名称
    private String deptName;
    //用户类型
    private String userType;
    //交通工具
    private String userTrafficTool;
    //警力状态
    private String userPoliceStatus;

    //证件号码
    private String cardNum;
    //MAC地址
    private String macAddress;
    //类型int。状态；0-冻结，1-启用；默认值：1。
    private int stat;
    //电子邮箱
    private String email;
    //用户名
    private String userName;
    //用户密码
    private String userPassword;
    //类型int。是否验证mac地址；0-不验证，1-验证；默认值：0。
    private int isAutoJudgeMac;
    //所属IP
    private String ownerIp;
    //SaaS域ID
    private String saasDomainId;
    //PaaS 域ID，用户登录的时候就可以获取到PaaSId,需要注册登陆到某个域。
    private String paasId;
    //SaaS设备、通道CODE。
    private String deviceCode;
    //SaaS用户自定义设备CODE。
    private String userDeviceCode;
    //登录密码，话机虚拟设备都用这个。
    private String loginPassword;
    //PASS层通道唯一编码 。
    private String chnId;
    //虚拟号码SaaS对应的设备Code。
    private String virtualPhoneDevCode;
    //虚拟号码SaaS对应的通道Code。
    private String virtualPhoneChnCode;
    //话机号码SaaS对应的设备Code。
    private String userPhoneDevCode;
    //话机号码SaaS对应的通道Code。
    private String userPhoneChnCode;

    //用于测试
    public static UserInfo generVirtualUser() {
        UserInfo dahuaUserInfo = new UserInfo();

        dahuaUserInfo.setName("virtual");
        dahuaUserInfo.setDeptCode("1");
        return dahuaUserInfo;
    }

}
