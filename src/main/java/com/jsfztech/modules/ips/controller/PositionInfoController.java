package com.jsfztech.modules.ips.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.service.PersonsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import com.jsfztech.modules.ips.service.PositionInfoService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;




/**
 * 室内定位信息表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-07-24 09:12:22
 */
@RestController
@RequestMapping("/ips/positioninfo")
public class PositionInfoController {
	@Autowired
	private PositionInfoService positionInfoService;

	@Autowired
	private PersonsService personsService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:positioninfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PositionInfoEntity> positionInfoList = positionInfoService.queryList(query);
		int total = positionInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(positionInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:positioninfo:info")
	public R info(@PathVariable("id") Long id){
		PositionInfoEntity positionInfo = positionInfoService.queryObject(id);
		
		return R.ok().put("positionInfo", positionInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("ips:positioninfo:save")
	public R save(@RequestBody PositionInfoEntity positionInfo){
		Date date1 = new Date();
		SimpleDateFormat ss = new SimpleDateFormat("yyyyMMdd");
		String day = ss.format(date1);
		String tablename = "ips_position_info"+day;
		positionInfoService.save(positionInfo,tablename);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:positioninfo:update")
	public R update(@RequestBody PositionInfoEntity positionInfo){
		positionInfoService.update(positionInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:positioninfo:delete")
	public R delete(@RequestBody Long[] ids){
		positionInfoService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 列表
	 */
	@RequestMapping("/treelist")
	@RequiresPermissions("ips:positioninfo:list")
	public R queryPersonTreeList(){
		List<Map<String, Object>> treeList = personsService.queryPersonTreeList();
		return R.ok().put("treelist", treeList);
	}
}
