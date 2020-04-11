package com.justchat.demo.controller;

import com.justchat.demo.dto.CustomUserDto;
import com.justchat.demo.dto.GroupDataDto;
import com.justchat.demo.dto.MessageSender;
import com.justchat.demo.dto.NetworkData;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.service.MessageService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
public class UserController {


    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/get-all-users", method = RequestMethod.POST)
    public Set<CustomUserDto> getAllUsers() {

        CustomUser currentUser = userService.getUserByLogin(getLoginCurrentUser());


        return userService.getSavedUsers(currentUser);
    }


    @RequestMapping(value = "/search-users", method = RequestMethod.POST)
    public Set<CustomUser> searchUsers(@RequestBody MessageSender user) {


        return userService.searchUser(user.getLogin());

    }

    @RequestMapping(value = "/search-unsigned-users", method = RequestMethod.POST)
    public Set<CustomUserDto> searchUnsignedUsers(@RequestBody GroupDataDto data) {


        Set<CustomUserDto> users = new LinkedHashSet<>();
        users = userService.searchUsersToAddingToGroup(data.getUsers(), data.getGroupName());

        return users;

    }


    @RequestMapping(value = "/get-current-user", method = RequestMethod.POST)
    public CustomUser getCurrentUser() {
        return userService.getUserByLogin(getLoginCurrentUser());

    }

    @RequestMapping(value = "/save-network", method = RequestMethod.POST)
    public String saveNetwork(@RequestBody NetworkData network) {
        CustomUser customUsers = userService.getUserByLogin(getLoginCurrentUser());

        if (network.getNameOfTheNetwork().toLowerCase().trim().equals("facebook")) {
            customUsers.setFacebook(network.getUrlOfTheNetwork());
        } else if (network.getNameOfTheNetwork().toLowerCase().trim().equals("twitter")) {
            customUsers.setTwitter((network.getUrlOfTheNetwork()));
        } else customUsers.setInstagram(network.getUrlOfTheNetwork());

        userService.saveUser(customUsers);

        return "OK";

    }


    @RequestMapping(value = "/get-all-networks", method = RequestMethod.POST)
    public List<String> getAllNetworks() {
        CustomUser customUsers = userService.getUserByLogin(getLoginCurrentUser());
        return userService.getAllNetworks(customUsers);

    }


    @RequestMapping(value = "/visit-network", method = RequestMethod.POST)
    public String visitNetworks(@RequestBody NetworkData data) {

        return userService.getNetworkUrl(data.getOwner(), data.getNameOfTheNetwork());

    }


    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        return loggedInUser.getName();
    }

}
