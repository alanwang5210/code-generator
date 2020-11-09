package com.alan.cloud.codegenerator.controller;

import com.alan.cloud.codegenerator.enmu.DatabaseEnmu;
import com.alan.cloud.codegenerator.enmu.TableEnmu;
import com.alan.cloud.codegenerator.model.DatabaseInfo;
import com.alan.cloud.codegenerator.model.DbConfig;
import com.alan.cloud.codegenerator.model.TableInfo;
import com.alan.cloud.codegenerator.model.qo.DbConfigQO;
import com.alan.cloud.codegenerator.service.CodeService;
import com.alan.cloud.codegenerator.service.DbConfigService;
import com.alan.cloud.codegenerator.utils.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * #数据库信息表 前端控制器
 * </p>
 *
 * @author wanghe
 * @since 2019-11-10
 */
@RestController
@RequestMapping("/database")
@Api(tags = "数据库配置")
public class DatabaseController {

    private final DbConfigService dbConfigService;

    private final CodeService codeService;

    private final ExcelUtil excelUtil;

    public DatabaseController(DbConfigService dbConfigService, CodeService codeService, ExcelUtil excelUtil) {
        this.dbConfigService = dbConfigService;
        this.codeService = codeService;
        this.excelUtil = excelUtil;
    }

    /**
     * 分页查询数据库配置列表
     *
     * @param dbConfigQO 查询参数
     * @return HttpResult<IPage < DbConfig>> 数据库配置列表
     * @author 王合
     * @exception/throws
     */
    @PostMapping("/list")
    @ApiOperation(value = "数据库配置列表", notes = "获取所有数据库配置列表")
    public IPage<DbConfig> getDbList(@RequestBody DbConfigQO dbConfigQO) {
        Page page = new Page();
        if (dbConfigQO.getSize() == null) {
            page.setSize(10);
        } else {
            page.setSize(dbConfigQO.getSize());
        }

        if (dbConfigQO.getCurrent() == null) {
            page.setCurrent(1);
        } else {
            page.setCurrent(dbConfigQO.getCurrent());
        }

        DbConfig dbConfig = new DbConfig();
        BeanUtils.copyProperties(dbConfigQO, dbConfig);

        QueryWrapper<DbConfig> wrapper = new QueryWrapper<>();
        wrapper.setEntity(dbConfig);
        IPage<DbConfig> dbConfigIPage = dbConfigService.query(page, dbConfig);
        return dbConfigIPage;
    }

    /**
     * 添加数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.Integer> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/save")
    @ApiOperation(value = "添加数据库配置", notes = "添加数据库配置")
    public Integer save(@RequestBody DbConfig dbConfig) {
        Integer id = dbConfigService.insert(dbConfig);
        return id;
    }

    /**
     * 根据ID获取数据库配置
     *
     * @param id 数据库配置主键ID
     * @return HttpResult<DbConfig> 数据库配置信息
     * @author 王合
     * @exception/throws
     */
    @GetMapping("/getByDbId")
    @ApiOperation(value = "根据ID获取数据库配置", notes = "根据ID获取数据库配置")
    public DbConfig getByDbId(Long id) {
        DbConfig dbConfig = dbConfigService.getById(id);
        return dbConfig;
    }

