package com.springcloud.webclients.jpah2.controller;

import com.springcloud.webclients.jpah2.dto.UserParam;
import com.springcloud.webclients.jpah2.entity.TbUser;
import com.springcloud.webclients.jpah2.repository.TbUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final TbUserRepository repository;

    @PostMapping("/users")
    public ResponseEntity<Long> createUser(@RequestBody UserParam userParam){

        log.info(userParam.toString());
        TbUser user = new TbUser();

        user.setUserId(userParam.getUserId());
        user.setUserName(userParam.getUserName());

        repository.save(user);

        return new ResponseEntity<>(user.getUserCode(), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<TbUser> userList(@PathVariable Long id){

        Optional<TbUser> user = repository.findById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
