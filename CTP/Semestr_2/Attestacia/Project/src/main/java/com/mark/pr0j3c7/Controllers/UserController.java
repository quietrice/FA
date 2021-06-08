package com.mark.pr0j3c7.Controllers;

import com.mark.pr0j3c7.Entities.User;
import com.mark.pr0j3c7.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService USER_SERVICE;

    @Autowired
    public UserController(UserService USER_SERVICE) {
        this.USER_SERVICE = USER_SERVICE;
    }

    @PostMapping(value="/api/user")
    private ResponseEntity<?> create(@RequestBody User user){
        User newUser = USER_SERVICE.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping(value = "/api/user")
    private ResponseEntity<List<User>> readAll(){
        final List<User> USERS_LIST = USER_SERVICE.findAll();

        return USERS_LIST != null && !USERS_LIST.isEmpty()
                ? new ResponseEntity<>(USERS_LIST, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "api/user/{id}")
    public ResponseEntity<User> getOne(@PathVariable(name = "id") User user) {
        final User CURRENT_USER = user;

        return user != null
                ? new ResponseEntity<>(CURRENT_USER, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "api/user/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") User userFromDB,
                                 @RequestBody User user) {
        User updatedUser = USER_SERVICE.update(user, userFromDB);

        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "api/user/{id}")
    public ResponseEntity<List<User>> delete(@PathVariable(name = "id") User user) {
        if (USER_SERVICE.delete(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
