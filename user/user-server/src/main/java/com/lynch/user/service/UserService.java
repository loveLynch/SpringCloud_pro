package com.lynch.user.service;

import com.lynch.user.domain.UserInfo;

/**
 * Created by lynch on 2020-02-21.
 **/
public interface UserService {
    /**
     * 通过openid来查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);
}
