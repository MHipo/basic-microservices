package com.minejava.blogapp.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import com.minejava.blogapp.model.jpa.Blog;
import com.minejava.blogapp.model.jpa.Post;
import com.minejava.blogapp.model.jpa.PostStatus;
import com.minejava.blogapp.model.jpa.User;
import com.minejava.blogapp.repository.BlogRepository;
import com.minejava.blogapp.repository.PostRepository;
import com.minejava.blogapp.repository.UserRepository;
@Service
@Slf4j
public class DataGenerator {
    private AtomicBoolean initialized = new AtomicBoolean(false);
    List<String> blogNames = Arrays.asList("PartyBlog", "ScienceBlog");
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Transactional
    public void generateSampleData()  {
        this.initialized.compareAndSet(false, true);
        log.info("data being generated");
        List<Blog> blogs = new ArrayList<>();
        User user1 = new User( "Akira", "password", "none@none.com");
        User user2 = new User( "Lisa", "password", "none@none.com");
        User user3 = new User( "James", "password", "none@none.com");
        userRepository.save(user1);
        userRepository.refresh(user1);
        userRepository.save(user2);
        userRepository.refresh(user2);
        userRepository.save(user3);
        userRepository.refresh(user3);
        blogNames.forEach(name->{

        Blog blog = Blog.builder()
                    .name(name)
                    .guid(UUID.randomUUID())
                    .about("Sample blog")
                    .publishedAt(LocalDateTime.now()).build();
        blogRepository.save(blog);
        blogRepository.refresh(blog); //populate generated id
        blogs.add(blog);
        Post post1 = Post.builder().title("Lorem Ispum1").content("Sample content1").user(user1).blog(blog).postStatus(PostStatus.ACTIVE).build();
        Post post2 = Post.builder().title("Lorem Ispum2").content("Sample content2");
    }
}
}
