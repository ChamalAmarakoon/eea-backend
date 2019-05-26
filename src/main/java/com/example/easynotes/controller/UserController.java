package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.User;
import com.example.easynotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@EnableAutoConfiguration
@Configuration
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@Valid @RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getNoteById(@PathVariable(value = "id") Long userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable(value = "id")Long userID,
                                 @Valid @RequestBody User userDetails){
        User user = userRepository.findById(userID)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userID));

        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setPassword(String.valueOf(userDetails.getPassword()));

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }

}
