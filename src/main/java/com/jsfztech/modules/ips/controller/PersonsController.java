package com.jsfztech.modules.ips.controller;

import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;
import com.jsfztech.modules.api.annotation.AuthIgnore;
import com.jsfztech.modules.ips.entity.ExportExcel;
import com.jsfztech.modules.ips.entity.PersonsEntity;
import com.jsfztech.modules.ips.entity.RailPersonRelEntity;
import com.jsfztech.modules.ips.service.PersonsService;
import com.jsfztech.modules.ips.service.PositionLabelService;
import com.jsfztech.modules.ips.service.RailPersonRelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 人员表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@RestController
@RequestMapping("/ips/persons")
public class PersonsController {
	@Autowired
	private PersonsService personsService;
	@Autowired
	private RailPersonRelService railPersonRelService;
	@Autowired
	private PositionLabelService positionLabelService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ips:persons:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);

		List<PersonsEntity> personsList = personsService.queryList(query);
		int total = personsService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(personsList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:persons:info")
	public R info(@PathVariable("id") Integer id){
		PersonsEntity persons = personsService.queryObject(id);
		
		return R.ok().put("persons", persons);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:persons:save")
	public R save(@RequestBody PersonsEntity persons){
		System.out.println("save==="+persons.getRail().get(0).getRailId());
		ArrayList<RailPersonRelEntity> rail=persons.getRail();
		 personsService.save(persons);
		for (RailPersonRelEntity railinfo:rail) {
			railinfo.setPersonId(persons.getId());
			railPersonRelService.save(railinfo);

		}
		//相关的标签信息进行修改
		Map<String,Object >map=new HashMap<>();
		map.put("isuse",1);
		map.put("id",persons.getLabelId());
		positionLabelService.updateuse(map);

		System.out.println(persons.getId());
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:persons:update")
	public R update(@RequestBody PersonsEntity persons){
		System.out.println("update==="+persons.getRail().get(0).getRailId());

		//相关的标签信息进行修改
		Map<String,Object >map=new HashMap<>();
		String [] laberids=persons.getLabelId().split(",");//前面是新的  后面是原来的
		map.put("isuse",1);
		map.put("id",laberids[0]);
		positionLabelService.updateuse(map);
		map.put("isuse",0);
		map.put("id",laberids[1]);
		positionLabelService.updateuse(map);
		persons.setLabelId(laberids[0]);
		personsService.update(persons);

		ArrayList<RailPersonRelEntity> rail=persons.getRail();

		//一律先删除 关联的所有数据
		railPersonRelService.deletePersionId(persons.getId());
		for (RailPersonRelEntity railinfo:rail) {

			railinfo.setPersonId(persons.getId());
			railPersonRelService.save(railinfo);

		}
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:persons:delete")
	public R delete(@RequestBody Integer[] ids){
		personsService.deleteBatch(ids);
		
		return R.ok();
	}
	/**
	 * person  excel导出
	 */
	@AuthIgnore
	@GetMapping("export")
/*	@RequestMapping("/export")
	@RequiresPermissions("ips:positionrail:export")*/
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response){
		Query query = new Query(params);
		List<Map<String,Object>> personsList = personsService.queryExport(query);
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");//设置日期格式
		String newDate = "告警记录表"+df.format(date);
		System.out.println(newDate);
		try {
			response.reset();
			response.setContentType("text/html; charset=utf-8");//不用改
			response.setContentType("application/msexcel");//不用改
			response.setHeader("Content-disposition", "attachment; filename=" + new String(newDate.getBytes("GBK"), "ISO-8859-1") + ".xlsx");//文件名
			ExportExcel<Map<String, Object>> ee = new ExportExcel<>();//导出的工具类，泛型填的bean跟上面的list里的泛型bean必须一样
			String title = "告警记录";//excel里的sheet名
			String[] headers = {"姓名", "部门", "性别", "民族", "出生日期", "职位","工号","联系方式","家庭住址","标签编号"};//表头
			String[] fieldNames = {"name", "deptName", "sex", "nation", "birth", "job","employeeNumber","phone","address","labelId"};//list里的bean里的属性名，注意要跟表头对应
			OutputStream out = response.getOutputStream();
			ee.exportExcel2007(title, headers, personsList, fieldNames, out, "yyyy-MM-dd hh:mm:ss");
		}catch (Exception e){
			e.printStackTrace();
		}

	}
	
}
