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

import com.jsfztech.modules.ips.entity.RailPersonRelEntity;
import com.jsfztech.modules.ips.service.RailPersonRelService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;




/**
 * 电子围栏人员关联表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@RestController
@RequestMapping("/ips/railpersonrel")
public class RailPersonRelController {
	@Autowired
	private RailPersonRelService railPersonRelService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:railpersonrel:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<RailPersonRelEntity> railPersonRelList = railPersonRelService.queryList(query);
		int total = railPersonRelService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(railPersonRelList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:railpersonrel:info")
	public R info(@PathVariable("id") Integer id){
		RailPersonRelEntity railPersonRel = railPersonRelService.queryObject(id);
		
		return R.ok().put("railPersonRel", railPersonRel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:railpersonrel:save")
	public R save(@RequestBody RailPersonRelEntity railPersonRel){
		railPersonRelService.save(railPersonRel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:railpersonrel:update")
	public R update(@RequestBody RailPersonRelEntity railPersonRel){
		railPersonRelService.update(railPersonRel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:railpersonrel:delete")
	public R delete(@RequestBody Integer[] ids){
		railPersonRelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
