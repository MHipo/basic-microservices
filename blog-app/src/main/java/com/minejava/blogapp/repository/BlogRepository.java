package com.minejava.blogapp.repository;

import com.minejava.blogapp.model.jpa.Blog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long>{
    
}
