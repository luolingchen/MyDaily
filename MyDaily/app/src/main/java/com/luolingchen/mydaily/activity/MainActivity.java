package com.luolingchen.mydaily.activity;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.luolingchen.mydaily.R;
import com.luolingchen.mydaily.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout refresh_layout;
    private Toolbar toolbar;
    private FrameLayout fl_content;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
    }

    private void initFragment() {
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_content,new MainFragment()).commit();

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.BLUE); //设置toolbar背景颜色
        setSupportActionBar(toolbar);

        refresh_layout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        //设置刷新时动画的颜色，可以设置4个
        refresh_layout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置刷新监听
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();//同步
    }

}
