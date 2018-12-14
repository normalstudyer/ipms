package com.jsfztech.modules.ips.entity;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/10/17.
 */
public class ExportExcel<T> {

    public void exportExcel(String title, Collection<T> dataset, String[] fieldNames, OutputStream out) throws InvocationTargetException {
        exportExcel(title, null, dataset, fieldNames, out, "yyyy-MM-dd", null);
    }

    public void exportExcel(String title, String[] headers, Collection<T> dataset, String[] fieldNames,
                            OutputStream out) throws InvocationTargetException {
        exportExcel(title, headers, dataset, fieldNames, out, "yyyy-MM-dd", null);
    }

    public void exportExcel(String title, String[] headers,
                            Collection<T> dataset, String[] fieldNames, OutputStream out, String pattern, String footInfo) throws InvocationTargetException {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLACK.index);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("duxin");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Class tCls = t.getClass();
            for (int i = 0; i < fieldNames.length; i++) {
                try {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    String fieldName = fieldNames[i];
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = StringUtil.parseString(value);

                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        if (!StringUtil.isEmpty(footInfo)) {
            // 生成一个样式
            HSSFCellStyle style3 = workbook.createCellStyle();
            // 设置这些样式
            style3.setFillForegroundColor(HSSFColor.TAN.index);
            style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 把字体应用到当前的样式
            HSSFFont font1 = workbook.createFont();
            font1.setColor(HSSFColor.RED.index);
            font1.setFontHeightInPoints((short) 12);
            font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style3.setFont(font1);

            int rowNum = dataset.size();
            HSSFRow footRow = sheet.createRow(rowNum + 1);
            HSSFCell cell0 = footRow.createCell(0);
            HSSFRichTextString text = new HSSFRichTextString(footInfo);
            cell0.setCellStyle(style3);
            cell0.setCellValue(text);
            for (int i = 1; i < headers.length; i++) {
                HSSFCell cell = footRow.createCell(i);
                cell.setCellStyle(style3);
            }
            sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, headers.length - 1));
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据导出到excel2007中
     *
     * @param title
     * @param headers
     * @param dataset
     * @param fieldNames
     * @param out
     * @param pattern
     */
    public void exportExcel2007(String title, String[] headers,
                                Collection<Map<String, Object>> dataset, String[] fieldNames, OutputStream out, String pattern) {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(2000);
        //XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        //设置背景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色填充颜色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        CellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.RED.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        Font font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLACK.index);

        // 产生表格标题行
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(headers[i]);
        }
//        for(int i=0;i<6;i++){
//            System.out.println(row.getCell(i));
//        }

        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataset.iterator();
        int index = 0;
        int quan = 0;
        int type = 0;//用type来表示是哪个方法的导出
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> t = it.next();
            //System.out.println(t);
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            // Class tCls = t.getClass();

            for (int i = 0; i < fieldNames.length; i++) {
                try {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    String fieldName = fieldNames[i];
                    String getMethodName = fieldName;/*.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);*/
                    //Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = null;
                    //HuiDanBiLi
                    if ("triggerType".equals(getMethodName)) {
                        if ("1".equals((t.get(getMethodName) + "").trim())) {
                            value = "进入";
                        } else  {
                            value = "离开";
                        }
                    } else {
                        value = t.get(getMethodName);
                    }
                    if ("sex".equals(getMethodName)) {
                        if ("1".equals((t.get(getMethodName) + "").trim())) {
                            value = "男";
                        } else  {
                            value = "女";
                        }
                    } else {
                        value = t.get(getMethodName);
                    }

                    // 判断值的类型后进行强制类型转换
                    String textValue = null;

                    savedata(value,textValue,pattern,i,index,row,sheet,cell,font3);
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    // 清理资源
                }
            }
        }

        try {
            System.out.println("写入进去之前");
            workbook.write(out);

            System.out.println("写入进去之后");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportExcel2007ZZ(String title, String[] headers,
                                  Collection<Map<String, Object>> dataset, String[] fieldNames, OutputStream out, String pattern) {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(2000);
        // 生成一个表格
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20
        sheet.setDefaultColumnWidth(30);
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        //设置背景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色填充颜色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


        CellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.RED.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        Font font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLACK.index);

        // 产生表格标题行
        Row row = sheet.createRow(0);
        sethead(headers,row,style);

        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataset.iterator();
        int index = 0;
        int quan = 0;
        int type = 0;//用type来表示是哪个方法的导出
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            Map<String, Object> t = it.next();
            System.out.println(t);
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            // Class tCls = t.getClass();

            for (int i = 0; i < fieldNames.length - 1; i++) {
                try {
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    String fieldName = fieldNames[i];
                    String getMethodName = fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    //Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = null;
                    //HuiDanBiLi
                    if ("trigger_type".equals(getMethodName)) {
                        type = 1;//1表示是纸质回单那个页面的导出
                        if ("1".equals((t.get(getMethodName) + "").trim())) {
                            quan++;

                            value = "进入";
                        } else {


                            cell.setCellStyle(style4);
                            value = "离开";

                        }
                    } else {
                        value = t.get(getMethodName);
                    }

                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    savedata(value,textValue,pattern,i,index,row,sheet,cell,font3);
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    // 清理资源
                }
            }
            String valu = (t.get(fieldNames[fieldNames.length - 1]) + "").replaceAll(",", "");
            String[] tiHuoDanInof = valu.split(";");//最後一個出庫單回單信息
            for (int m = 0; m < tiHuoDanInof.length; m++) {
                Cell cell = row.createCell(fieldNames.length - 1 + m);
                cell.setCellStyle(style2);
                XSSFRichTextString xssfRichTextString = new XSSFRichTextString(tiHuoDanInof[m]);
                xssfRichTextString.applyFont(font3);
                cell.setCellValue(xssfRichTextString);
            }

        }

