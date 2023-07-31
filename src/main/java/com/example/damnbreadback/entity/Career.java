package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "career")
public class Career {
    @Id
    @GeneratedValue
    private Long careerId;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    private String place;
    private int period;
}
