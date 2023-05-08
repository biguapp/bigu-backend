package com.api.bigu.dto.user;

import com.api.bigu.models.Address;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Role;
import com.api.bigu.models.enums.UserType;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class UserDTO {

    public Integer userId;

    public String cpfUser;

    public String fullName;

    public String email;

    public String phoneNumber;

    public Role role;

    public UserType userType;

    public List<Address> address;

    public UserDTO(@NonNull User user) {
        this.userId = user.getUserId();
        this.cpfUser = user.getCpfUser();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.userType = user.getUserType();
        this.address = user.getAddress();
    }

    public UserDTO(Optional<User> user) {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCpfUser() {
        return cpfUser;
    }

    public void setCpfUser(String cpfUser) {
        this.cpfUser = cpfUser;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }


}
