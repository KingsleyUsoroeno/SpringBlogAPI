package com.kingtech.main.repository;

import com.kingtech.main.data.model.CacheUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CacheUserModel, String> {
    CacheUserModel findByUsername(String username);

    CacheUserModel findByEmail(String email);
}
