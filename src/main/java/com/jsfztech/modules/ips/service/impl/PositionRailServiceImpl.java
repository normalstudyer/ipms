package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.PositionRailDao;
import com.jsfztech.modules.ips.entity.PositionRailEntity;
import com.jsfztech.modules.ips.service.PositionRailService;



@Service("positionRailService")
public class PositionRailServiceImpl implements PositionRailService {
	@Autowired
	private PositionRailDao positionRailDao;
	
	@Override
	public PositionRailEntity queryObject(Integer id){
		return positionRailDao.queryObject(id);
	}
	
	@Override
	public List<PositionRailEntity> queryList(Map<String, Object> map){
		return positionRailDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return positionRailDao.queryTotal(map);
	}
	
	@Override
	public void save(PositionRailEntity positionRail){
		positionRailDao.save(positionRail);
	}
	
	@Override
	public void update(PositionRailEntity positionRail){
		positionRailDao.update(positionRail);
	}
	
	@Override
	public void delete(Integer id){
		positionRailDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		positionRailDao.deleteBatch(ids);
	}
	
}
