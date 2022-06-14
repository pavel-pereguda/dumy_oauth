package com.retro.sub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitResponse {

    private boolean failed;
    private String message;

    @SneakyThrows
    public static SubmitResponse asSuccess(ResponseBody message) {
        return SubmitResponse.builder()
                .message(message != null ? message.string() : null)
                .build();
    }

    @SneakyThrows
    public static SubmitResponse asFailed(ResponseBody message) {
        return SubmitResponse.asFailed(message != null ? message.string() : null);
    }

    public static SubmitResponse asFailed(String message) {
        return SubmitResponse.builder()
                .message(message)
                .failed(true)
                .build();
    }

}