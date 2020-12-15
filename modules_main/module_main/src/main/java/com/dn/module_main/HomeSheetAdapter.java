package com.dn.module_main;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.dn.domain.Music;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import zee.library.common.base.BaseRecyclerAdapter;
import zee.library.mglide.MGlide;
import zee.library.utils.DisplayUtils;

@Deprecated
public class HomeSheetAdapter extends BaseRecyclerAdapter<Music, HomeSheetAdapter.ViewHolder> {

    private static int itemWith;

    ArrayList<Integer> imgs = new ArrayList<Integer>() {
        {
            for (int i = 0; i < 2; i++) {
                add(R.mipmap.img_music1);
                add(R.mipmap.img_music2);
                add(R.mipmap.img_music3);
                add(R.mipmap.img_music4);
                add(R.mipmap.img_music5);
                add(R.mipmap.img_music6);
            }
        }
    };

    public HomeSheetAdapter() {
        super(R.layout.item_home_sheet);
        itemWith = (DisplayUtils.getScreenWidth() - DisplayUtils.dp2px(48)) / 3;
    }

    @Override
    protected void convert(ViewHolder helper, Music item) {
        /*Glide.with(mContext)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606926129166&di=f08418580e24fee51077e6a4f5279ecf&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Fmw690%2F9d6a5d79gy1gkemogt3gaj20uj0xc13i.jpg")
                //.override(124,124)
                //.transition()
                //.thumbnail()
                .into(helper.ivImg);*/
        //helper.ivImg.setImageResource(imgs.get(helper.getAdapterPosition() % getItemCount()));

        if (!TextUtils.isEmpty(item.getImg())) {
            MGlide.with((FragmentActivity) mContext)
                    .load(item.getImg())
                    .into(helper.ivImg);
        } else {
            helper.ivImg.setImageResource(imgs.get(helper.getAdapterPosition() % getItemCount()));
        }

        helper.tvTitle.setText(item.getMusicName() + " - " + item.getSingerName());
    }

    static class ViewHolder extends BaseViewHolder {

        ImageView ivImg;
        FrameLayout vgItem;
        TextView tvTitle;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            ivImg = view.findViewById(R.id.iv_img);
            vgItem = view.findViewById(R.id.vg_item);
            ViewGroup.LayoutParams layoutParams = vgItem.getLayoutParams();
            layoutParams.width = itemWith;
            layoutParams.height = itemWith;
            vgItem.setLayoutParams(layoutParams);
        }
    }

}