package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="scrap")
public class Scrap {

    @Id
    @GeneratedValue
    private Long scrapId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Scrap(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
