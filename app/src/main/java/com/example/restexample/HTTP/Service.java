package com.example.restexample.HTTP;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.POST;

public interface Service {
    @POST("office")
    Call<JsonObject> getOffice();
}
