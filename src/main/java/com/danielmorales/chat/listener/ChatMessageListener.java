package com.danielmorales.chat.listener;

import com.danielmorales.chat.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageListener {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @KafkaListener(
            topics = "chat_messages",
            groupId = "chat-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(ChatMessage message) {
        // example, save the message to the primary database
        chatMessageRepository.save(message);

        // can optionally log
        System.out.println("Received message from Kafka: " + message.getContent());
    }
}
