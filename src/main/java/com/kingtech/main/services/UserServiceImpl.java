package com.kingtech.main.services;


import com.kingtech.main.data.model.CacheUserModel;
import com.kingtech.main.data.model.CreateUserRequestModel;
import com.kingtech.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    UserRepository repository;
    BCryptPasswordEncoder passwordEncoder;

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
}
