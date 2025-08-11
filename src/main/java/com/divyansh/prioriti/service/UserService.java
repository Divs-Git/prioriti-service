package com.divyansh.prioriti.service;

import com.divyansh.prioriti.dto.Response;
import com.divyansh.prioriti.dto.UserRequest;
import com.divyansh.prioriti.entity.User;

public interface UserService {

    Response<?> signup(UserRequest userRequest);
    Response<?> login(UserRequest userRequest);
    User getCurrentLoggedInUser();
}
