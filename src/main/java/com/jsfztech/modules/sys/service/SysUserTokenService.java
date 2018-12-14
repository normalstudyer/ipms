package com.jsfztech.modules.sys.service;

import com.jsfztech.common.utils.R;
import com.jsfztech.modules.sys.entity.SysUserTokenEntity;
import com.jsfztech.common.utils.R;

/**
 * 用户Token
 * 
 * @author xujianhua
 * @email xujianhua@outlook.com
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService {

	SysUserTokenEntity queryByUserId(Long userId);

	SysUserTokenEntity queryByToken(String token);
	
	void save(SysUserTokenEntity token);
	
	void update(SysUserTokenEntity token);

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

}
