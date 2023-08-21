package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String accessToken;

    @Column(unique = true)
    public String refreshToken;


}