package com.shristi.smart_traffic_light.Api;

import com.shristi.smart_traffic_light.Models.Api_response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("user/login/")
    @FormUrlEncoded
    Call<Api_response> login(@Field("username") String username, @Field("password")String password);
}
