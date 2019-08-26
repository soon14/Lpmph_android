package com.ailk.im.tool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;


import com.ailk.pmph.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project : XHS
 * Created by 王可 on 2016/11/17.
 */

public class EaseSmileUtils {
    public static final String em_0001 = "[OK]";
    public static final String em_0002 = "[亲吻]";
    public static final String em_0003 = "[发愁]";
    public static final String em_0004 = "[发火]";
    public static final String em_0005 = "[口水]";
    public static final String em_0006 = "[可爱]";
    public static final String em_0007 = "[呲牙]";
    public static final String em_0008 = "[咖啡]";
    public static final String em_0009 = "[哭]";
    public static final String em_0010 = "[困]";
    public static final String em_0011 = "[困乏]";
    public static final String em_0012 = "[夜空]";
    public static final String em_0013 = "[大哭]";
    public static final String em_0014 = "[大笑]";
    public static final String em_0015 = "[太阳]";
    public static final String em_0016 = "[夸奖]";
    public static final String em_0017 = "[奸笑]";
    public static final String em_0018 = "[媒婆]";
    public static final String em_0019 = "[害羞]";
    public static final String em_0020 = "[差]";
    public static final String em_0021 = "[彩虹]";
    public static final String em_0022 = "[得志]";
    public static final String em_0023 = "[得意]";
    public static final String em_0024 = "[微笑]";
    public static final String em_0025 = "[心碎]";
    public static final String em_0026 = "[惊慌]";
    public static final String em_0027 = "[惊讶]";
    public static final String em_0028 = "[斗鸡眼]";
    public static final String em_0029 = "[晕倒]";
    public static final String em_0030 = "[暴汗]";
    public static final String em_0031 = "[楚楚可怜]";
    public static final String em_0032 = "[汗水]";
    public static final String em_0033 = "[流泪]";
    public static final String em_0034 = "[灯泡]";
    public static final String em_0035 = "[爱]";
    public static final String em_0036 = "[爱心]";
    public static final String em_0037 = "[狂吐]";
    public static final String em_0038 = "[玫瑰]";
    public static final String em_0039 = "[生气]";
    public static final String em_0040 = "[疑问]";
    public static final String em_0041 = "[礼物]";
    public static final String em_0042 = "[耍酷]";
    public static final String em_0043 = "[胜利]";
    public static final String em_0044 = "[蛋糕]";
    public static final String em_0045 = "[调皮]";
    public static final String em_0046 = "[赞]";
    public static final String em_0047 = "[鄙视]";
    public static final String em_0048 = "[钱钱]";
    public static final String em_0049 = "[青蛙]";
    public static final String em_0050 = "[音乐]";








    private static final Map<String, String> emojiMap = new HashMap<String, String>();
    public static final Map<String, Integer> emojiIcon = new HashMap<>();

    static {
        emojiMap.put(em_0001,em_0001);
        emojiMap.put(em_0002,em_0002);
        emojiMap.put(em_0003,em_0003);
        emojiMap.put(em_0004,em_0004);
        emojiMap.put(em_0005,em_0005);
        emojiMap.put(em_0006,em_0006);
        emojiMap.put(em_0007,em_0007);
        emojiMap.put(em_0008,em_0008);
        emojiMap.put(em_0009,em_0009);
        emojiMap.put(em_0010,em_0010);
        emojiMap.put(em_0011,em_0011);
        emojiMap.put(em_0012,em_0012);
        emojiMap.put(em_0013,em_0013);
        emojiMap.put(em_0014,em_0014);
        emojiMap.put(em_0015,em_0015);
        emojiMap.put(em_0016,em_0016);
        emojiMap.put(em_0017,em_0017);
        emojiMap.put(em_0018,em_0018);
        emojiMap.put(em_0019,em_0019);
        emojiMap.put(em_0020,em_0020);
        emojiMap.put(em_0021,em_0021);
        emojiMap.put(em_0022,em_0022);
        emojiMap.put(em_0023,em_0023);
        emojiMap.put(em_0024,em_0024);
        emojiMap.put(em_0025,em_0025);
        emojiMap.put(em_0026,em_0026);
        emojiMap.put(em_0027,em_0027);
        emojiMap.put(em_0028,em_0028);
        emojiMap.put(em_0029,em_0029);
        emojiMap.put(em_0030,em_0030);
        emojiMap.put(em_0031,em_0031);
        emojiMap.put(em_0032,em_0032);
        emojiMap.put(em_0033,em_0033);
        emojiMap.put(em_0034,em_0034);
        emojiMap.put(em_0035,em_0035);
        emojiMap.put(em_0036,em_0036);
        emojiMap.put(em_0037,em_0037);
        emojiMap.put(em_0038,em_0038);
        emojiMap.put(em_0039,em_0039);
        emojiMap.put(em_0040,em_0040);
        emojiMap.put(em_0041,em_0041);
        emojiMap.put(em_0042,em_0042);
        emojiMap.put(em_0043,em_0043);
        emojiMap.put(em_0044,em_0044);
        emojiMap.put(em_0045,em_0045);
        emojiMap.put(em_0046,em_0046);
        emojiMap.put(em_0047,em_0047);
        emojiMap.put(em_0048,em_0048);
        emojiMap.put(em_0049,em_0049);
        emojiMap.put(em_0050,em_0050);


    }


