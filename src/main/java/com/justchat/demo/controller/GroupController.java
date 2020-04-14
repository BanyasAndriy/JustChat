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
    public List<GroupDataDto> getAllGroups() {

        CustomUser currentUser = userService.getUserByLogin(getLoginCurrentUser());
if (currentUser==null){
    return null;
}
        List<GroupDataDto> groups = userService.getSavedGroups(currentUser);
        return  groups;

    }


    @RequestMapping(value = "/get-all-users-by-group", method = RequestMethod.POST)
    public List<CustomUserDto> getAllUsersByGroup(@RequestBody GroupDataDto groupName) {


        List<CustomUserDto> users = groupService.getUsersByGroup(groupName.getGroupName());

        return users;

    }

    @RequestMapping(value = "/delete-user-from-group", method = RequestMethod.POST)
    public String deleteUserFromGroup(@RequestBody GroupDataDto groupData) {


        if (groupService.deleteUserFromGroup(groupData.getGroupName(), groupData.getUsers())) {
            return "OK";
        } else return "ERROR";


    }


    @RequestMapping(value = "/delete-group", method = RequestMethod.POST)
    public String deleteGroup(@RequestBody GroupDataDto groupData) {


        if (groupService.deleteGroup(groupData.getGroupName())) {
            return "OK";
        } else return "ERROR";


    }

    @RequestMapping(value = "/add-user-to-group", method = RequestMethod.POST)
    public String addUserToGroup(@RequestBody GroupDataDto groupData) {


       return groupService.addUserToGroup(groupData.getGroupName(), groupData.getUsers())  ? "OK" : "Error";


    }


    @RequestMapping(value = "/get-all-unsigned-users", method = RequestMethod.POST)
    public List<CustomUserDto> getUnsignedUsers(@RequestBody GroupDataDto groupData) {

        return groupService.getUnsignedUsers(groupData.getGroupName(), getLoginCurrentUser());

    }


    @RequestMapping(value = "/change-group-name", method = RequestMethod.POST)
    public String changeGroupName(@RequestBody GroupDataDto groupData) {



        Group group =  groupService.changeGroupName(groupData.getGroupName() , groupData.getNewName());

        if (group!=null){
            return group.getName();
        }else return "Error";

    }

    @RequestMapping(value = "/leave-group", method = RequestMethod.POST)
    public String leaveGroup(@RequestBody GroupDataDto groupData) {


        return userService.leaveGroup(getLoginCurrentUser(),groupData.getGroupName())? "OK" : "ERROR";



    }


/*
    @RequestMapping(value = "/clear-notification-in-group", method = RequestMethod.POST)
    public String clearNotificationInGroup(@RequestBody GroupDataDto groupData) {


groupService.clearNotificationInGroup(groupData.getGroupName());
        return "OK";




    }
*/
    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();

        return login;
    }


}
