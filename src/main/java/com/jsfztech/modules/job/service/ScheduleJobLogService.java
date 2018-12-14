package com.jsfztech.modules.job.service;

import com.jsfztech.modules.job.entity.ScheduleJobLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2016年12月1日 下午10:34:48
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 */
	ScheduleJobLogEntity queryObject(Long jobId);
	
	/**
	 * 查询定时任务日志列表
	 */
	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 */
	void save(ScheduleJobLogEntity log);
	
}
