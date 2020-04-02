package com.justchat.demo.dto;

public class GroupDataDto {

    private String groupName;
    private String users;//get via ',' ;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
