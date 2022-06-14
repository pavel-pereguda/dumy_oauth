package com.retro.sub;

import com.retro.sub.dtos.PaymentPayload;
import com.retro.sub.dtos.TokenResponseDto;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.degrade.sentinel.SentinelDegrade;
import com.github.lianjiatech.retrofit.spring.boot.log.LogStrategy;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

import static com.retro.sub.config.ConstantStorage.ROOT_API_URL;

@RetrofitClient(baseUrl = ROOT_API_URL)
@Logging(logStrategy = LogStrategy.BODY)
@SentinelDegrade(count = 500)
public interface RetrofitApi {

    @POST("payment-requests")
    Call<ResponseBody> uploadPayment(@Header ("Authorization") String auth, @Body PaymentPayload paymentDto);

    @POST("token")
    Call<TokenResponseDto> checkTokenForOutside(@QueryMap() Map<String, String> options);

}