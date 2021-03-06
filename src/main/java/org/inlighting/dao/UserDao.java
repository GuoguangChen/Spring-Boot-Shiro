package org.inlighting.dao;

import org.inlighting.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User,Long> {

    User findByUsername(String username);
}
