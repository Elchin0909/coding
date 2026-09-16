package com.example.coding.auth;

import com.example.coding.dto.UpdateUserRequest;
import com.example.coding.dto.UserResponse;
import com.example.coding.entity.User;
import com.example.coding.service.AuthService;
import com.example.coding.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    UserController(UserService userService) {
     this.userService=userService;
    }
    @GetMapping
    public List<UserResponse> findAll() {
        return userService.getAll();

    }
    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userService.update(id,request);}

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
                public void delete(@PathVariable Long id){
            userService.delete(id);
        }
    }





