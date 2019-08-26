package com.ailk.xhs.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ailk.tool.DimenUtil;

/**
 * Project : XHS
 * Created by 王可 on 2016/11/23.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    final static String TAG = "SpaceItemDecoration";
    Context mContext;
    int mSpace;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(Context context, int space) {
        this.mSpace = space;
        this.mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int itemCount = mAdapter.getItemCount();
//        int pos = parent.getChildAdapterPosition(view);
//        Log.d(TAG, "itemCount>>" +itemCount + ";Position>>" + pos);
//
//        outRect.left = 0;
//        outRect.top = 0;
//        outRect.bottom = 0;
//
//
//        if (pos != (itemCount -1)) {
//            outRect.right = mSpace;
//        } else {
//            outRect.right = 0;
//        }
        int height = DimenUtil.dp2px(mContext, mSpace);
        outRect.set(0, 0, 0, height);
    }
}