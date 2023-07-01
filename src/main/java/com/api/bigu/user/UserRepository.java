package com.api.bigu.user;

import com.api.bigu.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByResetPasswordToken(String token);

    //void updateUser(User user);
}
