package com.justchat.demo.controller;

import com.justchat.demo.dto.GroupDataDto;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.Group;
import com.justchat.demo.service.GroupService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@RestController
public class GroupController {


    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @RequestMapping("/page-to-create-group")
    public ModelAndView sign(){
        return new ModelAndView("group");
    }


    @RequestMapping("/create-group")
    public String createGroup(@RequestBody GroupDataDto groupDataDto ){


   groupService.createGroup(groupDataDto.getGroupName(),groupDataDto.getUsers(),getLoginCurrentUser());

        return "OK";
    }




    @RequestMapping(value = "/get-all-groups" ,method = RequestMethod.POST)
    public Set<Group> getAllUsers(){

        CustomUser currentUser =userService.getUserByLogin(getLoginCurrentUser());
        return  userService.getSavedGroups(currentUser);

    }




    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();

        return login;
    }


}
