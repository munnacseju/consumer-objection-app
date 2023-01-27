package com.motiur.consumer.api;

import com.motiur.consumer.model.GetAllObjectionResponse;
import com.motiur.consumer.model.GetObjectionResponse;
import com.motiur.consumer.model.RegisterResponse;
import com.motiur.consumer.model.User;
import com.motiur.consumer.model.Objection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @POST("register")
    Call<RegisterResponse> createUser (
            @Body User user
    );

    @POST("login")
    Call<ResponseBody> checkUser (
            @Body User user
    );

    @POST("objections")
    Call<ResponseBody> sendObjection (
            @Body Objection objection
    );

    @GET("objections")
    Call<GetAllObjectionResponse> getObjections();

    @GET("objections/{id}")
    Call<GetObjectionResponse> getObjection(@Path("id") Long id);

    @DELETE("objections/{id}")
    Call<ResponseBody> deleteObjection(@Path("id") Long id);
}
