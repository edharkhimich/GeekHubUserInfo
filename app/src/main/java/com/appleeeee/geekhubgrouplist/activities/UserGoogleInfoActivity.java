package com.appleeeee.geekhubgrouplist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.RecyclerViewAdapter;
import com.appleeeee.geekhubgrouplist.api.Api;
import com.appleeeee.geekhubgrouplist.model.UserGoogleInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserGoogleInfoActivity extends AppCompatActivity {

    @BindView(R.id.google_info_toolbar)
    Toolbar googleInfoToolbar;
    @BindView(R.id.google_profile_image)
    ImageView googleUserImage;
    @BindView(R.id.google_user_name)
    TextView googleUserName;

    private static final String API_KEY = "AIzaSyCQTQH6bRN7kjHEymBW2y8Nam21ObklII4";

    private Intent intent;
    private String googleAcLogin;
    private String imageUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_google_info);
        ButterKnife.bind(this);

        intent = getIntent();
        googleAcLogin = intent.getStringExtra(RecyclerViewAdapter.KEY);
        setToolbar();
        makeRequest();
    }

    private void setToolbar() {
        setSupportActionBar(googleInfoToolbar);
        googleInfoToolbar.setNavigationIcon(R.drawable.ic_action_back);
        getSupportActionBar().setTitle(R.string.profile_information);
        googleInfoToolbar.setBackgroundColor(getResources().getColor(R.color.lightBlueForBack));
        googleInfoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeRequest() {
        Api.getInstance().getGoogleApiInterface().getGoogleUserInfo(googleAcLogin, API_KEY)
                .enqueue(new Callback<UserGoogleInfo>() {
                    @Override
                    public void onResponse(Call<UserGoogleInfo> call, Response<UserGoogleInfo> response) {
                        if (response.code() == 200) {
                            imageUrl = response.body().getImage().getUrl();
                            Picasso.with(getApplicationContext()).load(imageUrl).into(googleUserImage);
                            googleUserName.setText(response.body().getDisplayName());
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.wrong_response, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserGoogleInfo> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.wrong_response, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
