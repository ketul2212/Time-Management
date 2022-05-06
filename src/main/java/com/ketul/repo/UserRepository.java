package com.ketul.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.ketul.module.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPass(String email, String pass);

    User findByEmailAndIsEnabledIsTrue(String email);

    User findByEmail(String email);

    @Modifying
    @Query(value = "delete from user u where u.email=?", nativeQuery = true)
    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}