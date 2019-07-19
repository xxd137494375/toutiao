package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/1811:42
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