    private static final Spannable.Factory spannableFactory = Spannable.Factory
            .getInstance();

    public static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

    private static int simlesSize = 0;

    static {

        addPattern(emoticons, em_0001, R.drawable.em_0001);
        addPattern(emoticons, em_0002, R.drawable.em_0002);
        addPattern(emoticons, em_0003, R.drawable.em_0003);
        addPattern(emoticons, em_0004, R.drawable.em_0004);
        addPattern(emoticons, em_0005, R.drawable.em_0005);
        addPattern(emoticons, em_0006, R.drawable.em_0006);
        addPattern(emoticons, em_0007, R.drawable.em_0007);
        addPattern(emoticons, em_0008, R.drawable.em_0008);
        addPattern(emoticons, em_0009, R.drawable.em_0009);
        addPattern(emoticons, em_0010, R.drawable.em_0010);
        addPattern(emoticons, em_0011, R.drawable.em_0011);
        addPattern(emoticons, em_0012, R.drawable.em_0012);
        addPattern(emoticons, em_0013, R.drawable.em_0013);
        addPattern(emoticons, em_0014, R.drawable.em_0014);
        addPattern(emoticons, em_0015, R.drawable.em_0015);
        addPattern(emoticons, em_0016, R.drawable.em_0016);
        addPattern(emoticons, em_0017, R.drawable.em_0017);
        addPattern(emoticons, em_0018, R.drawable.em_0018);
        addPattern(emoticons, em_0019, R.drawable.em_0019);
        addPattern(emoticons, em_0020, R.drawable.em_0020);
        addPattern(emoticons, em_0021, R.drawable.em_0021);
        addPattern(emoticons, em_0022, R.drawable.em_0022);
        addPattern(emoticons, em_0023, R.drawable.em_0023);
        addPattern(emoticons, em_0024, R.drawable.em_0024);
        addPattern(emoticons, em_0025, R.drawable.em_0025);
        addPattern(emoticons, em_0026, R.drawable.em_0026);
        addPattern(emoticons, em_0027, R.drawable.em_0027);
        addPattern(emoticons, em_0028, R.drawable.em_0028);
        addPattern(emoticons, em_0029, R.drawable.em_0029);
        addPattern(emoticons, em_0030, R.drawable.em_0030);
        addPattern(emoticons, em_0031, R.drawable.em_0031);
        addPattern(emoticons, em_0032, R.drawable.em_0032);
        addPattern(emoticons, em_0033, R.drawable.em_0033);
        addPattern(emoticons, em_0034, R.drawable.em_0034);
        addPattern(emoticons, em_0035, R.drawable.em_0035);
        addPattern(emoticons, em_0036, R.drawable.em_0036);
        addPattern(emoticons, em_0037, R.drawable.em_0037);
        addPattern(emoticons, em_0038, R.drawable.em_0038);
        addPattern(emoticons, em_0039, R.drawable.em_0039);
        addPattern(emoticons, em_0040, R.drawable.em_0040);
        addPattern(emoticons, em_0041, R.drawable.em_0041);
        addPattern(emoticons, em_0042, R.drawable.em_0042);
        addPattern(emoticons, em_0043, R.drawable.em_0043);
        addPattern(emoticons, em_0044, R.drawable.em_0044);
        addPattern(emoticons, em_0045, R.drawable.em_0045);
        addPattern(emoticons, em_0046, R.drawable.em_0046);
        addPattern(emoticons, em_0047, R.drawable.em_0047);
        addPattern(emoticons, em_0048, R.drawable.em_0048);
        addPattern(emoticons, em_0049, R.drawable.em_0049);
        addPattern(emoticons, em_0050, R.drawable.em_0050);



        simlesSize = emoticons.size();
    }

    private static int EMOJI_CODE_TO_SYMBOL(int x) {
        return ((((0x808080F0 | (x & 0x3F000) >> 4) | (x & 0xFC0) << 10) | (x & 0x1C0000) << 18) | (x & 0x3F) << 24);
    }

    private static String EmojiCodeToString(int x) {
        int sym = EMOJI_CODE_TO_SYMBOL(x);
        byte data[] = {(byte) (sym & 0x00ff), (byte) (sym >> 8 & 0x00ff), (byte) (sym >> 16 & 0x00ff), (byte) (sym >> 24)};
        try {
            return new String(data, 0, 4, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
        emojiIcon.put(smile, resource);
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     *
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(context, entry.getValue()),
                            matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }
    public static CharSequence emoji2Str(CharSequence text, Context context) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(text);
            while (matcher.find()) {
                int resId = entry.getValue();
                Drawable drawable = ContextCompat.getDrawable(context, resId);
                drawable.setBounds(0, 0, 50, 50);//这里设置图片的大小
                ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
                builder.setSpan(imageSpan,matcher.start(),matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return builder;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static String getSmiledUnicodeText(Context context, CharSequence text) {
        if (emojiMap.containsKey(text)) {
            return emojiMap.get(text);
        } else {
            return "";
        }
    }

//    public static boolean containsKey(String key){
//        boolean b = false;
//        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
//            Matcher matcher = entry.getKey().matcher(key);
//            if (matcher.find()) {
//                b = true;
//                break;
//            }
//        }
//
//        return b;
//    }

    public static int getSmilesSize() {
        return simlesSize;
    }


}
