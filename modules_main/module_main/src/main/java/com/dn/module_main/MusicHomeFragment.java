package com.dn.module_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.allure.lbanners.LMBanners;
import com.dn.module_main.bean.Music;
import com.dn.module_main.databinding.BaseRecyclerAdapter;
import com.dn.module_main.livedata.MusicViewModel;
import com.dn.module_main.livedata.bus.LiveDataBus;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MusicHomeFragment extends Fragment {

    private LMBanners bannerView;
    private RecyclerView recyclerView;
    private MusicViewModel viewModel;
    private BaseRecyclerAdapter<Music> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = inflater.inflate(R.layout.fragment_music_home, container, false);
        initViews(rootView);
        initBanner();
        initList();
        loadData();
        return rootView;
    }

    private void initList() {
        if (adapter == null) {
            adapter = new BaseRecyclerAdapter<>(getActivity(), R.layout.item_home_sheet, BR.music);
            recyclerView.setFocusable(false);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayout.VERTICAL, false) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
        }

        /*List<Music> musics = DBManager.getInstance().getBaseDao(Music.class).query(null);
        sheetAdapter.setNewData(musics);*/
//        sheetAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
//                /*Music music = sheetAdapter.getItem(position);
//                Toast.makeText(getActivity(), music.toString(), Toast.LENGTH_SHORT).show();*/
//
//                //DBManager.getInstance().getBaseDao(Music.class).update(null, null);
//                //DBManager.getInstance().getBaseDao(Music.class)
//                //        .update(new Music("缓缓飘落的枫叶像思念", "周杰伦"), new Music("一路向北", ""));
//
//                /*DBManager.getInstance().getBaseDao(Music.class).delete(music);
//                initList();*/
//
//                /*Music music = sheetAdapter.getItem(position);
//                music.setMusicName("aa周杰棍-刷街轮");
//                music.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606926129166&di=f08418580e24fee51077e6a4f5279ecf&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Fmw690%2F9d6a5d79gy1gkemogt3gaj20uj0xc13i.jpg");
//                viewModel.changeItem(position, music);*/
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Music music = sheetAdapter.getItem(position);
//                        LiveDataBus.getInstance().with("playMusic", String.class)
//                                .postValue(music.getMusicName() + "开始播放！！！");
//                    }
//                }).start();
//
//            }
//        });
    }

    private void loadData() {
        //ViewModelProviders.of()

        viewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        viewModel.getMusics().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> musics) {
                //sheetAdapter.setNewData(musics);
                adapter.setNewData(musics);
            }
        });

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Music music = adapter.getItem(position);
//                music.setMusicName("aa周杰棍-刷街轮");
//                music.setImg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606926129166&di=f08418580e24fee51077e6a4f5279ecf&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Fmw690%2F9d6a5d79gy1gkemogt3gaj20uj0xc13i.jpg");
//                viewModel.changeItem(position, music);

                Music music = adapter.getItem(position);
                LiveDataBus.getInstance().with("playMusic", String.class)
                                .postValue(music.getMusicName() + "开始播放！！！");
            }
        });
    }


    private void initViews(View rootView) {
        bannerView = rootView.findViewById(R.id.bannerView);
        recyclerView = rootView.findViewById(R.id.recycler_view_sheet);
    }

    private void initBanner() {
        ArrayList<Integer> imgs = new ArrayList<Integer>() {
            {
                add(R.mipmap.img_banner1);
                add(R.mipmap.img_banner2);
                add(R.mipmap.img_banner3);
            }
        };
        bannerView.setAdapter(new BannerImgAdapter(), imgs);
    }

}