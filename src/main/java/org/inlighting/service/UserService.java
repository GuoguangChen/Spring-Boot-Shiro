package org.inlighting.service;

import org.inlighting.Entities.User;

public interface UserService {

    User findByUsername(String username);
}
