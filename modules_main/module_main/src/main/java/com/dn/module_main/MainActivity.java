package com.dn.module_main;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dn.module_main.livedata.bus.LiveDataBus;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;
import zee.library.common.base.BasePagerAdapter;
import zee.library.common.widget.PlayButtonView;
import zee.library.utils.DisplayUtils;
import zee.library.utils.UIHelper;

@Route(path = "/main/MainActivity")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PlayButtonView playButtonView;
    private MenuFragment menuFragment;
    private DrawerLayout drawerLayout;
    private View drawerL;
    private ViewPager viewPager;
    private TextView tvHomeTab1;
    private TextView tvHomeTab2;
    private TextView tvHomeTab3;
    private TextView tvHomeTab4;

    private List<TextView> tabs = new ArrayList<>();

    // 通过接口的方式去实现生命周期感知者
    //private ActivityLifecycle lifecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        addFragments();
        setDrawerLayout();
        initViewpager();

        //addMusicList();

        //lifecycle = new ActivityLifecycle();

        // 使用Lifecycle来进行绑定
        //getLifecycle().addObserver(new ZeeLib());
        //ZeeLib.getInstance().register(this);

        LiveDataBus.getInstance().with("playMusic", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //lifecycle.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //lifecycle.onStop();
    }

//    private void loadData3() {
//        MRetrofit.getApi()
//                .getMusics("baidu.ting.billboard.billList", 1, 1, 0)
//                .enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        Log.i(response.body().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });
//    }

//    private void loadData2() {
//
//        // 添加日志拦截
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
//                new HttpLoggingInterceptor.Logger() {
//                    @Override
//                    public void log(String message) {
//                        Log.i(message);
//                    }
//                }).setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        // 修改请求头拦截
//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder()
//                        .header("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        };
//
//        // 生成OkHttpClient
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
//                .addInterceptor(interceptor)
//                .build();
//
//        // 创建Retrofit并设置OkHttpClient
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://tingapi.ting.baidu.com")
//                //.addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//
//        // 获取动态代理对象
//        MusicApi api = retrofit.create(MusicApi.class);
//        Log.i(api.getClass().toString());
//
//        // 发起请求
//        Call<String> musics = api.getMusics("baidu.ting.billboard.billList", 1, 1, 0);
//        musics.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.i(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
//    }

//    private void loadData() {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        // 通过Builder创建OkHttpClient对象
//        // OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
//        //OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
//        Request request = new Request.Builder()
//                .url("http://www.baidu.com")
//                .build();
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                //.addInterceptor(new LogInterceptor())
//                .build();
//
//        /*try {
//            // 同步的发送请求
//            Response response = okHttpClient.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//
//        // 异步的方式发送请求
//        okHttpClient.newCall(request)
//                .enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//
//                    }
//                });
//
//    }

