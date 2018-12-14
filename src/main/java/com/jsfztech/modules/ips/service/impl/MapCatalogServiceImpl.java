package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.MapCatalogDao;
import com.jsfztech.modules.ips.entity.MapCatalogEntity;
import com.jsfztech.modules.ips.service.MapCatalogService;



@Service("mapCatalogService")
public class MapCatalogServiceImpl implements MapCatalogService {
	@Autowired
	private MapCatalogDao mapCatalogDao;
	
	@Override
	public MapCatalogEntity queryObject(Integer id){
		return mapCatalogDao.queryObject(id);
	}

	@Override
	public MapCatalogEntity queryParentObject(Long deptId){
		return mapCatalogDao.queryParentObject(deptId);
	}

	@Override
	public List<MapCatalogEntity> queryList(Map<String, Object> map){
		return mapCatalogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return mapCatalogDao.queryTotal(map);
	}
	
	@Override
	public void save(MapCatalogEntity mapCatalog){
		mapCatalogDao.save(mapCatalog);
	}
	
	@Override
	public void update(MapCatalogEntity mapCatalog){
		mapCatalogDao.update(mapCatalog);
	}
	
	@Override
	public void delete(Integer id){
		mapCatalogDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		mapCatalogDao.deleteBatch(ids);
	}

	@Override
	public List<Integer> queryMapIdList(Integer parentId) {
		return mapCatalogDao.queryMapIdList(parentId);
	}
}
