package com.retro.sub.config;

import com.retro.sub.RetrofitApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.time.Duration;

import static com.retro.sub.config.ConstantStorage.ROOT_API_URL;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RetrofitApi libraryClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(Duration.ofMinutes(30))
                .readTimeout(Duration.ofMinutes(30));
        return new Retrofit.Builder().client(httpClientBuilder.build())
                .baseUrl(ROOT_API_URL)
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .build().create(RetrofitApi.class);

    }
}