package com.justchat.demo.dto;

import com.justchat.demo.entity.CustomUser;

public class CustomUserDto {

    private  String avatar;
    private   String login;
    private boolean isAdmin;


    public CustomUserDto() {
    }

    public CustomUserDto( String login , String avatar , boolean isAdmin) {
        this.avatar = avatar;
        this.login = login;
        this.isAdmin=isAdmin;
    }




    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
