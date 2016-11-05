package com.appleeeee.geekhubgrouplist.api;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String GIT_BASE_URL = "https://api.github.com";
    public static final String GOOGLE_BASE_URL = "https://www.googleapis.com";

    private static Api api;

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
