package com.ailk.integral.holder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms101Resp;
import com.ailk.integral.activity.InteExchangeRecordActivity;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.DialogAnotherUtil;
import com.ailk.pmph.utils.DialogUtil;
import com.ailk.pmph.utils.Helper;
import com.ailk.pmph.utils.IntePromotionParseUtil;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.MoneyUtils;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.ui.activity.LoginActivity;
import com.ailk.pmph.ui.view.CustomerRecylerView;
import com.ailk.pmph.ui.view.InteRecyclerScrollerView;
import com.ailk.tool.GlideUtil;
import com.androidquery.AQuery;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 主页配置各个模块的viewholder
 * Created by jiangwei on 14-12-2.
 */
public abstract class InteHomeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    public static SparseArray<Class<InteHomeViewHolder>> viewMap;
    protected final int resoureceId;
    protected View viewLine;
    protected ImageView title_img;
    protected TextView title_name;
//    protected ImageView title_more;
    protected LinearLayout model_title;
    protected FrameLayout model_body;
    protected  TextView title_more;
    protected int bodyHeight_truth;
    protected ImageView model_devide;
    protected AQuery aq;
    protected Activity mConText;

    protected final int bodyWidth;
    protected final int bodyHeight;
    protected ViewGroup mRecylerView;
    private Cms101Resp.Model inteModel;

    public InteHomeViewHolder(Activity context, LayoutInflater inflater, ViewGroup viewGroup, int resoureId, int bodyWidthRes, int bodyHeightRes) {
        super(inflater.inflate(R.layout.inte_home_model_layout, viewGroup, false));
        mRecylerView = viewGroup;
        mConText = context;
        aq = new AQuery(context);
        aq = aq.recycle(this.itemView);
        this.resoureceId = resoureId;
        this.bodyHeight = context.getResources().getDimensionPixelOffset(bodyHeightRes);
        this.bodyWidth = context.getResources().getDimensionPixelOffset(bodyWidthRes);
        this.viewLine = this.itemView.findViewById(R.id.view_line);
        this.model_title = (LinearLayout) this.itemView.findViewById(R.id.model_title);
        this.model_body = (FrameLayout) this.itemView.findViewById(R.id.model_body);
        this.title_name = (TextView) this.model_title.findViewById(R.id.title_name);
        this.title_more = (TextView) this.model_title.findViewById(R.id.title_more);
        inflater.inflate(resoureId, model_body, true);
        int scwidth = Helper.getMetrics(mConText).widthPixels;
        bodyHeight_truth = (int)(((double)scwidth)/bodyWidth*bodyHeight);
        this.model_body.getLayoutParams().height = bodyHeight_truth;
        this.model_body.getLayoutParams().width = scwidth;
        initBodyView(model_body);
    }

    public int getViewHeight(){
        return mConText.getResources().getDimensionPixelOffset(R.dimen.home_model_title_height)
                + bodyHeight_truth
                +mConText.getResources().getDimensionPixelOffset(R.dimen.home_model_devide_height);
    }

    public static InteHomeViewHolder getHolder(Activity activity, LayoutInflater mInflater, ViewGroup parent, int viewType) {
        if (viewMap == null) {
            findHomeViewClass();
            LogUtil.e(viewMap);
        }
        try {
            Class<InteHomeViewHolder> c = viewMap.get(viewType);
            LogUtil.e("viewMap = " + viewMap.size() + "viewType = " + viewType+" getclassName = " + c.getName());
            Constructor<InteHomeViewHolder> method = c.getConstructor(Activity.class,LayoutInflater.class, ViewGroup.class);
            return method.newInstance(activity,mInflater, parent);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void init() {
        findHomeViewClass();
    }

    private static void findHomeViewClass() {
        viewMap = new SparseArray<>();
        List<Class<?>> list = getClasses();
        for (Class c : list) {
            if (c.isAnnotationPresent(InteHomeViewType.class)) {
                InteHomeViewType annotation = (InteHomeViewType) c.getAnnotation(InteHomeViewType.class);
                LogUtil.e("获得注解:" + c.getName() + " : " + annotation.ViewType());
                viewMap.put(annotation.ViewType(), c);
            }
        }
    }

    /**
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClasses() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(InteHomeViewPagerHolder.class);// 轮播广告 200
        classes.add(InteHomeTextHolder.class);// 我的积分 610
        classes.add(InteHomeMenuHolder.class);// 栏目 310
        classes.add(InteHomeAdOneHolder.class);// 单列图 210
        classes.add(InteHomeAdFourHolder.class);// 四列图 240
        classes.add(InteHomeThirtyHolder.class);// 30楼层 30
        classes.add(InteHomeThirtyOneHolder.class);// 31楼层 31
        classes.add(InteHomeThirtyTwoHolder.class);// 32楼层 32
        classes.add(InteHomeFortyHolder.class);// 40楼层 40

        classes.add(InteHomeFourHolder.class);
        classes.add(InteHomeFooterHolder.class);
        classes.add(InteHomeTenHolder.class);
        classes.add(InteHomeElevenHolder.class);
        classes.add(InteHomeTwentyHolder.class);
        classes.add(InteHomeTwentyOneHolder.class);
        classes.add(InteHomeTwentyTwoHolder.class);
        classes.add(InteHomeFortyOneHolder.class);
        classes.add(InteHomeSixtyOneHolder.class);
        return classes;
    }

    protected abstract void initBodyView(ViewGroup viewGroup);

    public void initData(Cms101Resp.Model model){
        if (inteModel == model) {
            return;
        }
        this.inteModel = model;
        if(model.getName()!=null && model.getName().trim().length()>0 ){
            LogUtil.e("积分 name=============" + model.getName());
            viewLine.setVisibility(View.VISIBLE);
            aq.id(model_title).visibility(View.VISIBLE);
            aq.id(title_name).text(model.getName());
        }else{
            viewLine.setVisibility(View.GONE);
            aq.id(model_title).visibility(View.GONE);
        }

        if(model.getType() == 0){
            aq.id(model_devide).invisible();
        }else{
            aq.id(model_devide).visible();
        }
        setOnClickListener(model_title,model.getClickUrl(),null);
        setOnClickListener(title_more, model.getClickUrl(),null);
    }


    protected void setImageViewImg(String url,ImageView imgView,AQuery aq){
        setImageViewImg(url,imgView,aq,150);
    }

    protected void setImageViewImg(String url,ImageView imgView,AQuery aq,int targetWidth){
        if(mRecylerView instanceof CustomerRecylerView){
            int status = ((CustomerRecylerView)mRecylerView).getScrollState();
            if(status != CustomerRecylerView.SCROLL_STATE_IDLE){
                LogUtil.e(" status is fling set tag on imageview");
//                aq.id(imgView).image(url, false, true, 10,
//                        R.drawable.default_img, aq.getCachedImage(R.drawable.default_img), 0);
                GlideUtil.loadImg(mConText, url, imgView);
//                aq.id(imgView).image(R.drawable.default_img);
//                imgView.setTag(R.id.tag_aq,aq);
//                imgView.setTag(R.id.tag_url,url);
//                imgView.setTag(R.id.tag_width,targetWidth);
            }else{
                LogUtil.e(" status is  not fling ");
                GlideUtil.loadImg(mConText, url, imgView);
//                aq.id(imgView).image(url, false, true, targetWidth,
//                        R.drawable.default_img, null, 0);
            }
        }
        else
        if(mRecylerView instanceof InteRecyclerScrollerView) {
            if(this instanceof InteHomeViewPagerHolder){
                GlideUtil.loadImg(mConText, url, imgView);
//                aq.id(imgView).image(url, true, true, targetWidth,
//                        R.drawable.default_img, AppContext.bmPreset, 0);
            }else {
                GlideUtil.loadImg(mConText, url, imgView);
//                imgView.setImageBitmap(AppContext.bmPreset);
//                imgView.setTag(R.id.tag_aq, aq);
//                imgView.setTag(R.id.tag_url, url);
//                imgView.setTag(R.id.tag_width, targetWidth);
            }
        }else
        {
            GlideUtil.loadImg(mConText, url, imgView);
//            aq.id(imgView).image(url, true, true, targetWidth,
//                    R.drawable.default_img, AppContext.bmPreset, 0);
        }
    }

    protected void setTextView(String name, Long score, Long price, TextView tvName, TextView tvScore){
        tvName.setText(name);
        if ((score!=null && score!=0) && (price !=null && price !=0)) {
            tvScore.setText(score+"积分 + " + MoneyUtils.GoodListPrice(price));
        } else if ((score!=null && score!=0) && ((price!=null && price ==0)) || price==null) {
            tvScore.setText(score+"积分");
        } else if ((price!=null && price!=0) && ((score!=null && score==0)) || score==null) {
            tvScore.setText(MoneyUtils.GoodListPrice(price));
        }
    }

    protected void setOnClickListener(View view,String clickUrl,String shareKey){
        if(!StringUtil.isNullString(clickUrl)){
//            view.setTag(clickUrl);
//            view.setTag(1,shareKey);
            HashMap<String,String> map = new HashMap<>();
            map.put("clickUrl",clickUrl);
            map.put("shareKey",shareKey);
            view.setTag(map);
            view.setOnClickListener(this);
        }else{
            view.setTag(null);
            view.setOnClickListener(null);
        }
    }

    protected void redirectTo(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppContext.isLogin) {
                    DialogAnotherUtil.showCustomAlertDialogWithMessage(mConText, null,
                            "您未登录，请先登录", "登录", "取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                    mConText.startActivity(new Intent(mConText,LoginActivity.class));
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DialogUtil.dismissDialog();
                                }
                            });
                } else {
                    Intent intent = new Intent(mConText, InteExchangeRecordActivity.class);
                    mConText.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if((v.getTag()!=null && v.getTag() instanceof  HashMap) ){
            HashMap<String,String> map = (HashMap<String, String>) v.getTag();
            String url = map.get("clickUrl");
            String shareKey = map.get("shareKey");
            IntePromotionParseUtil.parsePromotionUrl(mConText, url, true, false, shareKey);
        }
    }

}
