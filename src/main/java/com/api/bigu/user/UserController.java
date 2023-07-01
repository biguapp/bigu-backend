package com.api.bigu.user;

import com.api.bigu.config.JwtService;
import com.api.bigu.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

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
        System.err.println("ENTROU AQUI.");
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
}
