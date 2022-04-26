package com.minejava.blogapp.model.jpa;


public enum PostStatus {
    ACTIVE(1), NOT_ACTIVE(2);
    int status;
    PostStatus(int status){
        this.status = status;
    }
    
}
