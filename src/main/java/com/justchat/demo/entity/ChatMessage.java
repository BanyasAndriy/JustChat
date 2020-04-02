package com.justchat.demo.entity;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column( columnDefinition="TEXT" , name = "text_message")
    private String message;

    @Column( name = "whom")
    private String to;



    @ManyToOne
    private CustomUser customUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;


    @ManyToOne
    Group group;

    public ChatMessage() {
    }


    public ChatMessage(String message,String to,CustomUser customUser,MessageStatus messageStatus) {
        this.message=message;
        this.to=to;
        this.customUser=customUser;
        this.messageStatus=messageStatus;
        this.date=new Date();

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
