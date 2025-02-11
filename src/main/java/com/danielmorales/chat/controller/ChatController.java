package com.danielmorales.chat.controller;

import com.danielmorales.chat.entity.ChatMessage;
import com.danielmorales.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * Handle STOMP messages sent to /app/chat.sendMessage.
     * Broadcast result to /topic/public.
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        // write to primary DB
        chatMessageService.saveToPrimary(chatMessage.getSender(), chatMessage.getContent());

        // set timestamp for the broadcasted message
        chatMessage.setTimestamp(Instant.now());

        // return so subscribers at /topic/public get it
        return chatMessage;
    }
}
