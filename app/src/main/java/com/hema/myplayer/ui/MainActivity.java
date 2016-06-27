package com.hema.myplayer.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hema.myplayer.R;
import com.hema.myplayer.ui.fragment.AttentionFragment;
import com.hema.myplayer.ui.fragment.FanjuFragment;
import com.hema.myplayer.ui.fragment.FindFragment;
import com.hema.myplayer.ui.fragment.RecommendFragment;
import com.hema.myplayer.ui.fragment.SubareaFragment;
import com.hema.myplayer.util.ActivityTools;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    public boolean sz = false;//顶部是不是展开
    private DrawerLayout drawerLayout;
    private AppBarLayout main_ap_layout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Adapter adapter;
    //侧边布局
    private ImageView logo;
    private LinearLayout souye;
    private LinearLayout lixian;
    private LinearLayout shouchang;
    private LinearLayout lishi;
    //顶部布局
    private ImageView huancun;
    private ImageView soushuo;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        initView();
        setListener();


    }

    @Override
    protected void onResume() {
        super.onResume();
        main_ap_layout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        main_ap_layout.removeOnOffsetChangedListener(this);
    }

    private void findview() {
        main_ap_layout = (AppBarLayout) findViewById(R.id.main_ap_layout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //侧边布局
        logo = (ImageView) findViewById(R.id.logo);
        souye = (LinearLayout) findViewById(R.id.souye);
        lixian = (LinearLayout) findViewById(R.id.huancun);
        shouchang = (LinearLayout) findViewById(R.id.shouchang);
        lishi = (LinearLayout) findViewById(R.id.lishi);
        //顶部布局
        huancun = (ImageView) findViewById(R.id.huancunx);
        soushuo = (ImageView) findViewById(R.id.soushuo);

    }

    private void initView() {

        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(2);
    }

    private void setListener() {
        huancun.setOnClickListener(this);
        lixian.setOnClickListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());

        String[] strs = new String[]{"番剧", "分区", "推荐", "关注", "发现"};


        adapter.addFragment(new FanjuFragment(), strs[0]);
        adapter.addFragment(new SubareaFragment(), strs[1]);
        adapter.addFragment(new RecommendFragment(), strs[2]);
        adapter.addFragment(new AttentionFragment(), strs[3]);
        adapter.addFragment(new FindFragment(), strs[4]);

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logo:

                break;
            case R.id.souye:

                break;
            case R.id.huancun:
                ActivityTools.skipActivity(this, DownloadActivity.class);
                break;
            case R.id.lishi:

                break;
            case R.id.shouchang:

                break;


        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {//顶部展开
            sz = true;
        }
        if ((appBarLayout.getTotalScrollRange() + verticalOffset) == 0) {
            sz = false;
        }
    }

    public boolean getiszangkai() {
        return sz;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
