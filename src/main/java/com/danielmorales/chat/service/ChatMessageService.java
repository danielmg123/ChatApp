package com.danielmorales.chat.service;

import com.danielmorales.chat.entity.ChatMessage;
import com.danielmorales.chat.repository.primary.PrimaryChatMessageRepository;
import com.danielmorales.chat.repository.replica.ReplicaChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * This service enforces that writes go to the primary
 * and reads come from the replica.
 */
@Service
public class ChatMessageService {

    @Autowired
    private PrimaryChatMessageRepository primaryRepo;

    @Autowired
    private ReplicaChatMessageRepository replicaRepo;

    /**
     * Writes a new message to the primary DB.
     */
    public ChatMessage saveToPrimary(String sender, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setContent(content);
        msg.setTimestamp(Instant.now());
        // Persist to the primary database
        return primaryRepo.save(msg);
    }

    /**
     * Reads messages from the replica DB.
     */
    public List<ChatMessage> readFromReplica(int limit) {
        // If I want them in descending order, I can adjust the repository or the sort param
        return replicaRepo.findAll(PageRequest.of(0, limit)).getContent();
    }
}
