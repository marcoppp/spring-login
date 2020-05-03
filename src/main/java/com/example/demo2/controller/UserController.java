package com.example.demo2.controller;

import com.example.demo2.model.*;
import com.example.demo2.repository.*;
import com.example.demo2.exception.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public User add(@RequestBody User user) {
        String sha256hex = DigestUtils.sha256Hex(user.getPassword());
        user.setPassword("1");
        user.setPwdHash(sha256hex);
        return userRepository.save(user);
    }
/* 
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }
 */
    @GetMapping(value = "/{name}")
    public User getOne(@PathVariable String name) {
        return userRepository.findById(name)
            .orElseThrow(() -> new ResourceNotFoundException());
    }

    @PutMapping(value = "/{id}")
    public User update(@PathVariable String id, @RequestBody User updatedUser) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());
            user.setName(updatedUser.getName());
        // candidate.setPassword(updatedUser.getPassword());
        String sha256hex = DigestUtils.sha256Hex(updatedUser.getPassword());
        updatedUser.setPassword(sha256hex);
        return userRepository.save(user);
    }

/*     
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException());
        userRepository.delete(user);
    }
 */
}