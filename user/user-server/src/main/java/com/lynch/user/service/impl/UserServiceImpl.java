package com.lynch.user.service.impl;

import com.lynch.user.domain.UserInfo;
import com.lynch.user.repository.UserInfoRepository;
import com.lynch.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lynch on 2020-02-21.
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
