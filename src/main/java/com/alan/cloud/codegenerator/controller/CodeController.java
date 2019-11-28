package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.conf.MySqlTypeConvertExt;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.model.TableStrategyConfig;
import com.alan.cloud.codegenerator.service.DbConfigService;
import com.alan.cloud.codegenerator.utils.FileUtil;
import com.alan.cloud.codegenerator.utils.ZipFileUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "code generator")
@RestController
@RequestMapping("code")
@Slf4j
public class CodeController {

    @Autowired
    private DbConfigService dbConfigService;

    @Autowired
    protected HttpServletRequest request;

    /**
     * 生成代码的核心逻辑
     *
     * @param response            参数说明
     * @param tableStrategyConfig 参数说明
     * @return void 返回参数说明
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    @PostMapping(value = "/generate")
    private void buildCodes(HttpServletResponse response, @RequestBody TableStrategyConfig tableStrategyConfig) {

        Long id = tableStrategyConfig.getDbId();
        DbConfig dbConfig = dbConfigService.getById(id);

        AutoGenerator mpg = new AutoGenerator();
        /*全局配置*/
        GlobalConfig gc = new GlobalConfig();
        /*生成文件的输出目录【默认 D 盘根目录】*/
        gc.setOutputDir(request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/upload/" + request.getSession().getId());
        /*开启 swagger2 模式*/
        gc.setSwagger2(true);
        /*是否覆盖已有文件 */
        gc.setFileOverride(true);
        /*开启 ActiveRecord 模式*/
        gc.setActiveRecord(true);
        /*是否在xml中添加二级缓存配置*/
        gc.setEnableCache(false);
        /*开启 BaseResultMap*/
        gc.setBaseResultMap(true);
        /*开启 baseColumnList*/
        gc.setBaseColumnList(true);
        /*开发人员*/
        gc.setAuthor(tableStrategyConfig.getAuthor());
        /**
         * 各层文件名称方式，例如： %sAction 生成 UserAction
         * %s 为占位符
         */
        gc.setEntityName(tableStrategyConfig.getEntityName());
        gc.setMapperName(tableStrategyConfig.getMapperName());
        gc.setXmlName(tableStrategyConfig.getXmlName());
        gc.setServiceName(tableStrategyConfig.getServiceName());
        gc.setServiceImplName(tableStrategyConfig.getServiceImplName());
        gc.setControllerName(tableStrategyConfig.getControllerName());
        mpg.setGlobalConfig(gc);

        /*数据源配置*/
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(dbConfig.getDriver().indexOf("mysql") > -1 ? DbType.MYSQL : DbType.ORACLE);
        dsc.setDriverName(dbConfig.getDriver());
        dsc.setUsername(dbConfig.getUsername());
        dsc.setPassword(dbConfig.getPassword());
        dsc.setUrl(dbConfig.getUrl());
        dsc.setTypeConvert(new MySqlTypeConvertExt());
        mpg.setDataSource(dsc);

        /* 包配置*/
        PackageConfig pc = new PackageConfig();
        /*pc.setParent("com");*/
        pc.setModuleName(tableStrategyConfig.getModelName());
        pc.setEntity(tableStrategyConfig.getEntityPackage());
        pc.setService(tableStrategyConfig.getServicePackage());
        pc.setServiceImpl(tableStrategyConfig.getServiceImplPackage());
        pc.setMapper(tableStrategyConfig.getMapperPackage());
        pc.setController(tableStrategyConfig.getControllerPackage());
        pc.setXml(tableStrategyConfig.getXmlName());
        mpg.setPackageInfo(pc);

        /*注入自定义配置，可以在 VM 中使用 cfg.entityLombokModel 设置的值*/
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("entityLombokModel", true);
                map.put("restControllerStyle", true);
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        /* 自定义模板配置，可以 copy 源码 mybatis-plus-generator/template 下面内容修改，
         放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称 */
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/gen-template/controller.java.vm");
        tc.setEntity("/templates/gen-template/entity.java.vm");
        tc.setMapper("/templates/gen-template/mapper.java.vm");
        tc.setXml("/templates/gen-template/mapper.xml.vm");
        tc.setService("/templates/gen-template/service.java.vm");
        tc.setServiceImpl("/templates/gen-template/serviceImpl.java.vm");
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /* 表前缀 */
        if (StringUtils.isNotEmpty(tableStrategyConfig.getPrefix())) {
            strategy.setTablePrefix(tableStrategyConfig.getPrefix());
        }
        /*需要生成的表*/
        strategy.setInclude(tableStrategyConfig.getTableName().split(","));
        /*数据库表映射到实体的命名策略 - 下划线转驼峰命名*/
        strategy.setNaming(NamingStrategy.underline_to_camel);
        /*数据库表字段映射到实体的命名策略 - 下划线转驼峰命名*/
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        /*Boolean类型字段是否移除is前缀（默认 false）*/
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        /*【实体】是否为lombok模型（默认 false）*/
        strategy.setEntityLombokModel(true);
        /*生成 <code>@RestController</code> 控制器*/
        strategy.setRestControllerStyle(true);
        /*是否生成实体时，生成字段注解*/
        strategy.setEntityTableFieldAnnotationEnable(true);
        /*controller mapping 驼峰转连字符*/
        strategy.setControllerMappingHyphenStyle(false);
        /*是否大写命名*/
        strategy.setCapitalMode(false);
        mpg.setStrategy(strategy);

        // 执行生成
        mpg.execute();

        //打包下载
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=src.zip");
        response.addHeader("Access-Control-Allow-Origin", "*");

        try {
            ZipFileUtil zip = new ZipFileUtil();
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            String fileName = request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/upload/" + request.getSession().getId();
            File ff = new File(fileName);
            if (!ff.exists()) {
                ff.mkdirs();
            }
            zip.zip(ff, zos, "");
            zos.flush();
            zos.close();
            //删除目录
            FileUtil.deleteDirectory(request.getSession().getServletContext().getRealPath("/") + "/WEB-INF/upload/" + request.getSession().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

