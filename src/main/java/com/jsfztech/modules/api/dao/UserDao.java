package com.jsfztech.modules.api.dao;

import com.jsfztech.modules.api.entity.UserEntity;
import com.jsfztech.modules.api.entity.UserEntity;
import com.jsfztech.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);
}
