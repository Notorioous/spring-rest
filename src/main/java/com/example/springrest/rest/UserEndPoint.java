package com.example.springrest.rest;

import com.example.springrest.model.User;
import com.example.springrest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserEndPoint {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/add")
    public ResponseEntity add(@RequestBody User user){

        if(userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/all")
    public ResponseEntity getAll(){
       return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getById(@PathVariable("id") int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
          return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PutMapping("/user/update")
    public ResponseEntity update(@RequestBody User user){
       if(userRepository.findById(user.getId()).isPresent()){
           userRepository.save(user);
           return ResponseEntity
                   .ok()
                   .build();
       }
       return ResponseEntity
               .notFound()
               .build();
    }

}
