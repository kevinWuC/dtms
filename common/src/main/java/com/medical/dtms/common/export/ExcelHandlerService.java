package com.medical.dtms.common.export;

import com.medical.dtms.common.model.file.ArchiveOrInvalidModel;
import com.medical.dtms.common.model.file.ExportModel;
import com.medical.dtms.common.model.file.FileYearRecordExportModel;
import com.medical.dtms.common.model.style.StyleModel;
import com.medical.dtms.common.model.train.TrainExcelModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @version $Id：ExcelHandleUtils.java v 0.1, 2019/1/2 11:25 wuxuelin Exp$
 * @Author wuxuelin
 * @Description Excel  处理
 */
@Slf4j
public class ExcelHandlerService {

    /**
     * 处理 Excel
     */
    public static void handleWorkBook(List<ExportModel> list, String sheetName, String title, String fileName, String[] header, HttpServletRequest request, HttpServletResponse response) {
        // Excel 2003
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet1";
        }

        Sheet sheet = workbook.createSheet(sheetName);
        // 获取属性个数
        int length = list.get(0).getClass().getDeclaredFields().length;
        StyleModel styleModel = new StyleModel();
        styleModel.setMerge(true);
        styleModel.setFirstRow(0);
        styleModel.setLastRow(0);
        styleModel.setFirstCol(0);
        styleModel.setLastCol(length - 1);
        styleModel.setTitleRow(0);
        styleModel.setHeadRow(1);
        styleModel.setBodyRow(2);
        workbook = setSheetStyle(workbook, list, header, styleModel);

        // 设置标题
        setTitleValue(title, header, sheet);
        // 设置主体内容
        ExportModel model = null;
        // 从第三行还是写入，第一行为标题，第二行为表头
        for (int i = 0; i < list.size(); i++) {
            Row mainRow = sheet.createRow(i + 2);
            model = list.get(i);
            // 开始写入数据
            mainRow.createCell(0).setCellValue(model.getFileNo());
            mainRow.createCell(1).setCellValue(model.getFileName());
            mainRow.createCell(2).setCellValue(model.getFileVersion());
            mainRow.createCell(3).setCellValue(model.getEffectDate());
            mainRow.createCell(4).setCellValue(model.getFileTypeIdName());
            mainRow.createCell(5).setCellValue(model.getApplyDeptName());
            mainRow.createCell(6).setCellValue(model.getApplyUserName());
            mainRow.createCell(7).setCellValue(model.getApproverUserName());
            mainRow.createCell(8).setCellValue(model.getAuditorName());
        }

