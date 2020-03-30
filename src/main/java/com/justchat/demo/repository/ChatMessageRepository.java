package com.justchat.demo.repository;

import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {


    List<ChatMessage> findByCustomUserAndTo(CustomUser customUser , String to);


}
