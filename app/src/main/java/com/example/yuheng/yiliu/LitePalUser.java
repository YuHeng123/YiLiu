package com.example.yuheng.yiliu;

import org.litepal.crud.LitePalSupport;

/**
 * LitePal数据库中的user表
 */
public class LitePalUser extends LitePalSupport{

    private int id;
    private String username;            //用户名
    private String password;            //密码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;

    }
}
