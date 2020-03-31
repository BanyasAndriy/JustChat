package com.justchat.demo.controller;

import com.justchat.demo.dto.NetworkData;
import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.service.MessageService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/save-network" ,method = RequestMethod.POST)
    public String saveNetwork(@RequestBody NetworkData network){
        CustomUser customUsers =userService.getUserByLogin(getLoginCurrentUser());

if (network.getNameOfTheNetwork().toLowerCase().trim().equals("facebook")){
    customUsers.setFacebook(network.getUrlOfTheNetwork());
}else if (network.getNameOfTheNetwork().toLowerCase().trim().equals("twitter")){
    customUsers.setTwitter((network.getUrlOfTheNetwork()));
}else customUsers.setInstagram(network.getUrlOfTheNetwork());

userService.saveUser(customUsers);

        return "OK";

    }



    @RequestMapping(value = "/get-all-networks" ,method = RequestMethod.POST)
    public List<String> getAllNetworks(){
        CustomUser customUsers =userService.getUserByLogin(getLoginCurrentUser());
        return userService.getAllNetworks(customUsers);

    }


    @RequestMapping(value = "/visit-network" ,method = RequestMethod.POST)
    public String visitNetworks(@RequestBody NetworkData data){

        String url  = userService.getNetworkUrl(data.getOwner() , data.getNameOfTheNetwork());

        return url ;

    }



    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();


        return login;
    }

}
