package com.alan.cloud.codegenerator.test;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 10100
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TestModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    //@JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JSONField(serializeUsing = LocalDateTimeSerializer.class)
    private LocalDateTime time;
}
