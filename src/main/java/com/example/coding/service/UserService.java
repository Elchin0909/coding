package com.example.coding.service;

import com.example.coding.dto.UpdateUserRequest;
import com.example.coding.dto.UserResponse;
import com.example.coding.entity.User;
import com.example.coding.repo.UserRepository;
import org.springframework.stereotype.Service;
import com.example.coding.exception.NotFoundException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public  UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(u->new UserResponse(u.getId(),u.getUsername(),u.getRole().name()))
                .toList();

    }
    public UserResponse findById(Long id){
        User user= userRepository.findById(id).orElseThrow(()->new NotFoundException("User topilmadi "+id));
        return new UserResponse(user.getId(),user.getUsername(),user.getRole().name());
    }
    public UserResponse update(Long id, UpdateUserRequest  request){
        User user= userRepository.findById(id)
                .orElseThrow(()->new NotFoundException("User topilmadi "+id));
        user.setUsername(request.username());
        userRepository.save(user);
        return  new UserResponse(user.getId(),user.getUsername(),user.getRole().name());
    }
    public void delete(Long id){
        if(!userRepository.existsById(id)){
            throw new NotFoundException("User topilmadi "+id);
        }
        userRepository.deleteById(id);
    }




}
