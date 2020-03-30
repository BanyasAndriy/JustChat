package com.justchat.demo.controller;

import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.service.MessageService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
@RestController
public class UserController {



@Autowired
    MessageService messageService;

@Autowired
    UserService userService;

    @RequestMapping(value = "/get-all-users" ,method = RequestMethod.POST)
    public List<CustomUser> getHistory(){


        List<CustomUser> customUsers =userService.getAllUser();


return customUsers;

    }


    @RequestMapping(value = "/get-current-user" ,method = RequestMethod.POST)
    public CustomUser getCurrentUser(){


        CustomUser customUsers =userService.getUserByLogin(getLoginCurrentUser());





        return customUsers;

    }


    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();


        return login;
    }

}
