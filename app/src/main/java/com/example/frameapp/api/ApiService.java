package com.example.frameapp.api;



import com.example.frameapp.response.CommonResponse;
import com.example.frameapp.response.LoginRequest;
import com.example.frameapp.response.LoginResponse;
import com.example.frameapp.response.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @POST("login.php") // PHP script URL
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @Multipart
    @POST("signup.php") // This is your signup PHP endpoint
    Call<LoginResponse> registerUser(
            @Part("name") RequestBody name,
            @Part("phone_number") RequestBody phone,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part image
    );

    @FormUrlEncoded
    @POST("forget_password.php") // Ensure this path matches your backend script location
    Call<CommonResponse> resetPassword(
            @Field("phone_number") String phone,
            @Field("new_password") String newPassword
    );

        @GET("dashboard.php")
        Call<UserResponse> getUser(@Query("phone_number") String phoneNumber);
}

