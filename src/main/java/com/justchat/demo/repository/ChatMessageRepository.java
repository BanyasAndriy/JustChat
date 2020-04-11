package com.justchat.demo.repository;

import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {


    List<ChatMessage> findByCustomUserAndTo(CustomUser customUser , String to);
    List<ChatMessage> findByCustomUser(CustomUser customUser);


    List<ChatMessage> findByMessageStatus(MessageStatus privateMessage);
    List<ChatMessage> deleteAllByFromAndToAndMessage(String from , String to ,String message);

    List<ChatMessage> findByFromAndToAndMessage(String loginFirstUser, String loginSecondUser, String нове_повідомлення);
}