//    private void addMusicList() {
//        DBManager.getInstance().dropTable(Music.class);
//        final BaseDao<Music> musicDao = DBManager.getInstance().getBaseDao(Music.class);
//        musicDao.insert(new Music("回到过去", "周杰伦"));
//        musicDao.insert(new Music("晴天", "周杰伦"));
//        musicDao.insert(new Music("双节棍", "周杰伦"));
//        musicDao.insert(new Music("龙拳", "周杰伦"));
//        musicDao.insert(new Music("七里香", "周杰伦"));
//        musicDao.insert(new Music("一路向北", "周杰伦"));
//        //DBManager.getInstance().getBaseDao(BaseDaoNewImpl.class, Music.class).query("");
//    }

    private void addFragments() {
        menuFragment = new MenuFragment();
        UIHelper.addFragment(getSupportFragmentManager(), menuFragment, R.id.fl_content_menu);
    }

    private void initViews() {
        findViewById(R.id.fl_play_pause).setOnClickListener(this);
        findViewById(R.id.iv_menu).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
        findViewById(R.id.tv_home_tab1).setOnClickListener(this);
        findViewById(R.id.tv_home_tab2).setOnClickListener(this);
        findViewById(R.id.tv_home_tab3).setOnClickListener(this);
        findViewById(R.id.tv_home_tab4).setOnClickListener(this);
        findViewById(R.id.tv_setting).setOnClickListener(this);
        playButtonView = findViewById(R.id.pb_play_pause);
        tvHomeTab1 = findViewById(R.id.tv_home_tab1);
        tvHomeTab2 = findViewById(R.id.tv_home_tab2);
        tvHomeTab3 = findViewById(R.id.tv_home_tab3);
        tvHomeTab4 = findViewById(R.id.tv_home_tab4);
        viewPager = findViewById(R.id.view_pager_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerL = findViewById(R.id.vg_drawer);
    }

    private void initViewpager() {
        tabs.add(tvHomeTab1);
        tabs.add(tvHomeTab2);
        tabs.add(tvHomeTab3);
        tabs.add(tvHomeTab4);
        final MusicHomeFragment fragment1 = new MusicHomeFragment();
        final MusicHomeFragment fragment2 = new MusicHomeFragment();
        final MusicHomeFragment fragment3 = new MusicHomeFragment();
        final MusicHomeFragment fragment4 = new MusicHomeFragment();
        BasePagerAdapter customViewPagerAdapter = new BasePagerAdapter(getSupportFragmentManager());
        customViewPagerAdapter.addFragment(fragment1, "tab1");
        customViewPagerAdapter.addFragment(fragment2, "tab2");
        customViewPagerAdapter.addFragment(fragment3, "tab3");
        customViewPagerAdapter.addFragment(fragment4, "tab4");
        viewPager.setAdapter(customViewPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
        switchTabs(0);
    }

    private void switchTabs(int position) {
        for (int i = 0; i < tabs.size(); i++) {
            TextView tv = tabs.get(i);
            if (position == i) {
                //tabs.get(i).setSelected(true);
                tv.setPadding(0, 0, 0, DisplayUtils.dp2px(2));
                tv.setTextColor(getResources().getColor(R.color.tab_color_selected));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            } else {
                //tabs.get(i).setSelected(false);
                tv.setPadding(0, 0, 0, 0);
                tv.setTextColor(getResources().getColor(R.color.tab_color_default));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            }
        }
    }

    private void setDrawerLayout() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
             */
            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * arg1 表示 滑动的幅度（0-1）
             */
            @Override
            public void onDrawerSlide(View arg0, float arg1) {
            }

            @Override
            public void onDrawerOpened(View arg0) {
            }

            @Override
            public void onDrawerClosed(View arg0) {
            }
        });
    }

    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(drawerL, false);
            //drawerLayout.closeDrawers();
        }
    }

    private void openDrawer() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_menu) {
            openDrawer();

        } else if (i == R.id.iv_search) {
        } else if (i == R.id.tv_home_tab1) {
            viewPager.setCurrentItem(0);

        } else if (i == R.id.tv_home_tab2) {
            viewPager.setCurrentItem(1);

        } else if (i == R.id.tv_home_tab3) {
            viewPager.setCurrentItem(2);

        } else if (i == R.id.tv_home_tab4) {
            viewPager.setCurrentItem(3);

        } else if (i == R.id.tv_setting) {
        } else if (i == R.id.fl_play_pause) {
            if (isPlaying) {
                isPlaying = false;
                playButtonView.setPlaying(false);
                playButtonView.setProgress(0);
            } else {
                isPlaying = true;
                showPlayProgress();
            }

        }
    }

    private volatile boolean isPlaying = false;

    public void showPlayProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playButtonView.setPlaying(true);
                    }
                });
                while (true) {
                    if (!isPlaying) {
                        return;
                    }
                    final int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playButtonView.setProgress(finalI);
                        }
                    });
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    if (i >= 100) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isPlaying = false;
                                playButtonView.setPlaying(false);
                                playButtonView.setProgress(0);
                            }
                        });
                        return;
                    }
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}