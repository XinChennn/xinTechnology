package cn.ixinjiu.springsecurity.model.domain;

import cn.ixinjiu.springsecurity.model.LoginDetail;
import cn.ixinjiu.springsecurity.model.TokenDetail;

/**
 * Created by XinChen on 2022/8/25
 *
 * @Description : TODO
 */
public class User implements LoginDetail, TokenDetail {

    private String username;
    private String password;
    private String authorities;
    private Long lastPasswordChange;
    private char enable;

    public User() {
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAuthorities() {
        return authorities;
    }

    public User setAuthorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    public Long getLastPasswordChange() {
        return lastPasswordChange;
    }

    public User setLastPasswordChange(Long lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
        return this;
    }

    public User setEnable(char enable) {
        this.enable = enable;
        return this;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean enable() {
        if (this.enable == '1'){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities='" + authorities + '\'' +
                ", lastPasswordChange=" + lastPasswordChange +
                ", enable=" + enable +
                '}';
    }
}
