package com.example.damnbreadback.dto;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TokenDTO {

    Long id;
    String acessToken;
    String refreshToken;
}
