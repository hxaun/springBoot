package com.huajiance.springboot.web;

import com.alibaba.fastjson.JSONObject;
import com.huajiance.springboot.entity.TMenu;
import com.huajiance.springboot.service.MenuService;
import com.huajiance.springboot.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping("/getUserMenus.do")
    @ResponseBody
    public JSONObject getUserMenus() {
        List<TMenu> menuMap = null;
        Set<TMenu> menuSet = new HashSet<>();

        // 获取用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String roleId = authority.getAuthority();

            //获取当前角色下的菜单
            menuMap = menuService.getUserMenusByRoleId(roleId);
            menuSet.addAll(menuMap);
        }
        JSONObject json = new JSONObject();
//        List<String> menulist = menuService.getUserMenus();
        json.put("menuList", menuSet);
        logger.info("------------获取当前用户下属菜单---------------");
        return json;
    }
}