package com.justchat.demo.controller;

import com.justchat.demo.dto.ChatMessageDto;
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

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {


    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

   /* @RequestMapping(value = "/get-history" ,method = RequestMethod.POST)
    public List<ChatMessage> getHistory(@RequestBody MessageSender messageSender ){

        MessageStatus messageStatus= MessageStatus.privateMessage;
        if (messageSender.getMessageStatus().trim().equals("Groups")){
             messageStatus=MessageStatus.publicMessage;
        }else {
             messageStatus=MessageStatus.privateMessage;
        }



        List<ChatMessage> history=null;

        if (messageStatus.equals(MessageStatus.privateMessage)) {
            if (messageSender.getFrom() == null) {
                history = messageService.getPrivateMessage(getLoginCurrentUser(), messageSender.getLogin());
            } else {
                history = messageService.getPrivateMessage(messageSender.getFrom(), messageSender.getLogin());
            }
        }else {

            history=messageService.getGeneralMessage(messageSender.getLogin());
        }


        return  history;
    }
*/

    @RequestMapping(value = "/get-history" ,method = RequestMethod.POST)
    public List<ChatMessageDto> getHistory(@RequestBody MessageSender messageSender ){

        MessageStatus messageStatus= MessageStatus.privateMessage;
        if (messageSender.getMessageStatus().trim().equals("Groups")){
            messageStatus=MessageStatus.publicMessage;
        }else {
            messageStatus=MessageStatus.privateMessage;
        }



        List<ChatMessage> history=null;
List<ChatMessageDto> result=new ArrayList<>();
        int newMessageCount=0;
        if (messageStatus.equals(MessageStatus.privateMessage)) {
            if (messageSender.getFrom() == null) {
                history = messageService.getPrivateMessage(getLoginCurrentUser(), messageSender.getLogin());
            } else {
                history = messageService.getPrivateMessage(messageSender.getFrom(), messageSender.getLogin());
            }
        }else {
            history=messageService.getGeneralMessage(messageSender.getLogin());
        }

       Integer indexOfStartNewMsg = null;
        for (int i = 0 ; i < history.size();i++){
            if (history.get(i).getMessage().equals("Нове повідомлення")){
                indexOfStartNewMsg=i ;
            }
        }

        if (indexOfStartNewMsg!=null){
            newMessageCount=history.size()-indexOfStartNewMsg;
        }

        for (ChatMessage chatMessage: history
             ) {
            result.add(new ChatMessageDto(chatMessage.getMessage(),newMessageCount==0?0:newMessageCount-1,chatMessage.getFrom(),chatMessage.getTo()));
        }

        return  result;
    }


    @RequestMapping(value = "/clear-notification" ,method = RequestMethod.POST)
    public String  deleteNotification(@RequestBody MessageSender messageSender ) {

        return messageService.deleteNotificationAboutNewMessages(getLoginCurrentUser(),messageSender.getLogin())?"OK" : "ERROR";


    }

        private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();


        return login;
    }


}