        // 导出数据
        try {
            setOutData(request, response, fileName, workbook);
        } catch (IOException e) {
            log.error("导出失败", e);
        }
    }

    /**
     * 处理 trainuser-Excel
     */
    public static void handleWorksBook(List<TrainExcelModel> list, String sheetName, String title, String fileName, String[] header, HttpServletRequest request, HttpServletResponse response) {
        // Excel 2003
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet1";
        }

        Sheet sheet = workbook.createSheet(sheetName);
        // 获取属性个数
        int length = list.get(0).getClass().getDeclaredFields().length;
        StyleModel styleModel = new StyleModel();
        styleModel.setMerge(true);
        styleModel.setFirstRow(0);
        styleModel.setLastRow(0);
        styleModel.setFirstCol(0);
        styleModel.setLastCol(length - 1);
        styleModel.setTitleRow(0);
        styleModel.setHeadRow(1);
        styleModel.setBodyRow(2);
        workbook = setSheetStyle(workbook, list, header, styleModel);


        // 设置标题
        sheet.getRow(0).getCell(0).setCellValue(title);

        Row headRow = sheet.getRow(1);
        for (int i = 0; i < header.length; i++) {
            headRow.createCell(i).setCellValue(header[i]);
        }
        // 设置主体内容
        TrainExcelModel model = null;
        // 从第三行还是写入，第一行为标题，第二行为表头
        for (int i = 0; i < list.size(); i++) {
            Row mainRow = sheet.createRow(i + 2);
            model = list.get(i);
            // 开始写入数据
            mainRow.createCell(0).setCellValue(model.getDspName());
            mainRow.createCell(1).setCellValue(model.getDeptName());
            mainRow.createCell(2).setCellValue(model.getTrainName());
            mainRow.createCell(3).setCellValue(model.getFileName());
            mainRow.createCell(4).setCellValue(model.getPoint());
            mainRow.createCell(5).setCellValue(model.getFinishTimeStr());
            mainRow.createCell(6).setCellValue(model.getPassStr());
        }

        // 导出数据
        try {
            setOutData(request, response, fileName, workbook);
        } catch (IOException e) {
            log.error("导出失败", e);
        }

    }

    /**
     * 导出 作废文件/归档文件 Excel
     */
    public static void handlerArchiveOrInvalidFile(List<ArchiveOrInvalidModel> list, String sheetName, String title, String fileName, String[] header, HttpServletRequest request, HttpServletResponse response) {
        // Excel 2003
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet1";
        }

        Sheet sheet = workbook.createSheet(sheetName);
        // 获取属性个数
        int length = list.get(0).getClass().getDeclaredFields().length;
        StyleModel styleModel = new StyleModel();
        styleModel.setMerge(true);
        styleModel.setFirstRow(0);
        styleModel.setLastRow(0);
        styleModel.setFirstCol(0);
        styleModel.setLastCol(length - 1);
        styleModel.setTitleRow(0);
        styleModel.setHeadRow(1);
        styleModel.setBodyRow(2);
        workbook = setSheetStyle(workbook, list, header, styleModel);

        // 设置标题
        setTitleValue(title, header, sheet);
        // 设置主体内容
        ArchiveOrInvalidModel model = null;
        // 从第三行还是写入，第一行为标题，第二行为表头
        for (int i = 0; i < list.size(); i++) {
            Row mainRow = sheet.createRow(i + 2);
            model = list.get(i);
            // 开始写入数据
            mainRow.createCell(0).setCellValue(model.getFileNo());
            mainRow.createCell(1).setCellValue(model.getFileName());
            mainRow.createCell(2).setCellValue(model.getFileVersion());
            mainRow.createCell(3).setCellValue(model.getEffectDate());
            mainRow.createCell(4).setCellValue(model.getApplyDeptName());
            mainRow.createCell(5).setCellValue(model.getOperatorDate());
            mainRow.createCell(6).setCellValue(model.getOperatorName());
        }

        // 导出数据
        try {
            setOutData(request, response, fileName, workbook);
        } catch (IOException e) {
            log.error("导出失败", e);
        }
    }

    /**
     * 导出 年审记录 Excel
     */
    public static void handlerFileYearRecord(List<FileYearRecordExportModel> list, String sheetName, String title, String fileName, String[] header, HttpServletRequest request, HttpServletResponse response) {
        // Excel 2003
        HSSFWorkbook workbook = new HSSFWorkbook();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = "sheet1";
        }

        Sheet sheet = workbook.createSheet(sheetName);
        // 获取属性个数
        int length = list.get(0).getClass().getDeclaredFields().length;
        StyleModel styleModel = new StyleModel();
        styleModel.setMerge(true);
        styleModel.setFirstRow(0);
        styleModel.setLastRow(0);
        styleModel.setFirstCol(0);
        styleModel.setLastCol(length - 1);
        styleModel.setTitleRow(0);
        styleModel.setHeadRow(1);
        styleModel.setBodyRow(2);
        workbook = setSheetStyle(workbook, list, header, styleModel);

        // 设置标题
        setTitleValue(title, header, sheet);
        // 设置主体内容
        FileYearRecordExportModel model = null;
        // 从第三行还是写入，第一行为标题，第二行为表头
        for (int i = 0; i < list.size(); i++) {
            Row mainRow = sheet.createRow(i + 2);
            model = list.get(i);
            // 开始写入数据
            mainRow.createCell(0).setCellValue(model.getFileNo());
            mainRow.createCell(1).setCellValue(model.getFileName());
            mainRow.createCell(2).setCellValue(model.getFileVersion());
            mainRow.createCell(3).setCellValue(model.getEffectDate());
            mainRow.createCell(4).setCellValue(model.getExaminedDate());
            mainRow.createCell(5).setCellValue(model.getYearConclusion());
            mainRow.createCell(6).setCellValue(model.getVersionEffect());
            mainRow.createCell(7).setCellValue(model.getVersionEffectInfo());
            mainRow.createCell(8).setCellValue(model.getAdvancement());
            mainRow.createCell(9).setCellValue(model.getAdvancementInfo());
            mainRow.createCell(10).setCellValue(model.getFormat());
            mainRow.createCell(11).setCellValue(model.getFormatInfo());
            mainRow.createCell(12).setCellValue(model.getOperability());
            mainRow.createCell(13).setCellValue(model.getOperabilityInfo());
            mainRow.createCell(14).setCellValue(model.getApplicability());
            mainRow.createCell(15).setCellValue(model.getApplicabilityInfo());
            mainRow.createCell(16).setCellValue(model.getExaminedOther());
            mainRow.createCell(17).setCellValue(model.getExaminedUserName());
        }

        // 导出数据
        try {
            setOutData(request, response, fileName, workbook);
        } catch (IOException e) {
            log.error("导出失败", e);
        }
    }

    /**
     * 设置 表格格式
     */
    public static HSSFWorkbook setSheetStyle(HSSFWorkbook workbook, List list, String[] header, StyleModel styleModel) {
        // 定义总体样式
        CellStyle style = workbook.createCellStyle();
        //  单元格字体水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        //  单元格字体垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置标题格式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.cloneStyleFrom(style);
        Font titleFont = workbook.createFont();
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 18);
        titleStyle.setFont(titleFont);
        // 设置合并单元格
        if (null != styleModel && null != styleModel.getMerge() && styleModel.getMerge()) {
            CellRangeAddress addresses = new CellRangeAddress(styleModel.getFirstRow(), styleModel.getLastRow(), styleModel.getFirstCol(), styleModel.getLastCol());
            workbook.getSheetAt(0).addMergedRegion(addresses);
        }

        // 定义表头样式
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.cloneStyleFrom(style);
        // 设置表头字体格式
        Font headFont = workbook.createFont();
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 80);
        headStyle.setFont(headFont);

        //  定义主体样式
        CellStyle mainStyle = workbook.createCellStyle();
        mainStyle.cloneStyleFrom(style);
        mainStyle.setWrapText(true);
        // 设置主体字体格式
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 50);
        mainStyle.setFont(font);

        List<Integer> len = new ArrayList<>();
        for (String s : header) {
            // 获取单个表头字数
            int i = s.length();
            len.add(i);
        }

        // 应用格式
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (StringUtils.isNotBlank(workbook.getSheetAt(i).getSheetName())) {
                Sheet sheet = workbook.getSheetAt(i);

                // 应用标题样式
                HSSFRow titleRow = (HSSFRow) sheet.createRow(styleModel.getTitleRow());
                titleRow.setRowStyle(titleStyle);
                titleRow.setHeight((short) 760);
                titleRow.createCell(0).setCellStyle(titleStyle);

                // 应用表头样式
                HSSFRow headRow = (HSSFRow) sheet.createRow(styleModel.getHeadRow());
                headRow.setRowStyle(headStyle);
                headRow.setHeight((short) 510);
                for (int m = 0; m < len.size(); m++) {
                    headRow.createCell(m).setCellStyle(headStyle);
                }

                // 应用主体样式
                int rowNum = list.size();
                for (int j = 0; j < rowNum; j++) {
                    HSSFRow row = (HSSFRow) sheet.createRow(j + styleModel.getBodyRow());
                    row.setRowStyle(mainStyle);
                    row.setHeight((short) 480);
                    for (int n = 0; n < len.size(); n++) {
                        row.createCell(n).setCellStyle(mainStyle);
                    }
                }
                for (int k = 0; k < len.size(); k++) {
                    sheet.setColumnWidth(k, 8364);
                }
            }
        }
        return workbook;
    }

    /**
     * 设置响应数据
     */
    private static void setOutData(HttpServletRequest request, HttpServletResponse response, String fileName, HSSFWorkbook wk) throws IOException {
        ServletOutputStream os = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel,charset=utf-8");
        String agent = request.getHeader("user-agent");
        fileName = encodeFileName(fileName, agent);
        response.setHeader("Content-Disposition", new String(("attachment;filename=" + fileName).getBytes(), "UTF-8"));
        wk.write(os);
        os.flush();
        os.close();
    }

    /**
     * 对文件名进行编码
     */
    public static String encodeFileName(String fileName, String agent) {
        try {
            if (agent.contains("Firefox")) {
                //  火狐浏览器
                fileName = "=?UTF-8?B?" + new BASE64Encoder().encode(fileName.getBytes("UTF-8")) + "?=";
                fileName = fileName.replaceAll("\r\n", "");
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
                fileName = fileName.replace("+", " ");
            }
            return fileName;
        } catch (UnsupportedEncodingException e) {
            log.error("对文件名进行编码失败", e);
            return null;
        }
    }

    /**
     * 设置标题值
     */
    private static void setTitleValue(String title, String[] header, Sheet sheet) {
        sheet.getRow(0).getCell(0).setCellValue(title);

        Row headRow = sheet.getRow(1);
        for (int i = 0; i < header.length; i++) {
            headRow.createCell(i).setCellValue(header[i]);
        }
    }

}
