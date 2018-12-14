package com.jsfztech.modules.job.task;

import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import com.jsfztech.modules.ips.service.PositionInfoService;
import com.jsfztech.modules.ips.socket.IpsSocket;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 室内定位信息定时任务，向页面定时推送定位数据
 * @author adams
 * @date 2018年8月7日
 */
@Component("socketTask")
public class SocketTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    PositionInfoService positionInfoService;

    @Autowired
    IpsSocket ipsSocket;

    public void pushData(){
        logger.info("pushData定时任务正在被执行");
        List<PositionInfoEntity> newPositionList=positionInfoService.queryList(null);
        try {
            ipsSocket.sendInfo(JSON.toJSONString(newPositionList));
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("pushData执行失败，失败原因：list转JSON字符串失败");
        }

    }
    public void pushPositionData(){
        logger.info("每日0点生成一张ips_position_info表");
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String day = sf.format(date);
        String tablename = "ips_position_info"+day;

        positionInfoService.queryTable(tablename);
    }

}
