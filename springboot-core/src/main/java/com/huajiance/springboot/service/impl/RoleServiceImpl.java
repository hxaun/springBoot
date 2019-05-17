package com.huajiance.springboot.service.impl;

import com.huajiance.springboot.entity.TUserRole;
import com.huajiance.springboot.mapper.RoleMapper;
import com.huajiance.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleDao;

    @Override
    public List<TUserRole> getRoleByUserId(String userId) {
        List<TUserRole> roleList = roleDao.getRoleByUserId(userId);
        return roleList;
    }

    @Override
    public List<String> getRoleByUrl(String code, String name) {
        return roleDao.getRoleByUrl(code, name);
    }
}
