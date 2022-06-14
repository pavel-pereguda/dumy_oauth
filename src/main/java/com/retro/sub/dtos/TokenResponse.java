package com.retro.sub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.ResponseBody;

import java.io.IOException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

    private String token;
    private String errorMessage;

    public static TokenResponse asSuccess(TokenResponseDto responseDto) {
        return TokenResponse.builder()
                .token(responseDto != null ? responseDto.getAccessToken() : null)
                .build();
    }

    public static TokenResponse asFailed(ResponseBody responseBody) throws IOException {
        return TokenResponse.builder()
                .errorMessage(responseBody != null ? responseBody.string() : null)
                .build();
    }

    public static TokenResponse asFailed(String errorMessage) {
        return TokenResponse.builder()
                .errorMessage(errorMessage)
                .build();
    }

    public boolean existsToken(){
        return token!=null && !token.isEmpty() && errorMessage==null;
    }

}