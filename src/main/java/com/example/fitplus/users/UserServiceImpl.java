package com.example.fitplus.users;

import com.example.fitplus.AppThreadLocals;
import com.example.fitplus.exceptions.FitPlusException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(User user) throws Exception
    {
        validateUserCreation(user);
        setEncodedPassword(user);
        this.userRepository.save(user);
    }

    private void setEncodedPassword(User user)
    {
        String encodedPassword = getPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    private void validateUserCreation(User user) throws Exception
    {
        boolean isUserPresentWithUserName = userRepository.findByUserName(user.getUserName()).isPresent();
        if(isUserPresentWithUserName)
        {
            logger.info("User already present with this user name: {}", user.getUserName());
            throw new FitPlusException("User already present with this User name. Please enter a new one");
        }

        boolean isUserPresentWithEmail = userRepository.findByEmail(user.getEmail()).isPresent();
        if(isUserPresentWithEmail)
        {
            logger.info("This email is already registered.");
            throw new FitPlusException("This email is already registered. Please enter another one");
        }
    }

    @Override
    public Optional<User> findByID(Long id) {
        validateCurrentUser(id);
        return this.userRepository.findById(id);
    }

    private static void validateCurrentUser(Long id)
    {
        if(!id.equals(AppThreadLocals.getCurrentUserId()))
        {
            throw new FitPlusException("Cannot Get Another Users Data");
        }
    }

    @Override
    public void updateUser(Long id, User user) {
        validateCurrentUser(id);
        Optional<User> existingUserOptl = findByID(id);
        if(existingUserOptl.isEmpty())
        {
            logger.info("User not found. UserId: {}", id);
            throw new FitPlusException("User not found");
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

    public PasswordEncoder getPasswordEncoder()
    {
        return passwordEncoder;
    }
}
