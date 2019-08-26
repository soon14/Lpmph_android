package com.ailk.pmph.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MaskableImageView extends ImageView{

    private boolean touchEffect = true;
    public final float[] BG_PRESSED = new float[] { 1, 0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };
    public final float[] BG_NOT_PRESSED = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

    public MaskableImageView(Context context) {
        super(context);
    }

    public MaskableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaskableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setPressed(boolean pressed) {
        updateView(pressed);
        super.setPressed(pressed);
    }

    /**
     * 根据是否按下去来刷新bg和src
     * @param pressed
     */
    private void updateView(boolean pressed){
        //如果没有点击效果
        if( !touchEffect ){
            return;
        }//end if
        if(this.getBackground() == null){
        	setBackgroundColor(Color.TRANSPARENT);
        }
        if( pressed ){//点击
            /**
             * 通过设置滤镜来改变图片亮度@minghao
             */
            this.setDrawingCacheEnabled(true);
            this.setColorFilter( new ColorMatrixColorFilter(BG_PRESSED) ) ;
            this.getBackground().setColorFilter( new ColorMatrixColorFilter(BG_PRESSED) );
        }else{//未点击
            this.setColorFilter( new ColorMatrixColorFilter(BG_NOT_PRESSED) ) ;
            this.getBackground().setColorFilter(
                    new ColorMatrixColorFilter(BG_NOT_PRESSED));
        }
    }
}