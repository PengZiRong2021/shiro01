package com.zking.shiro.Test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class Demo {
    public static void main(String[] args) {
        //读取ini的配置
        IniSecurityManagerFactory ini=new IniSecurityManagerFactory("classpath:shiro.ini");
        //创建安全管理器
        SecurityManager instance = ini.getInstance();
        //3.将SecurityManager委托给SecurityUtils管理
        SecurityUtils.setSecurityManager(instance);
        //得到主体
        Subject subject = SecurityUtils.getSubject();
        //创建token令牌
        UsernamePasswordToken token=new UsernamePasswordToken(
                 "ww","789"
        );
        //6.身份验证
        try {
            subject.login(token);
            System.out.println("登录ok");
            if(subject.hasRole("role3")){
                if(subject.isPermitted("update")){
                    System.out.println("有这个权限");
                }else{
                    System.out.println("没有权限");
                }

            }else{
                System.out.println("no");
            }
        }catch(UnknownAccountException ua){
            System.out.println("账号不存在");
        }catch(IncorrectCredentialsException ic){
            System.out.println("密码错误");
        }
        catch (AuthenticationException e) {
            e.printStackTrace();
        }

        //7.安全退出
        subject.logout();

    }
}
