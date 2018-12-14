package com.jsfztech.modules.api.controller;

import com.jsfztech.common.utils.R;
import com.jsfztech.modules.api.annotation.AuthIgnore;
import com.jsfztech.modules.ips.entity.ExportExcel;
import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import com.jsfztech.modules.ips.service.PositionInfoHistoryService;
import com.jsfztech.modules.ips.service.PositionInfoService;
import com.jsfztech.modules.ips.service.RailWarnningService;
import com.jsfztech.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * api室内定位接口
 *
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-07-24 09:12:22
 */
@RestController
@RequestMapping("/api")
public class ApiIpsController {
    @Autowired
    private PositionInfoService positionInfoService;
    @Autowired
    private RailWarnningService railWarnningService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private PositionInfoHistoryService positionInfoHistoryService;

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    /**
     * 室内定位数据保存
     */
    @AuthIgnore
    @PostMapping("ipsInfoSave")
    public R register(@RequestBody PositionInfoEntity positionInfo){
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String day = sf.format(date);
        String tablename = "ips_position_info"+day;
        positionInfoService.save(positionInfo,tablename);
        return R.ok();
    }
    /**
     * 导出Excel
     */
    @AuthIgnore
    @GetMapping("export_excel")
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException, InvocationTargetException {

        Map<String,Object> map = new HashMap<>();
        String timestart = request.getParameter("timestart")+" 00:00:00";

        if(timestart.indexOf("-")!= -1){
            map.put("timestart",timestart);
        }else{
            map.put("timestart",null);
        }

        String timeend = request.getParameter("timeend")+" 00:00:00";
        if(timeend.indexOf("-")!= -1){
            map.put("timeend",timeend);
        }else{
            map.put("timeend",null);
        }
        /*if(request.getParameter("railname") == null){
            map.put("railname",null);
        }else{
            map.put("railname",request.getParameter("railname"));
        }
        if(request.getParameter("targetname") == null){
            map.put("targetname",null);
        }else{
            map.put("targetname",request.getParameter("targetname"));
        }*/
        map.put("railname",request.getParameter("railname"));
        map.put("name",request.getParameter("name"));
        List<Map<String,Object>> railWarnningList = railWarnningService.queryExportList(map);

        /*response.reset();
		response.setHeader("Content-Disposition", "attachment;filename="+ new String("ddbb".getBytes("GBK"),"ISO-8859-1")+".xlsx");
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);*/
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");//设置日期格式
        String newDate = "告警记录表"+df.format(date);
        System.out.println(newDate);
        response.reset();
        response.setContentType("text/html; charset=utf-8");//不用改
        response.setContentType("application/msexcel");//不用改
        response.setHeader("Content-disposition", "attachment; filename=" + new String(newDate.getBytes("GBK"),"ISO-8859-1") + ".xlsx");//文件名
        ExportExcel<Map<String, Object>> ee = new ExportExcel<>();//导出的工具类，泛型填的bean跟上面的list里的泛型bean必须一样
        String title = "告警记录";//excel里的sheet名
        String[] headers = {"编号", "围栏名称", "人员名称","触发时间", "是否进入", "信息描述"};//表头
        String[] fieldNames = {"id","railName", "name","triggerDate", "triggerType", "info"};//list里的bean里的属性名，注意要跟表头对应
        OutputStream out = response.getOutputStream();
        ee.exportExcel2007(title, headers, railWarnningList, fieldNames, out,"yyyy-MM-dd hh:mm:ss");

    }

    /**
     * 导出Excel
     */
    @AuthIgnore
    @GetMapping("exportlog")
    public void export(@RequestParam Map<String,Object> params, HttpServletResponse response) throws IOException, InvocationTargetException {

        Map<String,Object> map = new HashMap<>();
        String timestart = params.get("timestart")+" 00:00:00";

        if(timestart.indexOf("-")!= -1){
            map.put("timestart",timestart);
        }else{
            map.put("timestart",null);
        }

        String timeend = params.get("timeend")+" 00:00:00";
        if(timeend.indexOf("-")!= -1){
            map.put("timeend",timeend);
        }else{
            map.put("timeend",null);
        }
        /*if(request.getParameter("railname") == null){
            map.put("railname",null);
        }else{
            map.put("railname",request.getParameter("railname"));
        }
        if(request.getParameter("targetname") == null){
            map.put("targetname",null);
        }else{
            map.put("targetname",request.getParameter("targetname"));
        }*/
        map.put("key",params.get("key"));

        List<Map<String,Object>> sysLogList = sysLogService.queryExportList(map);

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");//设置日期格式
        String newDate = "系统日志"+df.format(date);
        System.out.println(newDate);
        response.reset();
        response.setContentType("text/html; charset=utf-8");//不用改
        response.setContentType("application/msexcel");//不用改
        response.setHeader("Content-disposition", "attachment; filename=" + new String(newDate.getBytes("GBK"),"ISO-8859-1") + ".xlsx");//文件名
        ExportExcel<Map<String, Object>> ee = new ExportExcel<>();//导出的工具类，泛型填的bean跟上面的list里的泛型bean必须一样
        String title = "系统日志";//excel里的sheet名
        String[] headers = {"编号", "用户名", "用户操作","请求方法", "请求参数", "执行时长(毫秒)","IP地址","创建时间"};//表头
        String[] fieldNames = {"id","username", "operation","method", "params", "time","ip","createDate"};//list里的bean里的属性名，注意要跟表头对应
        OutputStream out = response.getOutputStream();
        ee.exportExcel2007(title, headers, sysLogList, fieldNames, out,"yyyy-MM-dd hh:mm:ss");

    }
    /**
     * 导出历史轨迹
     */
    @AuthIgnore
    @GetMapping("exportrecord")
    public void export1(@RequestParam Map<String,Object> params, HttpServletResponse response) throws IOException, InvocationTargetException {

        Map<String,Object> map = new HashMap<>();
        String stime = params.get("stime")+" 00:00:00";

        if(stime.indexOf("-")!= -1){
            map.put("stime",stime);
        }else{
            map.put("stime",null);
        }

        String etime = params.get("etime")+" 00:00:00";
        if(etime.indexOf("-")!= -1){
            map.put("etime",etime);
        }else{
            map.put("etime",null);
        }

        map.put("tagId",params.get("tagId"));

        List<Map<String,Object>> positionInfoHistoryList = positionInfoHistoryService.queryExportList(map);

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");//设置日期格式
        String newDate = "历史轨迹"+df.format(date);
        System.out.println(newDate);
        response.reset();
        response.setContentType("text/html; charset=utf-8");//不用改
        response.setContentType("application/msexcel");//不用改
        response.setHeader("Content-disposition", "attachment; filename=" + new String(newDate.getBytes("GBK"),"ISO-8859-1") + ".xlsx");//文件名
        ExportExcel<Map<String, Object>> ee = new ExportExcel<>();//导出的工具类，泛型填的bean跟上面的list里的泛型bean必须一样
        String title = "历史轨迹";//excel里的sheet名
        String[] headers = {"编号", "用户名", "电量","X", "Y", "更新时间"};//表头
        String[] fieldNames = {"tagId","name", "battery","x", "y", "updateDate"};//list里的bean里的属性名，注意要跟表头对应
        OutputStream out = response.getOutputStream();
        ee.exportExcel2007(title, headers, positionInfoHistoryList, fieldNames, out,"yyyy-MM-dd hh:mm:ss");

    }
}
