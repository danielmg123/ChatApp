package com.danielmorales.chat.controller;

import com.danielmorales.chat.entity.ChatMessage;
import com.danielmorales.chat.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private ChatMessageService chatMessageService;

    /**
     * Fetch recent messages from the replica DB.
     * Example: GET /api/chat/history?limit=10
     */
    @GetMapping("/history")
    public List<ChatMessage> getRecent(@RequestParam(defaultValue = "10") int limit) {
        return chatMessageService.readFromReplica(limit);
    }

    /**
     * Write a new message to the primary DB via HTTP.
     * Example: POST /api/chat/send?sender=Alice&content=Hello
     */
    @PostMapping("/send")
    public ChatMessage send(@RequestParam String sender, @RequestParam String content) {
        return chatMessageService.saveToPrimary(sender, content);
    }
}
