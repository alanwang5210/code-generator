package com.alan.cloud.codegenerator.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SuperEntity implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;

    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    /**
     * 0-正常，1-删除
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT_UPDATE)
    @TableLogic
    private String delFlag;

    @TableField(value = "remarks")
    private String remarks;

}
