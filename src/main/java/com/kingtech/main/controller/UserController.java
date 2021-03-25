package com.kingtech.main.controller;

import com.kingtech.main.constants.SecurityConstants;
import com.kingtech.main.data.model.*;
import com.kingtech.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping({"/users/"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(SecurityConstants.SIGN_UP_URL)
    public ResponseEntity<CustomResponseModel<CacheUserModel>> createUser(@Valid @RequestBody CreateUserRequestModel model) {
        final CustomResponseModel<CacheUserModel> userModel = userService.signUpUser(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.loginUser(loginRequest).getToken();
            return ResponseEntity.ok(new JwtResponse(token));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseModel<>(
                    e.getMessage(),
                    null
            ));
        }
    }
}
