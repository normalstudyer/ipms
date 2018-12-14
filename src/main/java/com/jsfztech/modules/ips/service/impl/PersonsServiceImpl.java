package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.PersonsDao;
import com.jsfztech.modules.ips.entity.PersonsEntity;
import com.jsfztech.modules.ips.service.PersonsService;



@Service("personsService")
public class PersonsServiceImpl implements PersonsService {
	@Autowired
	private PersonsDao personsDao;
	
	@Override
	public PersonsEntity queryObject(Integer id){
		return personsDao.queryObject(id);
	}
	
	@Override
	public List<PersonsEntity> queryList(Map<String, Object> map){
		return personsDao.queryList(map);
	}
	@Override
	public List<Map<String,Object>> queryExport (Map<String, Object> map){
		return personsDao.queryExport(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return personsDao.queryTotal(map);
	}
	
	@Override
	public void save(PersonsEntity persons){
		personsDao.save(persons);
	}
	
	@Override
	public void update(PersonsEntity persons){
		personsDao.update(persons);
	}
	
	@Override
	public void delete(Integer id){
		personsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		personsDao.deleteBatch(ids);
	}

	@Override
	public List<Map<String,Object>> queryPersonTreeList(){
		return personsDao.queryPersonTreeList();
	};
	
}
