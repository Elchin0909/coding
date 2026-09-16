package com.example.coding.service;

import com.example.coding.dto.UserResponse;
import com.example.coding.entity.Role;
import com.example.coding.entity.User;
import com.example.coding.exception.NotFoundException;
import com.example.coding.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

@Test
     void getbyId_userbolsa_qaytarishi_kerak(){
    User  soxtauser=new User();
    soxtauser.setUsername("elchin");
    soxtauser.setRole(Role.USER);
    when(userRepository.findById(1L)).thenReturn(Optional.of(soxtauser));
    UserResponse userResponse=userService.findById(1L);
    assertNotNull(userResponse);
    assertEquals("elchin",userResponse.username());
}
@Test
    void getbyId_useryoqbolsa_xatotashlashikerak(){
   when(userRepository.findById(99L)).thenReturn(Optional.empty());
   assertThrows(NotFoundException.class,()->userService.findById(99L));

}
@Test
    void delete_userborbolsa_ochirishi_kerak(){
   when(userRepository.existsById(1L)).thenReturn(true);
   userService.delete(1L);
  verify( userRepository).deleteById(1L);

    }
    @Test
    void delete_useryoqbolsa_xatotashlashikerak(){
    when(userRepository.existsById(99L)).thenReturn(false);
    assertThrows(NotFoundException.class,()->userService.delete(99L));
    }




}
