package com.tvs.splashactivity.restapi;

import android.widget.TableLayout;

import com.tvs.splashactivity.models.TableData;
import com.tvs.splashactivity.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("/")
    Call<TableData> createUser(@Body User login);
}
