package com.danielmorales.chat.repository.primary;

import com.danielmorales.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
