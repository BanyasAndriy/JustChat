package com.justchat.demo.controller;


import com.justchat.demo.entity.ChatMessage;
import com.justchat.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
@RestController
public class MainController {

@Autowired
    MessageService messageService;



    @RequestMapping("/sign")
    public ModelAndView sign(){
        return new ModelAndView("sign");
    }

    @RequestMapping("/cancel")
    public ModelAndView cancel(){
        return new ModelAndView("chat");
    }






    @RequestMapping("/")
     public ModelAndView showChat(){
         return new ModelAndView("chat");
     }

@RequestMapping(value = "/get-сhat" ,method = RequestMethod.POST)
public List<ChatMessage> showChat(@RequestParam("firstUser") String firstUsers ,
                                  @RequestParam ("secondUser") String secondUser){

        List<ChatMessage> history = messageService.getPrivateMessage(firstUsers,secondUser);


        return  history;
}








}
