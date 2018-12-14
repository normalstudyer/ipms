package com.jsfztech.modules.ips.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsfztech.modules.api.annotation.AuthIgnore;
import com.jsfztech.modules.ips.entity.ExportExcel;
import com.jsfztech.modules.ips.service.PersonsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jsfztech.modules.ips.entity.PositionInfoHistoryEntity;
import com.jsfztech.modules.ips.service.PositionInfoHistoryService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-16 14:43:21
 */
@RestController
@RequestMapping("/ips/positioninfohistory")
public class PositionInfoHistoryController {
	@Autowired
	private PositionInfoHistoryService positionInfoHistoryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:positioninfohistory:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PositionInfoHistoryEntity> positionInfoHistoryList = positionInfoHistoryService.queryList(query);
		int total = positionInfoHistoryService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(positionInfoHistoryList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:positioninfohistory:info")
	public R info(@PathVariable("id") Long id){
		PositionInfoHistoryEntity positionInfoHistory = positionInfoHistoryService.queryObject(id);
		
		return R.ok().put("positionInfoHistory", positionInfoHistory);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:positioninfohistory:save")
	public R save(@RequestBody PositionInfoHistoryEntity positionInfoHistory){
		positionInfoHistoryService.save(positionInfoHistory);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:positioninfohistory:update")
	public R update(@RequestBody PositionInfoHistoryEntity positionInfoHistory){
		positionInfoHistoryService.update(positionInfoHistory);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:positioninfohistory:delete")
	public R delete(@RequestBody Long[] ids){
		positionInfoHistoryService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/recordlist")
	@RequiresPermissions("ips:positioninfo:list")
	public R recordlist(@RequestParam Map<String, Object> params){
		List<PositionInfoHistoryEntity> positionInfoHistoryList = positionInfoHistoryService.queryByTagId(params);
		return R.ok().put("recordlist", positionInfoHistoryList);
	}

}
