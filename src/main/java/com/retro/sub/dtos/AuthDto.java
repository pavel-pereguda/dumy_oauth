package com.retro.sub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto implements Serializable {

    private String client_id;
    private String client_secret;
    private String code;
    private String scope;
}
