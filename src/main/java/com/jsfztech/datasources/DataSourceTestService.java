package com.jsfztech.datasources;

import com.jsfztech.datasources.annotation.DataSource;
import com.jsfztech.modules.api.entity.UserEntity;
import com.jsfztech.modules.api.service.UserService;
import com.jsfztech.datasources.annotation.DataSource;
import com.jsfztech.modules.api.entity.UserEntity;
import com.jsfztech.modules.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017/9/16 23:10
 */
@Service
public class DataSourceTestService {
    @Autowired
    private UserService userService;

    public UserEntity queryObject(Long userId){
        return userService.queryObject(userId);
    }

    @DataSource(name = DataSourceNames.SECOND)
    public UserEntity queryObject2(Long userId){
        return userService.queryObject(userId);
    }
}
