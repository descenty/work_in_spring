package com.descenty.work_in_spring.chat.service;

import com.descenty.work_in_spring.chat.constant.MessageStatus;
import com.descenty.work_in_spring.chat.entity.ChatMessage;
import com.descenty.work_in_spring.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(repository::findByChatId).orElse(new ArrayList<>());

        if (messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public Optional<ChatMessage> findById(String id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                });
    }

    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        repository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .forEach(chatMessage -> {
                    chatMessage.setStatus(status);
                    repository.save(chatMessage);
                });
    }
}