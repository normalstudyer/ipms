package com.jsfztech.modules.api.dao;

import com.jsfztech.modules.api.entity.TokenEntity;
import com.jsfztech.modules.api.entity.TokenEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Token
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-03-23 15:22:07
 */
@Mapper
public interface TokenDao extends BaseDao<TokenEntity> {
    
    TokenEntity queryByUserId(Long userId);

    TokenEntity queryByToken(String token);
	
}
