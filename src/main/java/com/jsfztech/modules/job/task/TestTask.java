package com.jsfztech.modules.job.task;

import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import com.jsfztech.modules.ips.service.PositionInfoService;
import com.jsfztech.modules.sys.entity.SysUserEntity;
import com.jsfztech.modules.sys.service.SysUserService;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 测试定时任务(演示Demo，可删除)
 * 
 * testTask为spring bean的名称
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2016年11月30日 下午1:34:24
 */
@Component("testTask")
public class TestTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private PositionInfoService positionInfoService;
	
	public void test(String params){
		logger.info("我是带参数的test方法，正在被执行，参数为：" + params);
		
//		try {
//			Thread.sleep(1000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
//		SysUserEntity user = sysUserService.queryObject(1L);
//		System.out.println(ToStringBuilder.reflectionToString(user));

	}

	
	public void test2(){
		logger.info("我是不带参数的test2方法，正在被执行");
	}

	public void test3(){
		logger.info("测试生成ips_position_info表格");
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String day = sf.format(date);
		String tablename = "ips_position_info"+day;

		positionInfoService.queryTable(tablename);
	}
}
