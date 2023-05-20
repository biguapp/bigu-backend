package com.api.bigu.services;

import com.api.bigu.models.Message;
import com.api.bigu.models.User;
import com.api.bigu.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(User sender, User recipient, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());

        Message save = messageRepository.save(message);
    }

    public List<Message> getMessagesByRecipient(User recipient) {
        return messageRepository.findByRecipient(recipient);
    }

    public List<Message> getMessagesBySender(User sender){
        return messageRepository.findBySender(sender);
    }

    public List<Message> getChatHistory(User user1, User user2) {
        return messageRepository.findChatHistory(user1, user2);
    }
}