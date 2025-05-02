package com.example.fitplus.users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findByID(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public void updateUser(Long id, User user) {
        Optional<User> existingUserOptl = findByID(id);
        if(existingUserOptl.isEmpty())
        {
            throw new RuntimeException("User not found");
        }

        User existingUser = existingUserOptl.get();
        if(user.getUserName() != null) {
            existingUser.setUserName(user.getUserName());
        }

        if(user.getPassword() != null){
            existingUser.setPassword(user.getPassword());
        }

        if(user.getEmail() != null){
            existingUser.setEmail(user.getEmail());
        }

        this.userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
