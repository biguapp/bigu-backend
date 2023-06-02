package com.api.bigu.dto.user;


import com.api.bigu.models.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class UserRequest{

    public Integer userId;

    public String fullName;

    public String email;

    public String sex;

    public String phoneNumber;

    public UserRequest(User usuario) {
            this.userId = usuario.getUserId();
            this.fullName = usuario.getFullName();
            this.sex = usuario.getSex();
            this.email = usuario.getEmail();
            this.phoneNumber = usuario.getPhoneNumber();
    }
}
