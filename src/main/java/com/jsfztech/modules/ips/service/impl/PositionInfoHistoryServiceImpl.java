package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.PositionInfoHistoryDao;
import com.jsfztech.modules.ips.entity.PositionInfoHistoryEntity;
import com.jsfztech.modules.ips.service.PositionInfoHistoryService;



@Service("positionInfoHistoryService")
public class PositionInfoHistoryServiceImpl implements PositionInfoHistoryService {
	@Autowired
	private PositionInfoHistoryDao positionInfoHistoryDao;
	
	@Override
	public PositionInfoHistoryEntity queryObject(Long id){
		return positionInfoHistoryDao.queryObject(id);
	}
	
	@Override
	public List<PositionInfoHistoryEntity> queryList(Map<String, Object> map){
		return positionInfoHistoryDao.queryList(map);
	}

	@Override
	public List<Map<String, Object>> queryExportList(Map<String, Object> map) {
		return positionInfoHistoryDao.queryExportList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return positionInfoHistoryDao.queryTotal(map);
	}
	
	@Override
	public void save(PositionInfoHistoryEntity positionInfoHistory){
		positionInfoHistoryDao.save(positionInfoHistory);
	}
	
	@Override
	public void update(PositionInfoHistoryEntity positionInfoHistory){
		positionInfoHistoryDao.update(positionInfoHistory);
	}
	
	@Override
	public void delete(Long id){
		positionInfoHistoryDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		positionInfoHistoryDao.deleteBatch(ids);
	}

	@Override
	public List<PositionInfoHistoryEntity> queryByTagId(Map<String, Object> map){
		return positionInfoHistoryDao.queryByTagId(map);
	}
	
}
