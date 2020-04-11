package com.justchat.demo.service;


import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.Group;
import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.repository.ChatMessageRepository;
import com.justchat.demo.repository.CustomUserRepository;
import com.justchat.demo.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {


    @Autowired
    ChatMessageRepository chatMessageRepository;


    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    GroupService groupService;
    @Autowired
    GroupRepository groupRepository;




  /*  @Transactional
    public void saveMessage(CustomUser currentUser, String to, String message, MessageStatus messageStatus) {

CustomUser newMessageBot=null;
        Group group = null;
        ChatMessage msg = null;
        if (messageStatus.equals(MessageStatus.publicMessage)) {
            group =groupService.findGroupByName(to);
            msg = new ChatMessage(message, to, currentUser, messageStatus, group);
            if (group!=null)
            group.addToChats(msg);
        }else {

            msg = new ChatMessage(message, to, currentUser, messageStatus);


        }
            chatMessageRepository.save(msg);

      if (group!=null)
        groupRepository.save(group);


    }
*/

    @Transactional
    public void saveMessage(CustomUser currentUser, String to, String message, MessageStatus messageStatus) {


        Group group = null;
        ChatMessage msg = null;
        ChatMessage newMessageNotification = null;
        if (messageStatus.equals(MessageStatus.publicMessage)) {
            group = groupService.findGroupByName(to);
            msg = new ChatMessage(message, to, currentUser, messageStatus, group);
            if (group != null)
                group.addToChats(msg);
        } else {


            if ( chatMessageRepository.findByCustomUser(currentUser).contains(new ChatMessage("Нове повідомлення", to, currentUser, messageStatus)))
            {
                msg = new ChatMessage(message, to, currentUser, messageStatus);
            } else {

              //  newMessageBot = new CustomUser("newMessageBot");
                newMessageNotification = new ChatMessage("Нове повідомлення", to, currentUser, messageStatus);
                msg = new ChatMessage(message, to, currentUser, messageStatus);

             //   customUserRepository.save(newMessageBot);
            }


        }
        if (newMessageNotification != null) {
            chatMessageRepository.save(newMessageNotification);
        }
        chatMessageRepository.save(msg);


        if (group != null)
            groupRepository.save(group);


    }

    @Transactional
    public List<ChatMessage> getPrivateMessage(String loginFirstUser, String loginSecondUser) {

        List<ChatMessage> chatMessages = new ArrayList<>();

        CustomUser firstUser = customUserRepository.findByLogin(loginFirstUser);
        CustomUser secondUser = customUserRepository.findByLogin(loginSecondUser);

        chatMessages.addAll(chatMessageRepository.findByCustomUserAndTo(firstUser, loginSecondUser));
        chatMessages.addAll(chatMessageRepository.findByCustomUserAndTo(secondUser, loginFirstUser));

        chatMessages.sort((o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });

        return chatMessages;
    }


    @Transactional
    public boolean deleteNotificationAboutNewMessages(String loginFirstUser, String loginSecondUser) {



List<ChatMessage> fromMess= chatMessageRepository.deleteAllByFromAndToAndMessage(loginSecondUser,loginFirstUser,"Нове повідомлення");
        List<ChatMessage> toMess= chatMessageRepository.deleteAllByFromAndToAndMessage(loginFirstUser,loginSecondUser,"Нове повідомлення");




        return false;
    }



    @Transactional
    public List<ChatMessage> getGeneralMessage(String nameOfGroup) {

        Group group = groupService.findGroupByName(nameOfGroup);


        List<ChatMessage> generalMessage=group.getChatMessage();

        Collections.sort(generalMessage, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });


   return generalMessage;


    }







}
