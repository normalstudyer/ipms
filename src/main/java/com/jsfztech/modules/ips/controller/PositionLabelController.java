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

import com.jsfztech.modules.ips.entity.PositionLabelEntity;
import com.jsfztech.modules.ips.service.PositionLabelService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;




/**
 * 定位标签表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-08-27 16:15:52
 */
@RestController
@RequestMapping("/ips/positionlabel")
public class PositionLabelController {
	@Autowired
	private PositionLabelService positionLabelService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:positionlabel:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PositionLabelEntity> positionLabelList = positionLabelService.queryList(query);
		int total = positionLabelService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(positionLabelList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:positionlabel:info")
	public R info(@PathVariable("id") Integer id){
		PositionLabelEntity positionLabel = positionLabelService.queryObject(id);
		
		return R.ok().put("positionLabel", positionLabel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:positionlabel:save")
	public R save(@RequestBody PositionLabelEntity positionLabel){
		positionLabelService.save(positionLabel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:positionlabel:update")
	public R update(@RequestBody PositionLabelEntity positionLabel){
		positionLabelService.update(positionLabel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:positionlabel:delete")
	public R delete(@RequestBody Integer[] ids){
		positionLabelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
