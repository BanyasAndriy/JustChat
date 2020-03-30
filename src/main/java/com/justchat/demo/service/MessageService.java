package com.justchat.demo.service;


import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.repository.ChatMessageRepository;
import com.justchat.demo.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {


    @Autowired
    ChatMessageRepository chatMessageRepository;


    @Autowired
    CustomUserRepository customUserRepository;

    @Transactional
    public void saveMessage(CustomUser currentUser, String to, String message, MessageStatus messageStatus) {

        chatMessageRepository.save(new ChatMessage(message, to, currentUser,messageStatus));

    }


    @Transactional
    public List<ChatMessage> getPrivateMessage(String loginFirstUser, String loginSecondUser) {

        List<ChatMessage> chatMessages = new ArrayList<>();

        CustomUser firstUser = customUserRepository.findByLogin(loginFirstUser);
        CustomUser secondUser = customUserRepository.findByLogin(loginSecondUser);

        chatMessages.addAll(chatMessageRepository.findByCustomUserAndTo(firstUser, loginSecondUser));
        chatMessages.addAll(chatMessageRepository.findByCustomUserAndTo(secondUser, loginFirstUser));

        Collections.sort(chatMessages, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });

        return chatMessages;
    }

    @Transactional
    public List<ChatMessage> getGeneralMessage() {

        List<ChatMessage> generalMessage = new ArrayList<>();

       List<ChatMessage> allMessage =  chatMessageRepository.findAll();


        for (ChatMessage msg: allMessage) {

            if (msg.getMessageStatus().equals(MessageStatus.publicMessage)){
                generalMessage.add(msg);
            }

        }


        Collections.sort(generalMessage, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });

       
   return generalMessage;


    }







}
