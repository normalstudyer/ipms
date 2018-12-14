package com.jsfztech.modules.sys.controller;

import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;
import com.jsfztech.modules.api.annotation.AuthIgnore;
import com.jsfztech.modules.ips.entity.ExportExcel;
import com.jsfztech.modules.sys.entity.SysLogEntity;
import com.jsfztech.modules.sys.service.SysLogService;

import com.jsfztech.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统日志
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	//@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		String timestart = query.get("timestart")+" 00:00:00";
		if(timestart.indexOf("-")!= -1){
			query.replace("timestart",timestart);
		}

		String timeend = query.get("timeend")+" 00:00:00";
		if(timeend.indexOf("-")!= -1){
			query.replace("timeend",timeend);
		}
        System.out.println("开始和结束"+query.get("timestart")+"=="+query.get("timeend"));
		List<SysLogEntity> sysLogList = sysLogService.queryList(query);
		int total = sysLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysLogList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	
}
