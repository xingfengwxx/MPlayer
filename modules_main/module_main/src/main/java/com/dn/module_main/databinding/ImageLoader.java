package com.dn.module_main.databinding;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import zee.library.mglide.MGlide;
import zee.library.mglide.request.RequestOptions;

public class ImageLoader extends BaseObservable {

    @BindingAdapter("bind:showPic")
    public static void showPic(ImageView iv, String img) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(144, 144);
        MGlide.with((FragmentActivity) iv.getContext())
                .load(img)
                .apply(requestOptions)
                .into(iv);
    }

}
