package com.crud.app.rest.Controllers;

import com.crud.app.rest.Models.User;
import com.crud.app.rest.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping(value = "/user/create")
    public String saveUser(@RequestBody User user) {
        userRepo.save(user);
        return "User saved to db..";
    }
    @PutMapping(value = "/user/update/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user) {
        User toUpdateUser = userRepo.findById(id).get();
        toUpdateUser.setFirst_name(user.getFirst_name());
        toUpdateUser.setLast_name(user.getLast_name());
        toUpdateUser.setProfession(user.getProfession());
        toUpdateUser.setAge(user.getAge());
        userRepo.save(toUpdateUser);
        return "User updated to db..";
    }
    @DeleteMapping(value = "user/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        User toDeleteUser = userRepo.findById(id).get();
        userRepo.delete(toDeleteUser);
        return "User deleted..";
    }
}
