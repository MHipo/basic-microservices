package com.minejava.blogapp.repository;

import com.minejava.blogapp.model.jpa.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    void deleteByUsername(String username);
    
}
