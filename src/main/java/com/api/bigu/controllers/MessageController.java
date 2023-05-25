package com.api.bigu.controllers;

import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Message;
import com.api.bigu.models.User;
import com.api.bigu.services.MessageService;
import com.api.bigu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> sendMessage(
            @RequestParam Integer senderId,
            @RequestParam Integer recipientId,
            @RequestParam String content
    ) {

        User sender;
        User recipient;

        try {
            sender = userService.findUserById(senderId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender not found.", e);
        }
        try {
            recipient = userService.findUserById(recipientId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient not found.", e);
        }

        // Enviar a mensagem
        messageService.sendMessage(sender, recipient, content);

        return ResponseEntity.ok("Mensagem enviada com sucesso.");
    }

    @GetMapping("/received/{recipientId}")
    public ResponseEntity<List<Message>> getReceivedMessages(@PathVariable Integer recipientId) {

        User recipient;

        try {
            recipient = userService.findUserById(recipientId);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient not found.", e);
        }

        List<Message> receivedMessages = messageService.getMessagesByRecipient(recipient);

        return ResponseEntity.ok(receivedMessages);
    }

    @GetMapping("/history/{user1Id}/{user2Id}")
    public ResponseEntity<?> getChatHistory(
            @PathVariable Integer user1Id,
            @PathVariable Integer user2Id
    ) {

        User user1;
        try {
            user1 = userService.findUserById(user1Id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + user1Id + " not found.", e);
        }
        User user2;
        try {
            user2 = userService.findUserById(user2Id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + user2Id + " not found.", e);
        }

        Map<LocalDateTime, Message> chatHistory = messageService.getChatHistory(user1, user2);

        return ResponseEntity.ok(chatHistory);
    }
}