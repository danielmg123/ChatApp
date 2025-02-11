package com.danielmorales.chat.controller;

import com.danielmorales.chat.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage){
        chatMessage.setTimestamp(Instant.now());
       // will save to DB or publish to Kafka
        return chatMessage;
    }
}
