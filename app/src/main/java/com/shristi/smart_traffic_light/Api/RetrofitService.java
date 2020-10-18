package com.shristi.smart_traffic_light.Api;

import com.shristi.smart_traffic_light.Models.Api_response;
import com.shristi.smart_traffic_light.Models.route_detail;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("maps/getRoute/")
    @FormUrlEncoded
    Call<route_detail> getlocation(@Field("originLat") Double lat,@Field("originLng") Double lng, @Field("destination") String dest,
                                   @Header("Authorization") String auth);
    @POST("user/login/")
    @FormUrlEncoded
    Call<Api_response> login(@Field("username") String username, @Field("password")String password);
}
