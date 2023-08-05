package com.api.bigu.user;

public interface UserService {
    boolean isDriver();
    boolean isRider();
    boolean isDriverOrRider();
    boolean isAdmin();
}
