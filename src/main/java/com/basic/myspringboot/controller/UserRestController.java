package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserRepository userRepository;


    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
//        ResponseEntity<User> responseEntity = optionalUser.map(user -> ResponseEntity.ok(user))
//                .orElse(ResponseEntity.notFound().build());
        return optionalUser.map(ResponseEntity::ok)
                .orElse(new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}/")
    public User getUserByEmail(@PathVariable String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User existUser = getExistUser(optionalUser);
        return existUser;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetail){
        User existUser = getExistUser(userRepository.findById(id));
        existUser.setName(userDetail.getName());
        User updatedUser = userRepository.save(existUser);
        return ResponseEntity.ok(updatedUser);
    }

    private User getExistUser(Optional<User> optionalUser) {
        User existUser = optionalUser
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        return existUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        User existUser = getExistUser(userRepository.findById(id));
        userRepository.delete(existUser);
//        return ResponseEntity.ok(existUser);
        return ResponseEntity.ok("User Successfully Deleted");
    }

//
//    }
//    public UserRestController(UserRepository userRepository) {
//        System.out.println("UserController: "+userRepository.getClass().getName());
//        this.userRepository = userRepository;
//    }


}
