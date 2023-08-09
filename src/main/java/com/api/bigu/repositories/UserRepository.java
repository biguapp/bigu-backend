package com.api.bigu.repositories;

import com.api.bigu.models.User;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByResetPasswordToken(String token);

    //void updateUser(User user);
}
