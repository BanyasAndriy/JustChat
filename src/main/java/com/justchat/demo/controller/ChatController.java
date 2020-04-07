package com.justchat.demo.controller;

import com.justchat.demo.dto.MessageSaver;
import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.service.MessageService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.xml.ws.Action;

@Controller
public class ChatController {

@Autowired
UserService userService;
@Autowired
    MessageService messageService;

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public MessageSaver getMessages(@Payload MessageSaver messageSaver) {


        CustomUser currentUser = userService.getUserByLogin(messageSaver.getFrom());
        MessageStatus messageStatus=null;

String status = messageSaver.getMessageStatus().trim().toLowerCase();

if (status.equals("groups")){
    messageStatus=MessageStatus.publicMessage;

}else if (status.equals("contacts")){
            messageStatus=MessageStatus.privateMessage;
        }

        messageService.saveMessage(currentUser,messageSaver.getTo(),messageSaver.getMessage(), messageStatus);

        return messageSaver;
    }


}
