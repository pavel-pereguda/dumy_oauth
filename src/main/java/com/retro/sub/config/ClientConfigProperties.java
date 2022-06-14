package com.retro.sub.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientConfigProperties {

    private String endpoint;

    private String username;

    private String password;

    private Integer connectionTimeout;

    private Integer readWriteTimeout;
}