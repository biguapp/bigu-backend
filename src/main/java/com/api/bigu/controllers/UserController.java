package com.api.bigu.controllers;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.feedback.FeedbackRequest;
import com.api.bigu.dto.user.EditUserRequest;
import com.api.bigu.dto.user.UserResponse;
import com.api.bigu.exceptions.FeedbackNotFoundException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.Feedback;
import com.api.bigu.models.User;
import com.api.bigu.services.FeedbackService;
import com.api.bigu.services.UserService;
import com.api.bigu.util.errors.UserError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User admin = userService.findUserById(userId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), admin)) {
                return ResponseEntity.ok(userService.getAllUsers());
            } else {
                return UserError.userNotAnAdministrator();
            }
        } catch (UserNotFoundException uNFE) {
            return UserError.userNotFoundError();
        }
    }

    @GetMapping("/self")
    public ResponseEntity<?> getSelf(@RequestHeader("Authorization") String authorizationHeader) {
        UserResponse userResp = new UserResponse();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            System.err.println(userId);
            User user = userService.findUserById(userId);
            System.err.println(user);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), user)) {
                userResp = userService.toResponse(user);
            }
            System.err.println(userResp);
            return ResponseEntity.ok(userResp);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteSelf(@RequestHeader("Authorization") String authorizationHeader) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        userService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mail/{userEmail}")
    public ResponseEntity<?> searchByEmail(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String userEmail) {
        UserResponse user = new UserResponse();
        try {
            Integer adminId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User admin = userService.findUserById(adminId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), admin)){
                user = userService.toResponse(userService.findUserByEmail(userEmail));
            }
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e){
            return UserError.userNotFoundError();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> searchById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Integer userId) {
        UserResponse user = new UserResponse();
        Integer adminId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        User admin = userService.findUserById(adminId);
        if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), admin)) {
            user = userService.toResponse(userService.findUserById(userId));
        }
        return ResponseEntity.ok(user);

    }

    @PutMapping("/edit")
    public ResponseEntity<?> editProfile(@RequestHeader("Authorization") String authorizationHeader, @RequestBody EditUserRequest editUserRequest){
        UserResponse userResponse = new UserResponse();
        try {
            Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
            User user = userService.findUserById(userId);
            if (jwtService.isTokenValid(jwtService.parse(authorizationHeader), user)){
                userResponse = userService.editProfile(userId, editUserRequest);
            }
            return ResponseEntity.ok(userResponse);
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }

    @PutMapping("/{userId}/profile-image")
    public ResponseEntity<String> uploadProfileImage(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("profileImage") MultipartFile profileImage) {
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            User user = userService.findUserById(userId);
            userService.saveUserProfileImage(user, profileImage);
            return ResponseEntity.ok("Profile image uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading profile image.");
        }
    }

    @GetMapping("/{userId}/profile-image")
    public ResponseEntity<?> getProfileImage(
            @RequestHeader("Authorization") String authorizationHeader){
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try{
            User user = userService.findUserById(userId);
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(user.getProfileImageType()))
                    .body(user.getProfileImage());
        } catch (UserNotFoundException e){
            return UserError.userNotFoundError();
        }
    }

    @DeleteMapping("/{userId}/profile-image")
    public ResponseEntity<?> deleteProfileImage(
            @RequestHeader("Authorization") String authorizationHeader){
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            User user = userService.findUserById(userId);
            userService.deleteUserProfileImage(user);
            return ResponseEntity.ok("Imagem removida com sucesso.");
        } catch (UserNotFoundException e){
            return UserError.userNotFoundError();
        }
    }

    @GetMapping("/{userId}/avg-score")
    public ResponseEntity<?> getAvgScore(@RequestHeader("Authorization") String authorizationHeader){
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try {
            return ResponseEntity.ok(userService.avgFeedbacksReceived(userId));
        } catch (UserNotFoundException e){
            return UserError.userNotFoundError();
        }
    }

    @PostMapping("/{userId}/create-feedback")
    public ResponseEntity<?> createFeedback(@RequestHeader("Authorization") String authorizationHeader, FeedbackRequest feedbackRequest){
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try{
            return ResponseEntity.ok(feedbackService.createFeedback(feedbackRequest));
        } catch (UserNotFoundException e){
            return UserError.userNotFoundError();
        }
    }

    @DeleteMapping("/{userId}/delete-feedback")
    public ResponseEntity<?> deleteFeedback(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Integer feedbackId){
        Integer userId = jwtService.extractUserId(jwtService.parse(authorizationHeader));
        try{
            feedbackService.deleteFeedback(feedbackId, userId);
            return ResponseEntity.ok("Feedback removido.");
        }
        catch (UserNotFoundException uNFE){
            return UserError.userNotFoundError();
        }
        catch (FeedbackNotFoundException fNFE){
            return ResponseEntity.badRequest().build();
        }
    }

    //TODO implementar endpoints de getFeedbacksReceived e getFeedbacksDone


}
