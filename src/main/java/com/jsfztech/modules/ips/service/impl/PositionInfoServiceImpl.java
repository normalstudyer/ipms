package com.jsfztech.modules.ips.service.impl;

import com.alibaba.fastjson.JSON;
import com.jsfztech.modules.ips.dao.RailWarnningDao;
import com.jsfztech.modules.ips.entity.RailWarnningEntity;
import com.jsfztech.modules.ips.socket.IpsSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsfztech.modules.ips.dao.PositionInfoDao;
import com.jsfztech.modules.ips.entity.PositionInfoEntity;
import com.jsfztech.modules.ips.service.PositionInfoService;



@Service("positionInfoService")
public class PositionInfoServiceImpl implements PositionInfoService {
	@Autowired
	private PositionInfoDao positionInfoDao;
    @Autowired
    private RailWarnningDao railWarnningDao;
    @Autowired
    IpsSocket ipsSocket;

    private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 当前定位信息缓存(为了检测出围栏告警情况)
	 */
	private static Map<String,PositionInfoEntity> currentLabel=new HashMap<String,PositionInfoEntity>();

	@Override
	public PositionInfoEntity queryObject(Long id){
		return positionInfoDao.queryObject(id);
	}
	
	@Override
	public List<PositionInfoEntity> queryList(Map<String, Object> map){
		return positionInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return positionInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(PositionInfoEntity positionInfo,String tablename){

        //websocket推送的信息
        Map<String,Object> socketMap=new HashMap<String,Object>();
        //第一步，检测是否需要插入电子围栏告警信息
		PositionInfoEntity currentlabel=currentLabel.get(positionInfo.getTagId());
        int flag=0;
		if(currentlabel!=null){
            if("0".equals(currentlabel.getMsg())){
                if(!"0".equals(positionInfo.getMsg())){
                    flag=1;
                }
            }else{
                if(!positionInfo.getMsg().equals(currentlabel.getMsg())){
                    if("0".equals(positionInfo.getMsg())){
                        flag=2;
                    }else{
                        flag=1;
                    }
                }
            }
        }else{
            if(!"0".equals(positionInfo.getMsg())){
                flag=1;
            }
        }

        //如果检测到电子围栏预警信息，则插入
        if(flag>0){
            RailWarnningEntity rail=new RailWarnningEntity();
            rail.setLabelId(Integer.parseInt(positionInfo.getTagId()));
            if(flag==2){
                rail.setRailId(Integer.parseInt(currentlabel.getMsg()));
            }else{
                rail.setRailId(Integer.parseInt(positionInfo.getMsg()));
            }

            rail.setTriggerType(flag);
            railWarnningDao.save(rail);
            //推送告警信息
            socketMap.put("railwarnning",railWarnningDao.queryObject(rail.getId()));
        }
        //位置信息
        socketMap.put("location",positionInfo);
        try {
            //推送告警信息
            ipsSocket.sendInfo(JSON.toJSONString(socketMap));
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("socket信息推送失败，失败原因：map转JSON字符串失败");
        }
        //更新缓存
		currentLabel.put(positionInfo.getTagId(),positionInfo);
		//更新位置信息
		positionInfoDao.save(positionInfo,tablename);

	}
	
	@Override
	public void update(PositionInfoEntity positionInfo){
		positionInfoDao.update(positionInfo);
	}
	
	@Override
	public void delete(Long id){
		positionInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		positionInfoDao.deleteBatch(ids);
	}

    @Override
    public void queryTable(String tablename) {
        positionInfoDao.queryTable(tablename);
    }


}
