package com.minejava.blogapp.model.jpa;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Type;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"blog", "user"})
@EqualsAndHashCode(exclude = {"blog", "user"})
@Entity

public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(name = "post_status")
    @Type(type = "com.example.blog_app.constants.PgEnumType", parameters = {@org.hibernate.annotations.Parameter(name = "enumClassName", value = "com.example.blog_app.model.jpa.PostStatus")})
    // @Enumerated(EnumType.ORDINAL) // Use this otherwise to store it as integer
    PostStatus postStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType. REFRESH)
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private User user;
}