        index++;
        row = sheet.createRow(index);
        Cell cell = row.createCell(6);
        cell.setCellStyle(style2);
        if (index == 0 || quan == 0) {
            cell.setCellValue(0);
        } else {
            index--;
            double shu = (((float) quan * 100) / ((float) index));
            shu = (double) Math.round(shu * 1000) / 1000;
            cell.setCellValue(quan + "/" + index + "=" + shu + "%");
        }

        try {
            System.out.println("写入进去之前2");
            workbook.write(out);
            System.out.println("写入进去之后2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据导出到excel多个表中
     *
     * @param title
     * @param headers
     * @param dataset
     * @param fieldNames
     * @param out
     * @param pattern
     */
    public void exportExcel2007(SXSSFWorkbook workbook, String title, String[] headers,
                                Collection<T> dataset, String[] fieldNames, OutputStream out, String pattern) {
        // 生成一个表格
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        Font font3 = workbook.createFont();
        font3.setColor(HSSFColor.BLACK.index);

        // 产生表格标题行
        Row row = sheet.createRow(0);

        sethead(headers,row,style);

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Class tCls = t.getClass();
            for (int i = 0; i < fieldNames.length; i++) {

                try {

                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    String fieldName = fieldNames[i];
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;

                    savedata(value,textValue,pattern,i,index,row,sheet,cell,font3);
                } catch (SecurityException e) {

                } catch (NoSuchMethodException e) {

                } catch (IllegalArgumentException e) {

                } catch (IllegalAccessException e) {

                } catch (InvocationTargetException e) {

                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置下载文件中文件的名称
     *
     * @param filename
     * @param request
     * @return
     */
    public String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE
         * 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1;
         * zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");
            return filename;
        } catch (Exception ex) {

            return filename;
        }
    }

    /**
     * 读取出filePath中的所有数据信息
     *
     * @paramfilePath excel文件的绝对路径
     */

    public static List<Map<String, String>> getDataFromExcel(MultipartFile file) {

        List<Map<String, String>> excelresult = new ArrayList<Map<String, String>>();
        //String filePath = "E:\\123.xlsx";
        if (file == null) {

        }
        String filename = file.getOriginalFilename();
        long size = file.getSize();
        if (filename == null || filename == "" && size == 0) {

        }

        //判断是否为excel类型文件
        if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")) {
            System.out.println("文件不是excel类型");
        }


        try {
            //获取文件上传流
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            //得到一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            //判断表头是否正确
            if (rowHead.getPhysicalNumberOfCells() != 2) {
                System.out.println("表头的数量不对!");
            }

            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();

            //要获得属性
            String name = "";
            String latitude = "";

            //获得所有数据
            for (int i = 1; i <= totalRowNum; i++) {
                //获得第i行对象
                Row row = sheet.getRow(i);
                Map<String, String> map = new HashMap();
                //获得获得第i行第0列的 String类型对象
                if (row.getCell(0) == null) {
                    map.put("IDCNO", "");//excel导入1 号位为 id卡
                } else {
                    row.getCell(0).setCellType(row.getCell(0).CELL_TYPE_STRING);
                    map.put("IDCNO", row.getCell(0).getStringCellValue().toString());//excel导入1 号位为 id卡
                }
                if (row.getCell(1) == null) {
                    map.put("YJNUMBER", "");//2号位为 硬件号
                } else {
                    row.getCell(1).setCellType(row.getCell(1).CELL_TYPE_STRING);

                    map.put("YJNUMBER", row.getCell(1).getStringCellValue().toString());//2号位为 硬件号
                }
                excelresult.add(map);
                /* for(int j=0;j< row.getLastCellNum();j++){

                	 Cell cell = row.getCell(j);
                	 if(cell!=null){
                		 cell.setCellType(cell.CELL_TYPE_STRING);
                		 System.out.print("-内容-："+ cell.getStringCellValue().toString());
                	 }else{
                		 System.out.print("-内容-：空的");
                	 }

                 }

                 System.out.println();*/


                //获得一个数字类型的数据


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelresult;


    }

    @SuppressWarnings("static-access")
    private String getValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING) {
            return String.valueOf(hssfCell.getStringCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    /**
     * 设置sheet头部
     */
    public void sethead(String[] headers,Row row,CellStyle style){
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
    }
    /**
     * 将数据放入row中，并对数据判断
     */
    public void savedata(Object value,String textValue,String pattern,int i,int index,Row row,Sheet sheet,Cell cell,Font font3){
        if (value instanceof Date) {
            Date date = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            textValue = sdf.format(date);
        } else if (value instanceof byte[]) {
            // 有图片时，设置行高为60px;
            row.setHeightInPoints(60);
            // 设置图片所在列宽度为80px,注意这里单位的一个换算
            sheet.setColumnWidth(i, (short) (35.7 * 80));
            // sheet.autoSizeColumn(i);
            byte[] bsValue = (byte[]) value;
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                    1023, 255, (short) 6, index, (short) 6, index);
            anchor.setAnchorType(2);
        } else {
            // 其它数据类型都当作字符串简单处理
            textValue = StringUtil.parseString(value);
        }
        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
        if (textValue != null) {
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher = p.matcher(textValue);
            if (matcher.matches()) {
                // 是数字当作double处理
                cell.setCellValue(Double.parseDouble(textValue));
            } else {
                XSSFRichTextString xssfRichTextString = new XSSFRichTextString(textValue);
                xssfRichTextString.applyFont(font3);
                cell.setCellValue(xssfRichTextString);
            }
        }
    }
}
