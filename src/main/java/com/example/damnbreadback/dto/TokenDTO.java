package com.example.damnbreadback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenDTO {
    String acessToken;
    String refreshToken;
}
