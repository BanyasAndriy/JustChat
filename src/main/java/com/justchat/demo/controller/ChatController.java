package com.justchat.demo.controller;

import com.justchat.demo.dto.MessageSaver;
import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.repository.CustomUserRepository;
import com.justchat.demo.service.MessageService;
import com.justchat.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.xml.ws.Action;

@Controller
public class ChatController {

    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserRepository userRepository;


    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public MessageSaver getMessages(@Payload MessageSaver messageSaver) {


        CustomUser currentUser = userService.getUserByLogin(messageSaver.getFrom());
        CustomUser to = userService.getUserByLogin(messageSaver.getTo());


        if (messageSaver.getFrom().equals("LeftGroupBot") && currentUser == null) {
            currentUser = userRepository.save(new CustomUser("LeftGroupBot"));
        } else if (messageSaver.getFrom().equals("AddedGroupBot") && currentUser == null) {

            currentUser = userRepository.save(new CustomUser("AddedGroupBot"));
        } else if (messageSaver.getFrom().equals("DeletedGroupBot") && currentUser == null) {

            currentUser = userRepository.save(new CustomUser("DeletedGroupBot"));
        }


        userService.saveUser(to);


        MessageStatus messageStatus = messageSaver.getMessageStatus().trim().toLowerCase().equals("groups") ? MessageStatus.publicMessage : MessageStatus.privateMessage;


        messageService.saveMessage(currentUser, messageSaver.getTo(), messageSaver.getMessage(), messageStatus);
        return messageSaver;
    }




    /*

    @MessageMapping("/leave_group")
    @SendTo("/chat/leave")
    public String leaveGroup() {


         return "";
    }
*/

    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();

        return login;
    }

}

