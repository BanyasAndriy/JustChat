package com.justchat.demo.controller;

import com.justchat.demo.dto.MessageSaver;
import com.justchat.demo.dto.MessageSender;
import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.service.MessageService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {


    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;
/*

    @RequestMapping(value = "/save-message" ,method = RequestMethod.POST)
    public String  saveMessage(@RequestBody MessageSaver messageSaver){


        CustomUser currentUser =userService.getUserByLogin(getLoginCurrentUser());

messageService.saveMessage(currentUser,messageSaver.getTo(),messageSaver.getMessage(), MessageStatus.privateMessage);

        return "OK";

    }*/

    @RequestMapping(value = "/get-history" ,method = RequestMethod.POST)
    public List<ChatMessage> getHistory(@RequestBody MessageSender messageSender ){


        List<ChatMessage> history=null;
       if(messageSender.getFrom()==null){
           history = messageService.getPrivateMessage(getLoginCurrentUser(),messageSender.getLogin());
       }else {
           history = messageService.getPrivateMessage(messageSender.getFrom(),messageSender.getLogin());
       }



        return  history;
    }


    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();


        return login;
    }


}
