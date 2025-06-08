package com.example.fitplus.users;

import com.example.fitplus.ApplicationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map> createUser(@RequestBody User user) throws Exception {
        userService.createUser(user);
        return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "User created successfully :)"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) throws Exception {
        Optional<User> user = userService.findByID(id);
        if(user.isPresent()){
            UserResponse userResponse = UserResponse.getUserResponse(user.get());
            return ResponseEntity.ok(userResponse);
        }else {
            return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), "User Not Found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map> updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
       try {
            userService.updateUser(id, user);
            return ResponseEntity.accepted().body(ApplicationUtil.getResponseMap(HttpStatus.ACCEPTED.value(), "User updated successfully :)"));
       } catch (RuntimeException e) {
           return new ResponseEntity<>(ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), "Unable to update the User details"), HttpStatus.BAD_REQUEST);
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> deleteUser(@PathVariable Long id) throws Exception{
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok().body(ApplicationUtil.getResponseMap(HttpStatus.OK.value(), "User deleted successfully :)"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), "Unable to delete User :("));
        }
    }
}
