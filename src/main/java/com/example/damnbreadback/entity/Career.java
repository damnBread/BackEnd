package com.example.damnbreadback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Long userId;

    private String place;
    private int period;
}
