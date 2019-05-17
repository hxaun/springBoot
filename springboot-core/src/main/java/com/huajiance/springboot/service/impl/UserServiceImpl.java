package com.huajiance.springboot.service.impl;

import com.huajiance.springboot.entity.TUser;
import com.huajiance.springboot.mapper.UserMapper;
import com.huajiance.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDao;

    @Override
    public TUser getByUserId(String userId) {
        TUser tUser = userDao.getByUserId(userId);
        return tUser;
    }


}
