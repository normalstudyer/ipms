package com.jsfztech.modules.job.dao;

import com.jsfztech.modules.job.entity.ScheduleJobLogEntity;
import com.jsfztech.modules.job.entity.ScheduleJobLogEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2016年12月1日 下午10:30:02
 */
@Mapper
public interface ScheduleJobLogDao extends BaseDao<ScheduleJobLogEntity> {
	
}
