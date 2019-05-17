package com.huajiance.springboot.service;

import com.huajiance.springboot.entity.TUserRole;

import java.util.List;

public interface RoleService {

    List<TUserRole> getRoleByUserId(String username);

    List<String> getRoleByUrl(String code, String name);
}
