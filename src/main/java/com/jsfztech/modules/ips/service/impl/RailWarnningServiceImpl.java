package com.jsfztech.modules.ips.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.RailWarnningDao;
import com.jsfztech.modules.ips.entity.RailWarnningEntity;
import com.jsfztech.modules.ips.service.RailWarnningService;



@Service("railWarnningService")
public class RailWarnningServiceImpl implements RailWarnningService {
	@Autowired
	private RailWarnningDao railWarnningDao;
	
	@Override
	public RailWarnningEntity queryObject(Integer id){
		return railWarnningDao.queryObject(id);
	}

	@Override
	public List<Map<String,Object>> queryList1(Map<String, Object> map){
		return railWarnningDao.queryList1(map);
	}

	@Override
	public List<Map<String,Object>> queryExportList(Map<String, Object> map){
        List<Map<String,Object>> list = railWarnningDao.queryExportList(map);
        return list;
        /*for(int i=0;i<list.size();i++){

		}
		List<ExcelBean> excel = new ArrayList<>();
		Map<Integer,List<ExcelBean>> map1 = new LinkedHashMap<>();
		XSSFWorkbook xssfWorkbook=null;
		//设置标题栏
		excel.add(new ExcelBean("序号","id",0));
		excel.add(new ExcelBean("围栏名称","rail_name",0));
		excel.add(new ExcelBean("人员名称","target_name",0));
		excel.add(new ExcelBean("触发时间","trigger_date",0));
		excel.add(new ExcelBean("1:进入,2:离开","trigger_type",0));
		excel.add(new ExcelBean("信息描述","info",0));
		map1.put(0,excel);
		String sheetName = "告警记录表";
		//调用ExcelUtil的方法
		xssfWorkbook = ExcelUtil.createExcelFile(RailWarnningEntity1.class,list,map1,sheetName);
		return xssfWorkbook;*/
	}

	/**
	 * 获取告警记录表中每个围栏的进出次数
	 * @return
	 */
	@Override
	public List<Map<String,Object>> queryEcharts(){
		List<Map<String,Object>> list = railWarnningDao.queryEcharts();
		List<String> list_railname = new ArrayList<>();//将围栏名称放在一起
		List<Object> list_innumbers = new ArrayList<>();//将进入次数放在一起
		List<Object> list_outnumbers = new ArrayList<>();//将出去次数放在一起
		Map<String, Object> data = new HashMap<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> ret = list.get(i);
			String railName = ret.get("railName") == null ? "":ret.get("railName").toString();
			String inNumbers = ret.get("inNumbers") == null ? "":ret.get("inNumbers").toString();
			String outNumbers = ret.get("outNumbers") == null ? "":ret.get("outNumbers").toString();
			list_railname.add(railName);
			list_innumbers.add(inNumbers);
			list_outnumbers.add(outNumbers);
		}
		data.put("railName",list_railname);
		data.put("inNumbers",list_innumbers);
		data.put("outNumbers",list_outnumbers);
		resultList.add(data);
		return resultList;
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return railWarnningDao.queryTotal(map);
	}
	
	@Override
	public void save(RailWarnningEntity railWarnning){
		railWarnningDao.save(railWarnning);
	}
	
	@Override
	public void update(RailWarnningEntity railWarnning){
		railWarnningDao.update(railWarnning);
	}
	
	@Override
	public void delete(Integer id){
		railWarnningDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		railWarnningDao.deleteBatch(ids);
	}
	
}
