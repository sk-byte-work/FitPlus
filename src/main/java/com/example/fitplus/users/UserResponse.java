package com.example.fitplus.users;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private final Long id;
    private final String userName;
    private final String email;

    public UserResponse(Long id, String userName, String email){
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public static UserResponse getUserResponse(User user){
        return new UserResponse(user.getId(), user.getUserName(), user.getEmail());
    }

    public static List<UserResponse> getUserResponses(List<User> users){
        List<UserResponse> userResponses = new ArrayList<>();

        for(User user : users){
            UserResponse userResponse = getUserResponse(user);
            userResponses.add(userResponse);
        }

        return userResponses;
    }
}
