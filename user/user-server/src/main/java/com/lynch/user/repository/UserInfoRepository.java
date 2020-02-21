package com.lynch.user.repository;

import com.lynch.user.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lynch on 2020-02-21.
 **/
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByOpenid(String openid);
}
