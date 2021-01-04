package com.zking.shiro.realm;

import com.zking.shiro.model.User;
import com.zking.shiro.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //得到用户的账号
        String  username = principalCollection.getPrimaryPrincipal().toString();
        //授权2部分
        //1.查询角色
        Set<String> roles = userService.findRoles(username);
        //2.查询权限
        Set<String> permissions = userService.findPermissions(username);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
         //获得用户账号密码
        String username = authenticationToken.getPrincipal().toString();
        System.out.println("username=="+username);
        String password=authenticationToken.getCredentials().toString();
        System.out.println("password=="+password);
       //通过用户输入的账号得到该账号的信息
        User user = userService.login(username);
        if(null==user){
            throw new RuntimeException("未知账号");
        }
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                this.getName()
        );

        return info;
    }
}