    /**
     * 修改数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.String> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/edit")
    @ApiOperation(value = "修改数据库配置", notes = "修改数据库配置")
    public boolean edit(@RequestBody DbConfig dbConfig) {
        dbConfigService.update(dbConfig);
        return true;
    }

    /**
     * 删除数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.String> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除数据库配置", notes = "删除数据库配置")
    public boolean delete(@RequestBody DbConfig dbConfig) {
        dbConfigService.delete(dbConfig);
        return true;
    }

    /**
     * 测试数据库配置
     *
     * @param dbConfig 数据库配置信息
     * @return com.alan.cloud.codegenerator.common.entity.HttpResult<java.lang.String> 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/validateConnection")
    @ApiOperation(value = "测试数据库配置", notes = "测试数据库配置")
    public String validateConnection(@RequestBody DbConfig dbConfig) {
        String result = codeService.validateConnection(dbConfig);
        return result;
    }

    /**
     * 导出数据库所有表信息到Excel
     *
     * @param config 数据库配置id
     * @return void 返回数据
     * @author 王合
     * @exception/throws
     */
    @PostMapping(value = "/exportDatabaseInfo")
    @ApiOperation(value = "导出数据库信息", notes = "导出数据库信息")
    public void exportDatabaseInfo(HttpServletResponse response, DbConfig config) {
        try {
            //创建excel工作簿
            Workbook wwb = new XSSFWorkbook();

            CellStyle headerStyle = excelUtil.getDefaultHeadStyle(wwb);
            CellStyle defaultStyle = excelUtil.getDefaultStyle(wwb);

            //处理数据库信息
            Sheet databaseInfoSheet = wwb.createSheet("数据库信息");
            databaseInfoSheet.setAutobreaks(true);

            Row databaseHeaderRow = databaseInfoSheet.createRow(0);
            Row databaseInfoRow = databaseInfoSheet.createRow(1);
            //设置默认单元格样式
            databaseInfoRow.setRowStyle(defaultStyle);

            DbConfig dbConfig = dbConfigService.getById(config.getId());
            DatabaseInfo databaseInfo = codeService.getDatabaseInfo(dbConfig);

            databaseInfo.setUrl(dbConfig.getUrl());
            databaseInfo.setDriver(dbConfig.getDriver());

            //创建表头
            for (DatabaseEnmu databaseEnmu : DatabaseEnmu.values()) {

                Cell headerCell = databaseHeaderRow.createCell(databaseEnmu.getCode());

                headerCell.setCellValue(databaseEnmu.getNote());
                headerCell.setCellStyle(headerStyle);

                Class databaseInfoClass = databaseInfo.getClass();

                try {
                    Field field = databaseInfoClass.getField(databaseEnmu.getFieldName());

                    String value = "";
                    if (field.get(databaseInfo) != null) {
                        value = field.get(databaseInfo).toString();
                    }

                    databaseInfoRow.createCell(databaseEnmu.getCode()).setCellValue(value);
                } catch (Exception e) {
                    databaseInfoRow.createCell(databaseEnmu.getCode()).setCellValue("");
                }

            }
            //设置表头样式 -- 由于新建单元格会覆盖行样式， 所以在单元格处理以后设置样式
            databaseHeaderRow.setRowStyle(headerStyle);
            databaseInfoSheet.createFreezePane(0, 1, 0, 1);
            excelUtil.setSizeColumn(databaseInfoSheet, databaseInfoSheet.getRow(0).getPhysicalNumberOfCells());

            List<TableInfo> tableList;
            tableList = codeService.getAllTables(dbConfig);

            //处理数据库表信息
            Sheet tableListSheet = wwb.createSheet("Table列表");
            tableListSheet.setAutobreaks(true);

            Row tableListHeaderRow = tableListSheet.createRow(0);

            //创建表头
            for (TableEnmu tableEnmu : TableEnmu.values()) {
                Cell tableListHeaderCell = tableListHeaderRow.createCell(tableEnmu.getCode());
                tableListHeaderCell.setCellValue(tableEnmu.getNote());
                tableListHeaderCell.setCellStyle(headerStyle);
            }

            for (int i = 0; i < tableList.size(); i++) {

                TableInfo tableInfoDetail = codeService.getFullTableInfo(tableList.get(i).getTableName(), dbConfig);

                Row tableInfoRow = tableListSheet.createRow(i + 1);
                for (TableEnmu tableEnmu : TableEnmu.values()) {

                    Class tableInfoClass = tableInfoDetail.getClass();

                    try {
                        Field field = tableInfoClass.getField(tableEnmu.getFieldName());

                        String value = "";
                        if (field.get(tableInfoDetail) != null) {
                            value = field.get(tableInfoDetail).toString();
                        }

                        tableInfoRow.createCell(tableEnmu.getCode()).setCellValue(value);
                    } catch (Exception e) {
                        tableInfoRow.createCell(tableEnmu.getCode()).setCellValue("");
                    }
                }

                //处理数据库表信息
                Sheet tableInfoSheet = wwb.createSheet(tableInfoDetail.getTableName());


            }

            OutputStream os = response.getOutputStream();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=DayReport.xlsx");
            wwb.write(os);
            //Workbook提供write方法，可以将当前工作空间写出到指定的地方。
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 信息模板导出
     *
     * @param response http 响应
     * @param type     下载文件类型
     * @return void
     * @author 王合
     * @exception/throws
     */
    @PostMapping("/exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        try {
            String filePath = "";
            String fileName = "";
            filePath = this.getClass().getResource("/").getPath() + "excelExample/DayReport.xlsx";
            fileName = "企业.xlsx";

            OutputStream os = response.getOutputStream();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
            //response.addHeader("Content-Length", String.valueOf(wwb.));

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(filePath);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

