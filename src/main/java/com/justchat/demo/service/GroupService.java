package com.justchat.demo.service;

import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.Group;
import com.justchat.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class GroupService {



    @Autowired
    UserService userService;

    @Autowired
    GroupRepository groupRepository;

    @Transactional
    public void createGroup(String groupName, String users, String loginCurrentUser) {

        CustomUser currentUser = userService.getUserByLogin(loginCurrentUser);
        List<CustomUser> customUserList=new ArrayList<>();
        String[] customUsers = users.trim().split(",");
if (customUsers.length>0) {
    for (int i = 0; i < customUsers.length; i++) {

        customUserList.add(userService.getUserByLogin(customUsers[i]));
    }
}
        customUserList.add(userService.getUserByLogin(loginCurrentUser));
        Group group = new Group(groupName,loginCurrentUser,customUserList);

        currentUser.initGroup(group);
        if (customUsers.length>0) {
            for (int i = 0; i < customUsers.length; i++) {

               userService.getUserByLogin(customUsers[i]).initGroup(group);
            }
        }
        groupRepository.save(group);

    }
}
