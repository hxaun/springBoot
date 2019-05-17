package com.huajiance.springboot.mapper;

import com.huajiance.springboot.entity.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    TUser getByUserId(@Param("userId")String userId);

}
