package com.example.damnbreadback.entity;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private String id;
    private String name;
    private String email;

    // firebase timestamp type
    private Date birth;

}