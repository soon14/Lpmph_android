package com.ailk.im.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.ailk.pmph.R;
import com.ailk.tool.GlideUtil;
/**
 * Project : pmph_android
 * Created by 王可 on 2017/3/21.
 */
public class ViewIMPicActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String src = getIntent().getStringExtra("src");
        setContentView(R.layout.activity_view_impics);
        ImageView imageview = (ImageView) findViewById(R.id.img);
        GlideUtil.loadImg(ViewIMPicActivity.this, src, imageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
