package com.shristi.smart_traffic_light.Api;

import com.shristi.smart_traffic_light.Models.Api_response;
import com.shristi.smart_traffic_light.Models.route_detail;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    @FormUrlEncoded

    @POST("maps/getRoute/")
    Call<route_detail> getlocation(@Field("originLat") Double lat,@Field("originLng") Double lng, @Field("destination") String dest,
                                   @Header("Authorization") String auth);
    @POST("user/login/")
    @FormUrlEncoded
    Call<Api_response> login(@Field("username") String username, @Field("password")String password);
    @GET("maps/turnSignalNormal/{signalId}/")
    Call<ResponseBody> turnlightsnormal(@Path("signalId") int signalid);
}
