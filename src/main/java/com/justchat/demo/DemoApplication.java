package com.justchat.demo;

import com.justchat.demo.entity.MessageStatus;
import com.justchat.demo.service.UserService;
import com.justchat.demo.service.MessageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo(final MessageService messageService ,
                                  final UserService userService) {
        return strings -> {

           /* customUserService.addUser(new CustomUser("Petro"));
            customUserService.addUser(new CustomUser("Vasul"));*/
          /*  messageService.sendMessage(userService.getUserByLogin("Ivan"),"all","first", MessageStatus.publicMessage);
            messageService.sendMessage(userService.getUserByLogin("Andriy"),"all","first",MessageStatus.publicMessage);

            messageService.sendMessage(userService.getUserByLogin("Ivan"),"all","second",MessageStatus.publicMessage);
            messageService.sendMessage(userService.getUserByLogin("Andriy"),"all","second",MessageStatus.publicMessage);

            messageService.sendMessage(userService.getUserByLogin("Ivan"),"all","third",MessageStatus.publicMessage);
            messageService.sendMessage(userService.getUserByLogin("Andriy"),"all","Hello,Petro",MessageStatus.publicMessage);

*/
        };
    }
}
