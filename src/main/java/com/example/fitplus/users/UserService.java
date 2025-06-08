package com.example.fitplus.users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(User user) throws Exception;
    Optional<User> findByID(Long id);
    void updateUser(Long id, User user);

    void deleteUser(Long id);
}
