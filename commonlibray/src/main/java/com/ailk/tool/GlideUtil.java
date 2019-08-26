package com.ailk.tool;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pongo.commonlibray.R;

/**
 * Project : XHS
 * Created by 王可 on 2016/12/15.
 */

public class GlideUtil {
    public static void loadImg(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.default_img))
                .into(view);
    }

    public static void loadImg(Context context, String url, ImageView view, @DrawableRes int drawableId) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .placeholder(ContextCompat.getDrawable(context, drawableId))
                .into(view);
    }
}
