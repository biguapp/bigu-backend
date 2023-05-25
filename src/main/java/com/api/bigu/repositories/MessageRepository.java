package com.api.bigu.repositories;

import com.api.bigu.models.Message;
import com.api.bigu.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByRecipient(User recipient);

    List<Message> findBySender(User sender);
}
