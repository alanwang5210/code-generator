package com.alan.cloud.codegenerator.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王合
 * @date 2020-01-31 19:01:33
 */
@Service
public class ExcelUtil {


    /**
     * 获取工作薄
     *
     * @param inStr 上传文件
     * @return wb
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr) throws Exception {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(inStr);
        } catch (Exception e) {
            throw new Exception("请选择正确的Excel文件");
        }
        return wb;
    }

    /**
     * 获取单元格值
     *
     * @param cell
     * @return cellValue
     * @throws Exception
     */
    public String getCellData(Cell cell) throws Exception {
        String cellValue = "";
        if (cell != null) {
            try {
                switch (cell.getCellTypeEnum()) {
                    case BLANK:
                        cellValue = "";
                        break;
                    case NUMERIC: //数值型 0----日期类型也是数值型的一种
                        if (DateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
                            cellValue = dateFormat.format(date);
                        } else {
                            DecimalFormat df = new DecimalFormat("0");
                            cellValue = df.format(cell.getNumericCellValue());
                        }
                        break;
                    case STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case FORMULA:
                        cell.setCellType(CellType.STRING);
                        cellValue = cell.getStringCellValue();
                        break;
                    case BOOLEAN:
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case ERROR: //错误 5
                        cellValue = "!#REF!";
                        break;
                    default:
                        cellValue = "";
                }
            } catch (Exception e) {
                throw new Exception("读取Excel单元格数据出错：" + e.getMessage());
            }
        }
        return cellValue;
    }

    /**
     * 自适应宽度(中文支持)
     *
     * @param sheet
     * @param size
     * @return void
     * @author 王合
     * @exception/throws 违例类型  违例说明
     */
    public Sheet setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }

        return sheet;
    }

    public CellStyle getDefaultHeadStyle(Workbook wwb) {
        Font font = wwb.createFont();
        font.setBold(true);
        font.setFontName("宋体");

        CellStyle cellStyle = wwb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    public CellStyle getDefaultStyle(Workbook wwb) {
        Font font = wwb.createFont();
        font.setBold(false);
        font.setFontName("宋体");

        CellStyle cellStyle = wwb.createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }
}
