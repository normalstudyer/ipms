package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.PositionLabelDao;
import com.jsfztech.modules.ips.entity.PositionLabelEntity;
import com.jsfztech.modules.ips.service.PositionLabelService;



@Service("positionLabelService")
public class PositionLabelServiceImpl implements PositionLabelService {
	@Autowired
	private PositionLabelDao positionLabelDao;
	
	@Override
	public PositionLabelEntity queryObject(Integer id){
		return positionLabelDao.queryObject(id);
	}
	
	@Override
	public List<PositionLabelEntity> queryList(Map<String, Object> map){
		return positionLabelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return positionLabelDao.queryTotal(map);
	}
	
	@Override
	public void save(PositionLabelEntity positionLabel){
		positionLabelDao.save(positionLabel);
	}
	
	@Override
	public void update(PositionLabelEntity positionLabel){
		positionLabelDao.update(positionLabel);
	}
	
	@Override
	public void delete(Integer id){
		positionLabelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		positionLabelDao.deleteBatch(ids);
	}
	@Override
	public void updateuse(Map<String, Object> map){
		positionLabelDao.updateuse(map);
	}
	
}
