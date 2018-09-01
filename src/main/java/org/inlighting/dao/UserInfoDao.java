package org.inlighting.dao;

import org.inlighting.Entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo,Long> {

    UserInfo findByUsername(String username);
}
