package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.RailPersonRelDao;
import com.jsfztech.modules.ips.entity.RailPersonRelEntity;
import com.jsfztech.modules.ips.service.RailPersonRelService;



@Service("railPersonRelService")
public class RailPersonRelServiceImpl implements RailPersonRelService {
	@Autowired
	private RailPersonRelDao railPersonRelDao;
	
	@Override
	public RailPersonRelEntity queryObject(Integer id){
		return railPersonRelDao.queryObject(id);
	}
	
	@Override
	public List<RailPersonRelEntity> queryList(Map<String, Object> map){
		return railPersonRelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return railPersonRelDao.queryTotal(map);
	}
	
	@Override
	public void save(RailPersonRelEntity railPersonRel){
		railPersonRelDao.save(railPersonRel);
	}
	
	@Override
	public void update(RailPersonRelEntity railPersonRel){
		railPersonRelDao.update(railPersonRel);
	}
	
	@Override
	public void delete(Integer id){
		railPersonRelDao.delete(id);
	}
	@Override
	public void deletePersionId(Integer persionId){
		railPersonRelDao.deletePersionId(persionId);
	}
	@Override
	public void deleteBatch(Integer[] ids){
		railPersonRelDao.deleteBatch(ids);
	}

	
}
