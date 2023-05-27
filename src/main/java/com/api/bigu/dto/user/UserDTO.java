package com.api.bigu.dto.user;

import com.api.bigu.models.Address;
import com.api.bigu.models.Car;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Addresses;
import com.api.bigu.models.enums.Role;
import com.api.bigu.models.enums.UserType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class UserDTO {

    public Integer userId;

    public String cpfUser;

    public String fullName;

    public String email;

    public String phoneNumber;

    public Role role;

    public Map<Addresses, Address> address;

    public UserDTO(Optional<User> user) {
        if (user.isPresent()) {
            User usuario = user.get();
            this.userId = usuario.getUserId();
            this.cpfUser = usuario.getCpfUser();
            this.fullName = usuario.getFullName();
            this.email = usuario.getEmail();
            this.phoneNumber = usuario.getPhoneNumber();
            this.role = usuario.getRole();
//            this.userType = usuario.getUserType();
            // TODO CONSERTAR ADRESS AQUI
        }
    }
}
