package com.retro.sub.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitDto {

    private String creditorIban;
    private String creditorName;
    private String amount;
    private String currency;
    private String description;

    private String errorHandler;
    private String token;

}