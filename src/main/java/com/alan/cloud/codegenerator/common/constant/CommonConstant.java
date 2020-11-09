package com.alan.cloud.codegenerator.common.constant;

/**
 * 基本配置常量
 *
 * @author wh
 * @date 2019-10-13 14:29:55
 */
public interface CommonConstant {

    /**
     * 系统常量
     */
    class System {
        /**
         * 应用名称——中文
         */
        public static final String APP_NAME_CH = "代码生成工具";

        /**
         * 应用名称——英文
         */
        public static final String APP_NAME_EN = "code-generator";

        /**
         * 功能模块 -- 代码生成
         */
        public static final String MODEL_DMSC = "代码生成";
    }

    /**
     * 错误信息
     */
    class ErrorMessage {
        /**
         * 用户名密码为空
         */
        public static final String UP_IS_NULL = "用户名密码为空!";
    }

    /**
     * 代码模板位置
     */
    class TemplateLocation {
        /**
         * controller 模板地址
         */
        public static final String CONTROLLER_LOCATION = "/templates/gen-template/controller.java.vm";

        /**
         * entity 模板地址
         */
        public static final String ENTITY_LOCATION = "/templates/gen-template/entity.java.vm";

        /**
         * mapper 模板地址
         */
        public static final String MAPPER_LOCATION = "/templates/gen-template/mapper.java.vm";

        /**
         * mapper xml 模板地址
         */
        public static final String MAPPER_XML_LOCATION = "/templates/gen-template/mapper.xml.vm";

        /**
         * service 模板地址
         */
        public static final String SERVICE_LOCATION = "/templates/gen-template/service.java.vm";

        /**
         * serviceImpl 模板地址
         */
        public static final String SERVICE_IMPL_LOCATION = "/templates/gen-template/serviceImpl.java.vm";
    }

    class KaptchaProperties {
        /**
         * 默认生成图形验证码宽度
         */
        public static final String DEFAULT_IMAGE_WIDTH = "100";

        /**
         * 默认生成图像验证码高度
         */
        public static final String DEFAULT_IMAGE_HEIGHT = "40";

        /**
         * 默认生成图形验证码长度
         */
        public static final String DEFAULT_IMAGE_LENGTH = "4";

        /**
         * 默认生成图形验证码过期时间
         */
        public static final int DEFAULT_IMAGE_EXPIRE = 60;
        /**
         * 边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.
         */
        public static final String DEFAULT_COLOR_FONT = "black";

        /**
         * 图片边框
         */
        public static final String DEFAULT_IMAGE_BORDER = "no";
        /**
         * 默认图片间隔
         */
        public static final String DEFAULT_CHAR_SPACE = "5";

        /**
         * 默认保存code的前缀
         */
        public static final String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";
        /**
         * 验证码文字大小
         */
        public static final String DEFAULT_IMAGE_FONT_SIZE = "30";
    }
}
