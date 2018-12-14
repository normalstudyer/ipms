package com.jsfztech.modules.ips.entity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/17.
 */
public class ExcelUtil1 {
    /**
     * 创建excel文档，
     * @param excelName excel的表名
     * @param list 数据，相当于每一行，而map的key代表了单元格的字段，value则是值
     * @param keys list中map的key数组集合
     * @param columnNames excel的列名
     * */
     public static Workbook createWorkBook(String excelName,List<Map<String,Object>> list,String[] keys,String[] columnNames){
         //创建Excel工作簿
         HSSFWorkbook wb = new HSSFWorkbook();
         //创建第一个sheet并命名
         Sheet sheet = wb.createSheet(excelName);
         //创建第一行
         Row firstRow = sheet.createRow(0);
         //设置列名
         for(int i=0;i<columnNames.length;i++){
             Cell cell = firstRow.createCell(i);
             cell.setCellValue(columnNames[i]);
         }
         //设置每行每列的值
         for (int i=0;i<list.size();i++){
             //Row行，Cell方格，Row从1开始，因为第一行用来存放列名，cell从0开始
             //创建一行，在页sheet上
             Row row = sheet.createRow(i+1);
             //在row上创建一个方格
             for(int j=0;j<keys.length;j++){
                 Cell cell = row.createCell(j);
                 cell.setCellValue(list.get(i).get(keys[j]) == null? " ":list.get(i).get(keys[j]).toString());
             }
         }
         return  wb;
     }
}
