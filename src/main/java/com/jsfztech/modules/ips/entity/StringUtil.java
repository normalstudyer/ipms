package com.jsfztech.modules.ips.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/10/17.
 */
public class StringUtil {

    public static boolean isEmpty(Object obj){
        return "".equalsIgnoreCase(parseString(obj));
    }

    /**
     * 过滤空对象
     * @param obj
     * @return
     */
    public static String parseString(Object obj){
        return parseString(obj, "");
    }

    /**
     * 过滤空对象
     * @param obj
     * @return
     */
    public static String parseString(Object obj,String str){
        boolean flag = false;
        if(obj == null){
            flag = true;
        }
        if(flag == false){
            if("null".equalsIgnoreCase(obj.toString()) || "".equalsIgnoreCase(obj.toString())){
                flag = true;
            }
        }
        if(flag){
            return str;
        }else{
            return obj.toString().trim();
        }
    }

    /**
     * 转换Long类型
     * @param obj
     * @return
     */
    public static Long parseLong(Object obj){
        return parseLong(obj,null);
    }

    /**
     * 转换Long类型
     * @param obj
     * @return
     */
    public static Long parseLong(Object obj,Long value){
        if(isEmpty(obj)){
            return value;
        }
        String str = parseString(obj);
        return Long.parseLong(str);
    }

    /**
     * 转换Integer类型
     * @param obj
     * @return
     */
    public static Integer parseInteger(Object obj){
        return parseInteger(obj,null);
    }

    /**
     * 转换Integer类型
     * @param obj
     * @return
     */
    public static Integer parseInteger(Object obj,Integer value){
        if(isEmpty(obj)){
            return value;
        }
        String str = parseString(obj);
        return Integer.valueOf(str);
    }

    /**
     * 转换Double类型
     * @param obj
     * @return
     */
    public static Double parseDouble(Object obj){
        return parseDouble(obj,null);
    }

    /**
     * 转换Double类型
     * @param obj
     * @return
     */
    public static Double parseDouble(Object obj,Double data){
        String str = parseString(obj);
        if("".equalsIgnoreCase(str)){
            if(isEmpty(data)){
                return null;
            }
            return data;
        }
        return Double.parseDouble(str);
    }

    /**
     * 转换Double类型
     * @param obj
     * @return
     */
    public static String formatDate(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(date == null){
            return "";
        }
        try{
            return sdf.format(date);
        }catch(Exception ex){
            return "";
        }
    }

