package com.divyansh.prioriti.service.impl;

import com.divyansh.prioriti.dto.Response;
import com.divyansh.prioriti.dto.UserRequest;
import com.divyansh.prioriti.entity.User;
import com.divyansh.prioriti.enums.Role;
import com.divyansh.prioriti.exceptions.BadRequestException;
import com.divyansh.prioriti.exceptions.NotFoundException;
import com.divyansh.prioriti.repository.UserRepository;
import com.divyansh.prioriti.security.JwtUtils;
import com.divyansh.prioriti.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Response<?> signup(UserRequest userRequest) {
        log.info("Inside signup");
        Optional<User> existingUser = userRepository.findByUsername(userRequest.getUsername());

        if(existingUser.isPresent()) {
            throw new BadRequestException("Username already taken");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setRole(Role.USER);

        // save the user
        userRepository.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User registered successfully")
                .build();

    }

    @Override
    public Response<?> login(UserRequest userRequest) {
        log.info("Inside login");

        User existingUser = userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if(!passwordEncoder.matches(userRequest.getPassword(), existingUser.getPassword())) {
            throw new BadRequestException("Password does not match");
        }

        String token = jwtUtils.generateToken(userRequest.getUsername());

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("login successful")
                .data(token)
                .build();

    }

    @Override
    public User getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
