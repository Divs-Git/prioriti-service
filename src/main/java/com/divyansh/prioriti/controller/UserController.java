package com.divyansh.prioriti.controller;

import com.divyansh.prioriti.dto.Response;
import com.divyansh.prioriti.dto.UserRequest;
import com.divyansh.prioriti.entity.User;
import com.divyansh.prioriti.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<?>> register(@Valid @RequestBody UserRequest userRequest) {
        log.info(userRequest.toString());
        return ResponseEntity.ok(userService.signup(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.login(userRequest));
    }
}
