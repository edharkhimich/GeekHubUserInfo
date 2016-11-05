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
import com.appleeeee.geekhubgrouplist.model.UserGitInfo;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGitInfoActivity extends AppCompatActivity {
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.profile_name)
    TextView profileName;
    @BindView(R.id.profile_location)
    TextView profileLocation;
    @BindView(R.id.profile_followers)
    TextView profileFollowers;
    @BindView(R.id.profile_following)
    TextView profileFollowing;
    @BindView(R.id.profile_repositories)
    TextView profileRepositories;
    @BindView(R.id.recycler_view_tollbar)
    Toolbar infoToolbar;

    private Intent intent;
    private String nickName;
    private String location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_git_info);
        ButterKnife.bind(this);

        intent = getIntent();
        nickName = intent.getStringExtra(RecyclerViewAdapter.KEY);
        setToolbar();
        makeRequest();
    }

    private void setToolbar(){
        setSupportActionBar(infoToolbar);
        infoToolbar.setNavigationIcon(R.drawable.ic_action_back);
        getSupportActionBar().setTitle(R.string.profile_information);
        infoToolbar.setBackgroundColor(getResources().getColor(R.color.lightBlueForBack));
        infoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }

    private void makeRequest(){
        Api.getInstance().getGitHubApiInterface().getUserInformation(nickName).enqueue(new Callback<UserGitInfo>() {
            @Override
            public void onResponse(Call<UserGitInfo> call, Response<UserGitInfo> response) {
                location = response.body().getLocation();
                if (response.code() == 200) {
                    Picasso.with(getApplicationContext()).load(response.body().getAvatar_url()).into(profileImage);
                    profileName.setText(response.body().getName());
                    if(location==null){
                        profileLocation.setText(R.string.unknown_location);
                    }else {
                        profileLocation.setText(response.body().getLocation());
                    }
                    profileFollowers.setText(response.body().getFollowers());
                    profileFollowing.setText(response.body().getFollowing());
                    profileRepositories.setText(response.body().getPublic_repos());
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.wrong_response), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserGitInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.wrong_response), Toast.LENGTH_LONG).show();
            }
        });
    }
}
