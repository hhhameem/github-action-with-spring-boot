package com.crud.app.rest.Controllers;

import com.crud.app.rest.Models.User;
import com.crud.app.rest.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ApiControllers {
    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/")
    public String getPage() {
        return "Hello!! HI";
    }
    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        if (userRepo.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User found for this id");
        }
    }
    @PostMapping(value = "/user/create")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(user.getEmail() + " already exist");
        } else {
            if (user.getEmail() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide email address");
            }
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }

    }
    @PutMapping(value = "/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user) {
        if (userRepo.findById(id).isPresent()) {
            User toUpdateUser = userRepo.findById(id).get();
            toUpdateUser.setEmail(user.getEmail());
            toUpdateUser.setFirst_name(user.getFirst_name());
            toUpdateUser.setLast_name(user.getLast_name());
            toUpdateUser.setProfession(user.getProfession());
            toUpdateUser.setAge(user.getAge());
            userRepo.save(toUpdateUser);
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.findById(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User found for this id");
        }
    }
    @DeleteMapping(value = "user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        if (userRepo.findById(id).isPresent()) {
            User toDeleteUser = userRepo.findById(id).get();
            userRepo.delete(toDeleteUser);
            return ResponseEntity.status(HttpStatus.OK).body("User with ID: " + toDeleteUser.getId() + " deleted");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User found for this id");
        }
    }
}
