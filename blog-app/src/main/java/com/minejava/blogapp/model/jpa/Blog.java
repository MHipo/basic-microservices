package com.minejava.blogapp.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
@Data // generates getters, setters, equals, toString & hashCode we well
@Builder // generater builder for the fields within
@NoArgsConstructor // generates a no argument constructor
@AllArgsConstructor // generates a constructor with all arguments
@ToString(exclude = {"posts"}) // generates toString method, skipping passed field as name
@EqualsAndHashCode(exclude = {"posts"}) 
@Entity
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    'guid' is useful to hide incremental 'id' from all external
    communications for
    security reasons.
    'id' would be faster to index and query while doing pagination,
    filtering etc.
    */
    @org.hibernate.annotations.Type(type = "pg-uuid")
    UUID guid;
    private String name;
    private String about;
    private LocalDateTime publishedAt;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
    orphanRemoval = true, mappedBy = "blog")
    private Set<Post> posts;
    }