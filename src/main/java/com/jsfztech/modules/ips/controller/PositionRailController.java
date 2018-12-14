package com.jsfztech.modules.ips.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsfztech.modules.ips.entity.PositionRailEntity;
import com.jsfztech.modules.ips.service.PositionRailService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 定位电子围栏表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
@RestController
@RequestMapping("/ips/positionrail")
public class PositionRailController {
	@Autowired
	private PositionRailService positionRailService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:positionrail:list")
	public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
		//查询列表数据
        Query query = new Query(params);
        System.out.println("token"+request.getParameter("token"));
		List<PositionRailEntity> positionRailList = positionRailService.queryList(query);
		int total = positionRailService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(positionRailList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:positionrail:info")
	public R info(@PathVariable("id") Integer id){
		PositionRailEntity positionRail = positionRailService.queryObject(id);
		
		return R.ok().put("positionRail", positionRail);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:positionrail:save")
	public R save(@RequestBody PositionRailEntity positionRail){
		positionRailService.save(positionRail);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:positionrail:update")
	public R update(@RequestBody PositionRailEntity positionRail){
		positionRailService.update(positionRail);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:positionrail:delete")
	public R delete(@RequestBody Integer[] ids){
		positionRailService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
