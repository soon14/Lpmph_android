package com.ailk.im.net;

import rx.functions.Func1;

/**
 * Project : XHS
 * Created by 王可 on 2016/10/21.
 */

public class JsonMapFunc<T, R> implements Func1<T, R> {
    @Override
    public R call(T t) {
        return null == t ? null : (R) t;
    }
}
