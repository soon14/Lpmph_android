package com.ailk.pmph.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/16.
 */
public abstract class ListViewAdapter extends ArrayAdapter implements InitView{
    public int getmResource() {
        return mResource;
    }

    private int mResource;
    private Context context;
    public ListViewAdapter(Context context, int resource) {
        super(context, resource);
        mResource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (null != convertView) {
            view = convertView;
        } else {
            view = View.inflate(this.context, mResource, null);
        }
        initView(view, position,convertView);
        return view;
    }


}
