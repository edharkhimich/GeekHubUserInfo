package com.appleeeee.geekhubgrouplist.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.appleeeee.geekhubgrouplist.R;
import com.appleeeee.geekhubgrouplist.adapter.RecyclerViewAdapter;
import com.appleeeee.geekhubgrouplist.model.User;
import com.appleeeee.geekhubgrouplist.swipe.SwipeHelper;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.appleeeee.geekhubgrouplist.util.Constants.GITHUB_HOST;
import static com.appleeeee.geekhubgrouplist.util.Constants.GOOGLE_HOST;
import static com.appleeeee.geekhubgrouplist.util.Constants.KEY;
import static com.appleeeee.geekhubgrouplist.util.Constants.NAME_IN_LOW_CASE;

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_tollbar)
    Toolbar recViewToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;
    private boolean findUser;
    private Realm realm;
    private RealmResults<User> mRealmResults;
    private RealmList<User> list;
    private List<User> userList;
    private ItemTouchHelper.Callback callback;
    private ItemTouchHelper helper;
    private Intent intent;
    private Uri data;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        setToolbar();
        addUserList();
        setAdapter();
        setSwipe();
        urlHandling();
    }

    private void setToolbar() {
        setSupportActionBar(recViewToolbar);
        recViewToolbar.setNavigationIcon(R.drawable.ic_action_back);
        getSupportActionBar().setTitle(R.string.geekhub_group);
        recViewToolbar.setBackgroundColor(getResources().getColor(R.color.lightBlueForBack));
        recViewToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchView != null) {
                    if (!newText.isEmpty()) {
                        mRealmResults = realm.where(User.class)
                                .contains(NAME_IN_LOW_CASE, newText.toLowerCase(Locale.ENGLISH))
                                .findAll();
                        adapter.setData(mRealmResults);
                    } else {
                        adapter.setData(getListResults());
                    }
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setSwipe() {
        callback = new SwipeHelper(adapter, recyclerView);
        helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    private void setAdapter() {
        if (getListResults() != null) {
            adapter = new RecyclerViewAdapter(getApplicationContext(), getListResults());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        }
    }

    private RealmResults<User> getListResults() {
        mRealmResults = realm.where(User.class).findAll();
        return mRealmResults;
    }

    public void addUserList() {
        if (realm.isEmpty()) {
            list = new RealmList<>();
            list.add(new User("Химич Эдгар", "https://github.com/lyfm",
                    "https://plus.google.com/u/0/102197104589432395674", "lyfm", "102197104589432395674"));
            list.add(new User("Винник Владислав", "https://github.com/vlads0n",
                    "https://plus.google.com/u/0/117765348335292685488", "vlads0n", "117765348335292685488"));
            list.add(new User("Теплий Михайло", "https://github.com/RedGeekPanda",
                    "https://plus.google.com/u/0/110313151428733681846", "RedGeekPanda", "110313151428733681846"));
            list.add(new User("Рибак Богдан", "https://github.com/BogdanRybak1996",
                    "https://plus.google.com/u/0/103145064185261665176", "BogdanRybak1996", "103145064185261665176"));
            list.add(new User("Лещенко Иван", "https://github.com/ivleshch",
                    "https://plus.google.com/u/0/111088051831122657934", "ivleshch", "111088051831122657934"));
            list.add(new User("Сакуров Павло", "https://github.com/sakurov",
                    "https://plus.google.com/u/0/108482088578879737406", "sakurov", "108482088578879737406"));
            list.add(new User("Воловик Руслан", "https://github.com/RuslanVolovyk",
                    "https://plus.google.com/u/0/109719711261293841416", "RuslanVolovyk", "109719711261293841416"));
            list.add(new User("Кириченко Дар\'я", "https://github.com/dashakdsr",
                    "https://plus.google.com/u/0/103130382244571139113", "dashakdsr", "103130382244571139113"));
            list.add(new User("Рябко Андрій", "https://github.com/RyabkoAndrew",
                    "https://plus.google.com/u/0/110288437168771810002", "RyabkoAndrew", "110288437168771810002"));
            list.add(new User("Ситник Євгеній", "https://github.com/YevheniiSytnyk",
                    "https://plus.google.com/u/0/101427598085441575303", "YevheniiSytnyk", "101427598085441575303"));
            list.add(new User("Бочкарьова Альона", "https://github.com/HelenCool",
                    "https://plus.google.com/u/0/107382407687723634701", "HelenCool", "107382407687723634701"));
            list.add(new User("Мигаль Руслан", "https://github.com/rmigal",
                    "https://plus.google.com/u/0/106331812587299981536", "rmigal", "106331812587299981536"));
            list.add(new User("Смалько Ірина", "https://github.com/IraSmalko",
                    "https://plus.google.com/u/0/113994208318508685327", "IraSmalko", "113994208318508685327"));
            list.add(new User("Губський Валерій", "https://github.com/gvv-ua",
                    "https://plus.google.com/u/0/107910188078571144657", "gvv-ua", "107910188078571144657"));
            list.add(new User("Жданов Євген", "https://github.com/zhdanov-ek",
                    "https://plus.google.com/u/0/113264746064942658029", "zhdanov-ek", "113264746064942658029"));
            list.add(new User("Сергеенко Иван", "https://github.com/dogfight81",
                    "https://plus.google.com/u/0/111389859649705526831", "dogfight81", "111389859649705526831"));
            list.add(new User("Пахаренко Ігор", "https://github.com/IhorPakharenko",
                    "https://plus.google.com/u/0/108231952557339738781", "IhorPakharenko", "108231952557339738781"));
            list.add(new User("Сторчак Олександр", "https://github.com/new15",
                    "https://plus.google.com/u/0/106553086375805780685", "new15", "106553086375805780685"));
            list.add(new User("Піхманець Микола", "https://github.com/NikPikhmanets",
                    "https://plus.google.com/u/0/110087894894730430086", "NikPikhmanets", "110087894894730430086"));
            list.add(new User("Лимарь Володимир", "https://github.com/VovanNec",
                    "https://plus.google.com/u/0/109227554979939957830", "VovanNec", "109227554979939957830"));

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(list);
                }
            });
        }
    }

    private void urlHandling() {
        intent = getIntent();
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Intent.ACTION_VIEW)) {
                userList = list;
                data = intent.getData();
                url = getString(R.string.https) + data.getHost() + data.getPath();
                if (GITHUB_HOST.equals(data.getHost()) && data.getPath() != null
                        || GOOGLE_HOST.equals(data.getHost()) && data.getPath() != null
                        && String.valueOf(data).startsWith(getString(R.string.http))
                        || String.valueOf(data).startsWith(getString(R.string.https))) {
                    for (User user : userList) {
                        if (url.equals(user.getGitUrl())) {
                            Intent userGitInfoIntent = new Intent(this, UserGitInfoActivity.class);
                            userGitInfoIntent.putExtra(KEY, String.valueOf(data.getPath().substring(1)));
                            userGitInfoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(userGitInfoIntent);
                            findUser = true;
                            break;
                        } else if (url.equals(user.getGoogleUrl())) {
                            Intent userGoogleInfoIntent = new Intent(this, UserGoogleInfoActivity.class);
                            userGoogleInfoIntent.putExtra(KEY, String.valueOf(data.getPath().substring(5)));
                            userGoogleInfoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(userGoogleInfoIntent);
                            findUser = true;
                            break;
                        } else {
                            findUser = false;
                        }
                    }
                }
                if (!findUser)
                    Toast.makeText(this, R.string.no_user_in_list, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}

