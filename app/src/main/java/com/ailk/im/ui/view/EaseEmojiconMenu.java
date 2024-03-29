package com.ailk.im.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.ailk.im.tool.EaseSmileUtils;
import com.ailk.im.ui.adapter.EaseExpressionPagerAdapter;
import com.ailk.pmph.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 表情图片控件
 */
public class EaseEmojiconMenu extends EaseEmojiconMenuBase {

    private float emojiconSize;
    private List<String> reslist;
    private Context context;
    private ViewPager expressionViewpager;

    private int emojiconRows;
    private int emojiconColumns;
    private final int defaultRows = 3;
    private final int defaultColumns = 7;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public EaseEmojiconMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaseEmojiconMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EaseEmojiconMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_emojicon, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseEmojiconMenu);
        emojiconColumns = ta.getInt(R.styleable.EaseEmojiconMenu_emojiconColumns, defaultColumns);
        emojiconRows = ta.getInt(R.styleable.EaseEmojiconMenu_emojiconRows, defaultRows);
        ta.recycle();
        // 表情list
        reslist = getExpressionRes(EaseSmileUtils.getSmilesSize());
        // 初始化表情viewpager
        List<View> views = getGridChildViews();
        expressionViewpager = (ViewPager) findViewById(R.id.vPager);
        expressionViewpager.setAdapter(new EaseExpressionPagerAdapter(views));
    }


    /**
     * 获取表情的gridview的子views
     *
     * @return
     */
    private List<View> getGridChildViews() {
        int itemSize = emojiconColumns * emojiconRows - 1;
        int totalSize = EaseSmileUtils.getSmilesSize();
        int pageSize = totalSize % itemSize == 0 ? totalSize / itemSize : totalSize / itemSize + 1;
        List<View> views = new ArrayList<View>();
        for (int i = 0; i < pageSize; i++) {
            View view = View.inflate(context, R.layout.ease_expression_gridview, null);
            EaseExpandGridView gv = (EaseExpandGridView) view.findViewById(R.id.gridview);
            gv.setNumColumns(emojiconColumns);
            List<String> list = new ArrayList<String>();
            if (i != pageSize - 1) {
                list.addAll(reslist.subList(i * itemSize, (i + 1) * itemSize));
            } else {
                list.addAll(reslist.subList(i * itemSize, totalSize));
            }
            list.add("delete_expression");
            final EmojiconGridAdapter gridAdapter = new EmojiconGridAdapter(context, 1, list);
            gv.setAdapter(gridAdapter);
            gv.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String filename = gridAdapter.getItem(position);
                    if (listener != null) {
                        if (filename != "delete_expression") {
                            try {
                                // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                                Class clz = Class.forName("com.ailk.im.tool.EaseSmileUtils");
                                Field field = clz.getField(filename);
                                //before 4.4
                                //CharSequence cs = EaseSmileUtils.getSmiledText(context,(String) field.get(null));
                                //after 4.4
                                String cs = EaseSmileUtils.getSmiledUnicodeText(context, (String) field.get(null));
                                SpannableString result = new SpannableString(cs);
                                int resId = 0;
                                if (EaseSmileUtils.emojiIcon.containsKey(cs)) {
                                    resId = EaseSmileUtils.emojiIcon.get(cs);

                                }


                                listener.onExpressionClicked(strToSmiley(cs, resId, context));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            listener.onDeleteImageClicked();
                        }
                    }

                }
            });

            views.add(view);
        }
        return views;
    }

    public CharSequence strToSmiley(CharSequence text, int resId, Context context) {
        if (0 == resId) {
            return text;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(0, 0, 50, 50);//这里设置图片的大小
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        builder.setSpan(imageSpan, 0,
                text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "em_" + String.format("%04d", x);
            reslist.add(filename);
        }
        return reslist;

    }

    private class EmojiconGridAdapter extends ArrayAdapter<String> {

        public EmojiconGridAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.ease_row_expression, null);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_expression);

            String filename = getItem(position);
            int resId = getContext().getResources().getIdentifier(filename, "drawable", getContext().getPackageName());
            imageView.setImageResource(resId);

            return convertView;
        }

    }

}
