package com.justchat.demo.service;

import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.entity.CustomUser;
import com.justchat.demo.repository.ChatMessageRepository;
import com.justchat.demo.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class UserService {


    @Autowired
    ChatMessageRepository chatMessageRepository;


    @Autowired
    CustomUserRepository customUserRepository;


    @Transactional
     public List<CustomUser> getAllUser(){
        return customUserRepository.findAll();
    }


    @Transactional
    public boolean saveUser(CustomUser customUser){

        if(customUserRepository.existsByLogin(customUser.getLogin())){
            return false;
        }
        customUserRepository.save(customUser);
        return true;
    }



    @Transactional
       public CustomUser getUserByLogin(String login){

           return customUserRepository.findByLogin(login);

       }

    @Transactional
    public CustomUser addUser(CustomUser customUser){

        return customUserRepository.save(customUser);

    }














}
