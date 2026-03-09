package com.wl.big_things.service.impl;

import com.wl.big_things.mapper.UserMapper;
import com.wl.big_things.pojo.User;
import com.wl.big_things.service.UserService;
import com.wl.big_things.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        String md5String =Md5Util.getMD5String(password);
        userMapper.add(username,md5String);
    }
}
