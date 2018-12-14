package com.jsfztech.modules.sys.dao;

import com.jsfztech.modules.sys.entity.SysUserTokenEntity;
import com.jsfztech.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface SysUserTokenDao extends BaseDao<SysUserTokenEntity> {
    
    SysUserTokenEntity queryByUserId(Long userId);

    SysUserTokenEntity queryByToken(String token);
	
}
