package com.appleeeee.geekhubgrouplist.api;


import com.appleeeee.geekhubgrouplist.model.UserGitInfo;
import com.appleeeee.geekhubgrouplist.model.UserGoogleInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/users/{username}")
    Call<UserGitInfo> getUserInformation(@Path("username") String username);

    @GET("/plus/v1/people/{username}")
    Call<UserGoogleInfo> getGoogleUserInfo(@Path("username") String username,
                                           @Query("key") String key);

}
