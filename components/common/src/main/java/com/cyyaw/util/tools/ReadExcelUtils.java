package com.cyyaw.util.tools;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 听心的原因
 * @create 2021/12/28 10:13
 */
public class ReadExcelUtils {

    /**
     * 读取Excel 数据
     *
     * @param excelFile
     * @param sheetNum
     * @param rowNum
     * @return
     */
    public static List<JSONObject> read(String excelFile, Integer sheetNum, Integer rowNum) {
        sheetNum = sheetNum == null ? 0 : sheetNum;
        rowNum = rowNum == null ? 0 : rowNum;
        ExcelReaderBuilder excel = EasyExcel.read(excelFile);
        ExcelReaderSheetBuilder sheet = excel.sheet(sheetNum);
        return read(sheet, rowNum);
    }

    /**
     * 读取Excel 数据
     *
     * @param excelFile
     * @param sheetName
     * @param rowNum
     * @return
     */
    public static List<JSONObject> read(String excelFile, String sheetName, Integer rowNum) {
        rowNum = rowNum == null ? 0 : rowNum;
        ExcelReaderBuilder excel = EasyExcel.read(excelFile);
        ExcelReaderSheetBuilder sheet = excel.sheet(sheetName);
        return read(sheet, rowNum);
    }

    private static List<JSONObject> read(ExcelReaderSheetBuilder sheet, Integer rowNum) {
        List<Object> data = sheet.doReadSync();
        JSONArray tmp =  new JSONArray(data);
        List<JSONObject> rest = new ArrayList<>();
        for (int i = rowNum; i < tmp.size(); i++) {
            JSONObject o = tmp.getJSONObject(i);
            // 处理合并列问题 START

            // 处理合并列问题 END
            rest.add(o);
        }
        return rest;
    }


}
