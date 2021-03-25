package com.kingtech.main.services;


import com.auth0.jwt.JWT;
import com.kingtech.main.data.model.*;
import com.kingtech.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.kingtech.main.constants.SecurityConstants.SECRET;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;
    BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CacheUserModel createUser(CreateUserRequestModel createUserRequestModel) {
        return new CacheUserModel(
                createUserRequestModel.getUserName(),
                createUserRequestModel.getFirstName(),
                passwordEncoder.encode(createUserRequestModel.getPassword()), // encode the users password
                createUserRequestModel.getLastName(),
                createUserRequestModel.getEmail()
        );
    }

    @Override
    public CacheUserModel getUserDetailsByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CacheUserModel saveUser(CreateUserRequestModel createUserRequestModel) {
        CacheUserModel cacheUser = createUser(createUserRequestModel);
        return repository.save(cacheUser);
    }

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        String token = doGenerateToken(loginRequest.getUsername(), TimeUnit.HOURS.toMillis(12));
        return new JwtResponse(token);
    }

    @Override
    public CustomResponseModel<CacheUserModel> signUpUser(CreateUserRequestModel userRequestModel) {
        final CacheUserModel user = saveUser(userRequestModel);
        return new CustomResponseModel<>("User created successfully", user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CacheUserModel cacheUser = repository.findByUsername(username);
        if (cacheUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(cacheUser.getEmail(), cacheUser.getPassword(),
                true, true,
                true, true,
                new ArrayList<>()
        );
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