    /**
     * 转换Date类型
     * @param obj
     * @return
     * @throws ParseException
     */
    public static Date parseDate(Object obj,String format) throws ParseException {
        String str = parseString(obj);
        if("".equalsIgnoreCase(str)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    /**
     * 过滤回车符
     * @param value
     * @return
     */
    public static String filterEnter(String value){
        value = parseString(value);
        value = value.replaceAll("\r", "");
        value = value.replaceAll("\n", "<br/>");
        return value;
    }

    public static boolean checkDate(String date){
        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(date);
        boolean b = m.matches();
        return b;
    }

    /**
     * 过滤掉SQL中包含 1 = 1 部分内容
     * @param sql
     * @return
     */
    public static String filterSQL(String sql){
        Pattern pattern1 = Pattern.compile("[\\s]*(.+)[\\s]*=[\\s]*\\1[\\s]+((and)|(or))[\\s]+",Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile("[\\s]*((and)|(or))[\\s]+(.+)[\\s]*=[\\s]*\\4[\\)]",Pattern.CASE_INSENSITIVE);
        Pattern pattern3 = Pattern.compile("[\\s]*((and)|(or))[\\s]+(.+)[\\s]*=[\\s]*\\4[\\s]+",Pattern.CASE_INSENSITIVE);
        Pattern pattern4 = Pattern.compile("[\\s]*where[\\s]+(.*)[\\s]*=[\\s]*\\1[\\)]",Pattern.CASE_INSENSITIVE);
        Pattern pattern5 = Pattern.compile("[\\s]*where[\\s]+(.*)[\\s]*=[\\s]*\\1[\\s]+",Pattern.CASE_INSENSITIVE);

        String str = sql;
        str = " " + str;
        str = str + " ";

        Matcher matcher = pattern1.matcher(str);
        while (matcher.find()) {
            str = matcher.replaceAll(" ");
        }
        matcher = pattern2.matcher(str);
        while (matcher.find()) {
            str = matcher.replaceAll(" ) ");
        }
        matcher = pattern3.matcher(str);
        while (matcher.find()) {
            str = matcher.replaceAll(" ");
        }
        matcher = pattern4.matcher(str);
        while (matcher.find()) {
            str = matcher.replaceAll(" ) ");
        }
        matcher = pattern5.matcher(str);
        while (matcher.find()) {
            str = matcher.replaceAll(" ");
        }
        return str.trim();
    }

    public static boolean checkFloat(String str){
        boolean b = str.matches("^(([0-9]+\\.[0-9]+)|([0-9]*))$");
        return b;
    }

    public static boolean checkNumber(String str){
        boolean b = str.matches("[\\d]+");
        return b;
    }

    public static String getRandomNum() {
        String t=String.valueOf(System.currentTimeMillis());
        t=t.substring(t.length()-5,t.length());
        String rad = "0123456789";
        StringBuffer result = new StringBuffer();
        Random rand = new Random();
        int length = 27;
        for (int i = 0; i < length; i++) {
            int randNum = rand.nextInt(10);
            result.append(rad.substring(randNum, randNum + 1));
        }
        return t+result;
    }

    public static String getRandomNumByLength(int length){
        String rad = "0123456789";
        Random rand = new Random();
        StringBuffer result = new StringBuffer();
        if(length>0){
            for (int i = 0; i < length; i++) {
                int randNum = rand.nextInt(10);
                result.append(rad.substring(randNum, randNum + 1));
            }
        }
        return result.toString();
    }

    /**
     * 精确小数位数
     * @param dou 小数
     * @param num1 位数
     * @param num2 1.四舍五入 2.舍去小数
     * @return
     */
    public static Double exactDouble(Double dou,int num1,int num2){
        if(isEmpty(dou)){
            return null;
        }
        int roundingMode = 4;
        BigDecimal bigDec = new BigDecimal(dou);
        bigDec = bigDec.setScale(10,roundingMode);
        dou = bigDec.doubleValue();

//		bigDec = new BigDecimal(dou);
//		if(num2 == 1){
//			roundingMode = 4;
//		}else{
//			roundingMode = 3;
//		}
//		bigDec = bigDec.setScale(num1,3);
//		return bigDec.doubleValue();

        int tmpNum = 1;
        for(int i=0;i<num1;i++){
            tmpNum *= 10;
        }
        if(num2 == 1){
            return StringUtil.parseDouble((Math.round(dou*tmpNum)/1.0D/tmpNum));
        }else if(num2 == 2){
            return (Math.floor(dou*tmpNum)/tmpNum);
        }else{
            return null;
        }
    }

    /**精确小数位数
     * 直接保留多少位小数
     * */
    public static BigDecimal exactNumber(Double d,int num){
        return new BigDecimal(d).setScale(num,BigDecimal.ROUND_HALF_UP);
    }

    public static Object viewZero(Object o){
        if(Double.parseDouble(String.valueOf(o))==0){
            return "0";
        }else{
            return o;
        }
    }

    /**根据长度截取字符串
     * */
    public static String subStrByLength(String str,int length){
        if(null == str)
            return "";
        else if(str.length()>length)
            return str.substring(0, length);
        else
            return str;
    }

    public static String bytesToHexString(byte[] paramArrayOfByte)
    {
        StringBuilder localStringBuilder = new StringBuilder("");
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0)) {
            return null;
        }
        for (int i = 0; i < paramArrayOfByte.length; ++i) {
            int j = paramArrayOfByte[i] & 0xFF;
            String str = Integer.toHexString(j);
            if (str.length() < 2) {
                localStringBuilder.append(0);
            }
            localStringBuilder.append(str);
        }
        return localStringBuilder.toString();
    }

    public static String generateRandomString(int paramInt)
    {
        char[] arrayOfChar = { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9' };

        StringBuffer localStringBuffer = new StringBuffer();
        Random localRandom = new Random();

        for (int i = 0; i < paramInt; ++i) {
            localStringBuffer.append(arrayOfChar[localRandom.nextInt(arrayOfChar.length)]);
        }
        return localStringBuffer.toString();
    }

    public static String fillLeft(String paramString, char paramChar,
                                  int paramInt) {
        return fillStr(paramString, paramChar, paramInt, true);
    }

    public static String fillRight(String paramString, char paramChar,
                                   int paramInt) {
        return fillStr(paramString, paramChar, paramInt, false);
    }

    private static String fillStr(String paramString, char paramChar,
                                  int paramInt, boolean paramBoolean) {
        int i = paramInt - paramString.length();

        if (i <= 0) {
            return paramString;
        }
        StringBuilder localStringBuilder = new StringBuilder(paramString);
        for (; i > 0; --i) {
            if (paramBoolean)
                localStringBuilder.insert(0, paramChar);
            else {
                localStringBuilder.append(paramChar);
            }
        }
        return localStringBuilder.toString();
    }

    /**
     * 替换字符串中的特殊字符
     * \n回车(\u000a) \t水平制表符(\u0009)  \s空格(\u0008) \r换行(\u000d)
     * @param sourceStr
     * @return
     */
    public static String replaceBlank(String sourceStr){
        String dest = "";
        if (sourceStr!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(sourceStr);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static void main(String []args){

        System.out.println(getRandomNumByLength(8));
        System.out.println(filterSQL("(select * from dual where 1=1 ) SELECT '' REAL_NAME,T.MOBILE,'' CUST_ID FROM JUNHONG_SENDSMS_MOBILE T WHERE 1 = 1 AND (LENGTH(T.MOBILE) = 11 OR LENGTH(T.MOBILE) = 12) and 1 = 12 or 22  = 22 order by id desc  (select * from dual where 1=2 and 12 = 123 or 12 = 12 order by id desc where 1 = 1 ) "));

    }


}
