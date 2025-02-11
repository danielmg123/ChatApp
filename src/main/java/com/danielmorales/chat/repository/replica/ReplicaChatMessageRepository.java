package com.danielmorales.chat.repository.replica;

import com.danielmorales.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplicaChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // For reading from the replica
}