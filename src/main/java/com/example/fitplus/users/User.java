package com.example.fitplus.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "UserID")
    private Long id;

    @Column(nullable = false)
    @NotEmpty @NotNull
    private String userName;

    @Column(nullable = false)
    @NotEmpty @NotNull
    private String password;

    @Column(nullable = false)
    @NotEmpty @NotNull @Email
    private String email;

    protected User()
    {

    }

    public User(String userName, String password, String email)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }
}
