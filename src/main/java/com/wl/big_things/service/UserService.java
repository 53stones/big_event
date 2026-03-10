package com.wl.big_things.service;

import com.wl.big_things.pojo.User;
public interface UserService {
    User findByUserName(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
