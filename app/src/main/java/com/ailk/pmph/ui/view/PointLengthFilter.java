package com.ailk.pmph.ui.view;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 类注释:
 * 项目名:pmph_android
 * 包名:com.ailk.pmph.ui.view
 * 作者: Chrizz
 * 时间: 2017/3/2
 */

public class PointLengthFilter implements InputFilter {

    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return null;
        }
        String dValue = dest.toString();
        String[] splitArray = dValue.split("\\.");
        if (splitArray.length > 1) {
            String dotValue = splitArray[1];
            int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
            if (diff > 0) {
                return source.subSequence(start, end - diff);
            }
        }
        return null;
    }

}
