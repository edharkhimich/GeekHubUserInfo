package com.appleeeee.geekhubgrouplist.api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.appleeeee.geekhubgrouplist.util.Constants.GIT_BASE_URL;
import static com.appleeeee.geekhubgrouplist.util.Constants.GOOGLE_BASE_URL;

public class Api {

    private static Api api;

//    private ApiInterface apiInterface;

    private Api() {
    }


    public static Api getInstance() {
        if (api == null) {
            return api = new Api();
        }
        return api;
    }

    public OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        return client.build();
    }

    public ApiInterface getGitHubApiInterface() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GIT_BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }

    public ApiInterface getGoogleApiInterface() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }
}
