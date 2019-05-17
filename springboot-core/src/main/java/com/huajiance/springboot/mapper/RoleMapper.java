package com.huajiance.springboot.mapper;

import com.huajiance.springboot.entity.TUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {


    List<TUserRole> getRoleByUserId(@Param("userId") String userId);

    List<String> getRoleByUrl(@Param("code") String code,@Param("name") String name);
}
