package com.kingtech.main.controller;

import com.auth0.jwt.JWT;
import com.kingtech.main.constants.SecurityConstants;
import com.kingtech.main.data.model.*;
import com.kingtech.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kingtech.main.constants.SecurityConstants.SECRET;

@RestController
@RequestMapping({"/users/"})
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(SecurityConstants.SIGN_UP_URL)
    public ResponseEntity<CustomResponseModel<CacheUserModel>> createUser(@Valid @RequestBody CreateUserRequestModel model) {
        final CacheUserModel user = userService.saveUser(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CustomResponseModel<>("User created successfully", user)
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            String token = doGenerateToken(loginRequest.getUsername(), TimeUnit.HOURS.toMillis(12));

            return ResponseEntity.ok(new JwtResponse(token));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponseModel<>(
                    e.getMessage(),
                    null
            ));
        }
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User Disabled", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials", e);
        }
    }

    private String doGenerateToken(String username, long expirationTime) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(HMAC512(SECRET.getBytes()));
    }
}
