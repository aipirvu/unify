package com.owlcreativestudio.unify.interfaces;

import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProfileService {
    @GET("/1.1/users/show.json")
    Call<User> profile(@Query("user_id") long id);
}
