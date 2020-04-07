package com.justchat.demo.controller;

import com.justchat.demo.dto.CustomUserDto;
import com.justchat.demo.dto.GroupDataDto;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.Group;
import com.justchat.demo.service.GroupService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.Set;

@RestController
public class GroupController {


    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @RequestMapping("/page-to-create-group")
    public ModelAndView sign() {
        return new ModelAndView("group");
    }


    @RequestMapping("/create-group")
    public String createGroup(@RequestBody GroupDataDto groupDataDto) {


        groupService.createGroup(groupDataDto.getGroupName(), groupDataDto.getUsers(), getLoginCurrentUser());

        return "OK";
    }


    @RequestMapping(value = "/get-all-groups", method = RequestMethod.POST)
    public Set<Group> getAllUsers() {

        CustomUser currentUser = userService.getUserByLogin(getLoginCurrentUser());
        return userService.getSavedGroups(currentUser);

    }


    @RequestMapping(value = "/get-all-users-by-group", method = RequestMethod.POST)
    public List<CustomUserDto> getAllUsersByGroup(@RequestBody GroupDataDto groupName) {


        List<CustomUserDto> users = groupService.getUsersByGroup(groupName.getGroupName());

        return users;

    }

    @RequestMapping(value = "/delete-user-from-group", method = RequestMethod.POST)
    public String  deleteUserFromGroup(@RequestBody GroupDataDto groupData) {


        if (groupService.deleteUserFromGroup(groupData.getGroupName(),groupData.getUsers())){
            return "OK";
        }else return "ERROR";


    }


    @RequestMapping(value = "/delete-group", method = RequestMethod.POST)
    public String  deleteGroup(@RequestBody GroupDataDto groupData) {


        if (groupService.deleteGroup(groupData.getGroupName())){
            return "OK";
        }else return "ERROR";


    }







    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();

        return login;
    }






}
