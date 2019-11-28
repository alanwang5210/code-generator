package com.alan.cloud.codegenerator.conf;

import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * @王合
 * @2019-11-24 20:20:37
 */
public enum DbColumnTypeExt implements IColumnType {
    // java8 新时间类型
    OFFSET_DATE_TIME("OffsetDateTime", "java.time.OffsetDateTime");

    /**
     * 类型
     */
    private final String type;

    /**
     * 包路径
     */
    private final String pkg;

    DbColumnTypeExt(final String type, final String pkg) {
        this.type = type;
        this.pkg = pkg;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getPkg() {
        return pkg;
    }
}
