package com.retro.sub;

import com.retro.sub.dtos.PaymentDto;
import com.retro.sub.dtos.SubmitResponse;
import com.retro.sub.dtos.TokenResponse;
import com.retro.sub.dtos.TokenResponseDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Call;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Service
public class CommonService {

    @Value(value = "${client.secret}")
    private String clientSecret;

    @Value(value = "${client.id}")
    private String clientId;

    private static final Logger log = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private RetrofitApi libraryClient;

    public SubmitResponse processPayment(PaymentDto paymentDto) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Call<ResponseBody> responseBodyCall = libraryClient.uploadPayment("Bearer " + paymentDto.getAuthorization(), paymentDto.getPayload());
            Response<ResponseBody> execute = responseBodyCall.execute();
            if (execute.isSuccessful()) {
                return SubmitResponse.asSuccess(execute.body());
            } else {
                log.error("Error delivering payload: {}", execute.body());
                return SubmitResponse.asFailed(execute.errorBody());
            }
        } catch (Exception ex) {
            log.error("Error handling retrofit call", ex);
            return SubmitResponse.asFailed(ex.getMessage());
        }
    }


    public TokenResponse checkAuth(String code) {
        try {
            Map<String, String> body = new HashMap<>();
            body.put("client_id", clientId);
            body.put("client_secret", clientSecret);
            body.put("code", code);
            body.put("scope", "READ");
            Call<TokenResponseDto> callLibResponse = libraryClient.checkTokenForOutside(body);
            Response<TokenResponseDto> libResponse = callLibResponse.execute();
            if (libResponse.isSuccessful()) {
                return TokenResponse.asSuccess(libResponse.body());
            } else {
                return TokenResponse.asFailed(libResponse.errorBody());
            }
        } catch (Exception ex) {
            return TokenResponse.asFailed(ex.getMessage());
        }
    }
}
