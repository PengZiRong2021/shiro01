package com.zking.shiro.mapper;

import com.zking.shiro.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(String username);

    //根据username查询该用户的所有角色，用于角色验证
    Set<String> findRoles(String username);

    //根据username查询他所拥有的权限信息，用于权限判断
    Set<String> findPermissions(String username);

}