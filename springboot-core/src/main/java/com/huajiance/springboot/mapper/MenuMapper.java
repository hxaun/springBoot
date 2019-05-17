package com.huajiance.springboot.mapper;

import com.huajiance.springboot.entity.TMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    List<TMenu> getUserMenus();

    List<TMenu> getMenusByRoleId(@Param("roleId") String roleId);
}
