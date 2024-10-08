package org.example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("sensor-data")
    Call<Info> getData(@Query("token") String token);

    @POST("forward")
    Call<Object> forward(@Query("token") String token);
    @POST("right")
    Call<Object> right(@Query("token") String token);
    @POST("left")
    Call<Object> left(@Query("token") String token);
    @POST("backward")
    Call<Object> backward(@Query("token") String token);
}
