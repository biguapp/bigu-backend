package com.api.bigu.dto.user;


import com.api.bigu.models.User;
import lombok.Getter;
import lombok.Setter;
import java.util.Optional;

@Getter
@Setter
public class UserDTO {

    public Integer userId;

    public String fullName;

    public String email;

    public String sex;

    public String phoneNumber;

    public UserDTO(User usuario) {
            this.userId = usuario.getUserId();
            this.fullName = usuario.getFullName();
            this.sex = usuario.getSex();
            this.email = usuario.getEmail();
            this.phoneNumber = usuario.getPhoneNumber();
    }
}
