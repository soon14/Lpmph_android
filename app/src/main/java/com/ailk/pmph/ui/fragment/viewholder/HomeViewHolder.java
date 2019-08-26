package com.ailk.pmph.ui.fragment.viewholder;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ai.ecp.app.resp.Cms008Resp;
import com.ai.ecp.app.resp.Cms010Resp;
import com.ailk.pmph.AppContext;
import com.ailk.pmph.R;
import com.ailk.pmph.utils.Helper;
import com.ailk.pmph.utils.LogUtil;
import com.ailk.pmph.utils.PromotionParseUtil;
import com.ailk.pmph.utils.StringUtil;
import com.ailk.pmph.ui.view.CustomerRecylerView;
import com.ailk.pmph.ui.view.RecyclerScrollerView;
import com.ailk.tool.GlideUtil;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;

import org.apache.commons.lang.StringUtils;

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
public abstract class HomeViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

    public static SparseArray<Class<HomeViewHolder>> viewMap;
    protected final int resoureceId;
    protected View line;
    protected View bottomLine;
    protected View viewLine;
    protected ImageView title_img;
    protected TextView title_name;
//    protected ImageView title_more;
    protected RelativeLayout model_title;
    protected FrameLayout model_body;
    protected TextView title_more;
    protected int bodyHeight_truth;
    protected ImageView model_devide;
    protected AQuery mAQuery;
    protected Activity mConText;

    protected final int bodyWidth;
    protected final int bodyHeight;
    protected ViewGroup mRecylerView;
    private Cms010Resp.Model mModel;

    public HomeViewHolder(Activity context,LayoutInflater inflater, ViewGroup viewGroup, int resoureId,int bodyWidthRes,int bodyHeightRes) {
        super(inflater.inflate(R.layout.home_model_layout, viewGroup, false));
        mRecylerView = viewGroup;
        mConText = context;
        mAQuery = new AQuery(context);
        mAQuery = mAQuery.recycle(this.itemView);
        this.resoureceId = resoureId;
        this.bodyHeight = context.getResources().getDimensionPixelOffset(bodyHeightRes);
        this.bodyWidth = context.getResources().getDimensionPixelOffset(bodyWidthRes);
        this.viewLine = this.itemView.findViewById(R.id.view_line);
        this.model_title = (RelativeLayout) this.itemView.findViewById(R.id.model_title);
        this.model_body = (FrameLayout) this.itemView.findViewById(R.id.model_body);
        this.line = this.model_title.findViewById(R.id.line);
        this.bottomLine = this.model_title.findViewById(R.id.bottom_line);
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

    public static HomeViewHolder getHolder(Activity activity,LayoutInflater mInflater, ViewGroup parent, int viewType) {
        if (viewMap == null) {
            findHomeViewClass();
            LogUtil.e(viewMap);
        }
        try {
            Class<HomeViewHolder> c = viewMap.get(viewType);
            LogUtil.e("viewType = " + viewType+" getclassName = " + c.getName());
            Constructor<HomeViewHolder> method = c.getConstructor(Activity.class,LayoutInflater.class, ViewGroup.class);
            return method.newInstance(activity,mInflater, parent);
        } catch (NoSuchMethodException e) {
            LogUtil.e(e.getMessage());
        } catch (InvocationTargetException e) {
            LogUtil.e(e.getMessage());
        } catch (InstantiationException e) {
            LogUtil.e(e.getMessage());
        } catch (IllegalAccessException e) {
            LogUtil.e(e.getMessage());
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
            if (c.isAnnotationPresent(HomeViewType.class)) {
                HomeViewType annotation = (HomeViewType) c.getAnnotation(HomeViewType.class);
                LogUtil.e("获得注解:" + c.getName() + " : " + annotation.ViewType());
                viewMap.put(annotation.ViewType(), c);
            }
        }
    }

    private static List<Class<?>> getClasses() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(HomeViewPagerHolder.class);// 轮播广告 200
        classes.add(HomeMenuHolder.class);// 栏目 310
        classes.add(HomeBulletinHolder.class);// 信息公告 510
        classes.add(HomeAdOneHolder.class);// 单列图 210
        classes.add(HomeAdFourHolder.class);// 四列图 240
        classes.add(HomeThirtyHolder.class);// 30楼层 30
        classes.add(HomeThirtyOneHolder.class);// 31楼层 31
        classes.add(HomeThirtyTwoHolder.class);// 32楼层 32
        classes.add(HomeFortyHolder.class);// 40楼层 40
        classes.add(HomeLikeHolder.class);//猜你喜欢 410
        classes.add(HomeSecondKillHolder.class);//限时秒杀 132

        classes.add(HomeFourHolder.class);
        classes.add(HomeFooterHolder.class);
        classes.add(HomeTenHolder.class);
        classes.add(HomeElevenHolder.class);
        classes.add(HomeTwentyHolder.class);
        classes.add(HomeTwentyOneHolder.class);
        classes.add(HomeTwentyTwoHolder.class);
        classes.add(HomeFortyOneHolder.class);
        classes.add(HomeSixtyOneHolder.class);
        return classes;
    }

    protected abstract void initBodyView(ViewGroup viewGroup);

    public void initData(Cms010Resp.Model model) {
        if(mModel == model){
            return;
        }
        this.mModel = model;
        if(model.getName()!=null && model.getName().trim().length()>0 ){
            LogUtil.e("name===================" + model.getName());
            if (model.getType() == 200) {
                mAQuery.id(model_title).visibility(View.GONE);
                mAQuery.id(line).visibility(View.GONE);
            }
            else if (model.getType() == 210) {
                mAQuery.id(model_title).visibility(View.GONE);
            }
            else if (model.getType() == 240) {
                mAQuery.id(model_title).visibility(View.GONE);
                mAQuery.id(line).visibility(View.GONE);
            }
            else if (model.getType() == 300) {
                mAQuery.id(line).visibility(View.GONE);
            }
            else if (model.getType() == 510) {
                mAQuery.id(line).visibility(View.GONE);
            }
            else {
                mAQuery.id(line).visibility(View.VISIBLE);
                mAQuery.id(model_title).visibility(View.VISIBLE);
                mAQuery.id(title_name).text(model.getName());
            }
            if (model.getType() < 100) {
                mAQuery.id(viewLine).backgroundColor(Color.parseColor(model.getColor()));
            }
            if (model.getType() == 410) {
                mAQuery.id(viewLine).backgroundColor(Color.parseColor(model.getColor()));
                mAQuery.id(model_title).clickable(false);
                mAQuery.id(title_more).visibility(View.GONE);
            } else {
                mAQuery.id(title_more).visibility(View.VISIBLE);
            }
        }else{
            mAQuery.id(model_title).visibility(View.GONE);
        }

        if(model.getType() == 0){
            mAQuery.id(model_devide).invisible();
        }else{
            mAQuery.id(model_devide).visible();
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
//                aq.id(imgView).image(url, false, true, targetWidth, R.drawable.default_img, null, 0);
            }
        }
        else{
            if(mRecylerView instanceof RecyclerScrollerView) {
                if(this instanceof HomeViewPagerHolder){
                    GlideUtil.loadImg(mConText, url, imgView);
//                    aq.id(imgView).image(url, true, true, targetWidth, R.drawable.default_img, AppContext.bmPreset, 0);
                }else {
                    GlideUtil.loadImg(mConText, url, imgView);
//                    imgView.setImageBitmap(AppContext.bmPreset);
//                    imgView.setTag(R.id.tag_aq, aq);
//                    imgView.setTag(R.id.tag_url, url);
//                    imgView.setTag(R.id.tag_width, targetWidth);
                }
            } else {
                GlideUtil.loadImg(mConText, url, imgView);
//                aq.id(imgView).image(url, true, true, targetWidth, R.drawable.default_img, AppContext.bmPreset, 0);
            }
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

    @Override
    public void onClick(View v) {
        if((v.getTag()!=null && v.getTag() instanceof HashMap) ){
            HashMap<String,String> map = (HashMap<String, String>) v.getTag();
            String url = map.get("clickUrl");
            String shareKey = map.get("shareKey");
            PromotionParseUtil.parsePromotionUrl(mConText, url, true, false, shareKey);
        }
    }

}
