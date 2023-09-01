package com.descenty.work_in_spring.chat.repository;

import com.descenty.work_in_spring.chat.constant.MessageStatus;
import com.descenty.work_in_spring.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, String> {

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);
    List<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientId);
}