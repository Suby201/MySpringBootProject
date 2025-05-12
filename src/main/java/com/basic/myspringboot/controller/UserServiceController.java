package com.basic.myspringboot.controller;

import com.basic.myspringboot.controller.dto.UserDTO;
import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserServiceController {
    private final UserService userService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping
    public ResponseEntity<UserDTO.UserResponse> create(@Valid @RequestBody UserDTO.UserCreateRequest request){
        UserDTO.UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDTO.UserResponse> getUsers(){
        return userService.getAllUsers()
                .stream()
//                .map(user -> new UserDTO.UserResponse(user)) 아래 거랑 같음
                .map(UserDTO.UserResponse::new)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserDTO.UserResponse getUserById(@PathVariable Long id){
        User existUser = userService.getUserById(id);
        return new UserDTO.UserResponse(existUser);
    }

    @GetMapping("/email/{email}/")
    public UserDTO.UserResponse getUserByEmail(@PathVariable String email){
        User existUser = userService.getUserByEmail(email);
        return new UserDTO.UserResponse(existUser);
    }

    @PatchMapping("/{email}")
    public  ResponseEntity<UserDTO.UserResponse> updateUser(@PathVariable String email, @Valid @RequestBody UserDTO.UserUpdateRequest userDetail){
        UserDTO.UserResponse updatedUser = userService.updateUserByEmail(email, userDetail);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
