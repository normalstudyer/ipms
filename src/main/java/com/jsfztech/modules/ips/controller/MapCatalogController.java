package com.jsfztech.modules.ips.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsfztech.common.utils.Constant;
import com.jsfztech.modules.sys.controller.AbstractController;
import com.jsfztech.modules.sys.entity.SysDeptEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsfztech.modules.ips.entity.MapCatalogEntity;
import com.jsfztech.modules.ips.service.MapCatalogService;
import com.jsfztech.common.utils.PageUtils;
import com.jsfztech.common.utils.Query;
import com.jsfztech.common.utils.R;

import static com.jsfztech.common.utils.ShiroUtils.getUserId;


/**
 * 地图目录表
 * 
 * @author adams
 * @email 278091388@qq.com
 * @date 2018-09-26 10:32:31
 */
@RestController
@RequestMapping("/ips/mapcatalog")
public class MapCatalogController extends AbstractController{
	@Autowired
	private MapCatalogService mapCatalogService;
	
	/**
	 * 列表
	 */
	/*@RequestMapping("/list")
	@RequiresPermissions("ips:mapcatalog:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MapCatalogEntity> mapCatalogList = mapCatalogService.queryList(query);
		int total = mapCatalogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(mapCatalogList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}*/
	@RequestMapping("/list")
	@RequiresPermissions("ips:mapcatalog:list")
	public List<MapCatalogEntity> list(){
		List<MapCatalogEntity> mapList = mapCatalogService.queryList(new HashMap<>());

		return mapList;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("ips:mapcatalog:list")
	public R select(){
		List<MapCatalogEntity> mapList = mapCatalogService.queryList(new HashMap<>());

		//添加一级地图
		if(getUserId() == Constant.SUPER_ADMIN){
			MapCatalogEntity root = new MapCatalogEntity();
			root.setId(0);
			root.setName("一级地图");
			root.setPid(-1);
			root.setOpen(true);
			mapList.add(root);
		}

		return R.ok().put("mapList", mapList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("ips:mapcatalog:list")
	public R info(){
		long mapId = 0;
		if(getUserId() != Constant.SUPER_ADMIN){
			MapCatalogEntity mapcatalog = mapCatalogService.queryParentObject(getDeptId());
			mapId = mapcatalog.getId();
		}

		return R.ok().put("mapId", mapId);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ips:mapcatalog:info")
	public R info(@PathVariable("id") Integer id){
		MapCatalogEntity mapCatalog = mapCatalogService.queryObject(id);
		
		return R.ok().put("mapCatalog", mapCatalog);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ips:mapcatalog:save")
	public R save(@RequestBody MapCatalogEntity mapCatalog){
		mapCatalogService.save(mapCatalog);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ips:mapcatalog:update")
	public R update(@RequestBody MapCatalogEntity mapCatalog){
		mapCatalogService.update(mapCatalog);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ips:mapcatalog:delete")
	public R delete(Integer ids){
		//判断是否有子部门
		List<Integer> mapList = mapCatalogService.queryMapIdList(ids);
		if(mapList.size() > 0){
			return R.error("请先删除子部门");
		}
		//mapCatalogService.deleteBatch(ids);
		mapCatalogService.delete(ids);
		return R.ok();
	}
	
}
