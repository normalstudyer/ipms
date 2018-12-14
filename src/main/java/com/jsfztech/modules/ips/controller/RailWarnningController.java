package com.jsfztech.modules.ips.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jsfztech.modules.ips.entity.RailWarnningEntity;
import com.jsfztech.modules.ips.service.RailWarnningService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;


/**
 * 电子围栏告警表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@RestController
@RequestMapping("/ips/railwarnning")
public class RailWarnningController {
	@Autowired
	private RailWarnningService railWarnningService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:railwarnning:list")
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
		List<Map<String,Object>> railWarnningList = railWarnningService.queryList1(query);
		int total = railWarnningService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(railWarnningList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:railwarnning:info")
	public R info(@PathVariable("id") Integer id){
		RailWarnningEntity railWarnning = railWarnningService.queryObject(id);
		
		return R.ok().put("railWarnning", railWarnning);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:railwarnning:save")
	public R save(@RequestBody RailWarnningEntity railWarnning){
		railWarnningService.save(railWarnning);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:railwarnning:update")
	public R update(@RequestBody RailWarnningEntity railWarnning){
		railWarnningService.update(railWarnning);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:railwarnning:delete")
	public R delete(@RequestBody Integer[] ids){
		railWarnningService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 获取告警记录表中每个围栏的进出次数
	 */
	@RequestMapping("/showecharts")

	public R showecharts(@RequestBody Map<String,Object> map){
        System.out.println(map.get("id"));
		List<Map<String,Object>> list = railWarnningService.queryEcharts();
        System.out.println(list);
		return R.ok().put("list",list);
	}
	
}
