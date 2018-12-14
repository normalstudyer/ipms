package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.MapInfoDao;
import com.jsfztech.modules.ips.entity.MapInfoEntity;
import com.jsfztech.modules.ips.service.MapInfoService;



@Service("mapInfoService")
public class MapInfoServiceImpl implements MapInfoService {
	@Autowired
	private MapInfoDao mapInfoDao;
	
	@Override
	public MapInfoEntity queryObject(Integer id){
		return mapInfoDao.queryObject(id);
	}
	
	@Override
	public List<MapInfoEntity> queryList(Map<String, Object> map){
		return mapInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return mapInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(MapInfoEntity mapInfo){
		mapInfoDao.save(mapInfo);
	}
	
	@Override
	public void update(MapInfoEntity mapInfo){
		mapInfoDao.update(mapInfo);
	}
	
	@Override
	public void delete(Integer id){
		mapInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		mapInfoDao.deleteBatch(ids);
	}
	
}
