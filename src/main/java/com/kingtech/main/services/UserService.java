package com.kingtech.main.services;

import com.kingtech.main.data.model.CacheUserModel;
import com.kingtech.main.data.model.CreateUserRequestModel;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    CacheUserModel createUser(CreateUserRequestModel createUserRequestModel);

    CacheUserModel getUserDetailsByEmail(String email);

    CacheUserModel saveUser(CreateUserRequestModel user);
}
