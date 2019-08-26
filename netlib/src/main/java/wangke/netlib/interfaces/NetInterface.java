package wangke.netlib.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;

import exception.XhsException;

/**
 * Project : NetLib
 * Created by 王可 on 2016/10/9.
 */

public interface NetInterface<P, B, H> {
    /**
     * return the request url value
     * not null
     * @return
     */
    @NonNull
    String getUrl();

    /**
     * use for handle the error of Json converting not correspond.
     *
     * @param t Throwable IOException when Deserialize error.
     */
    void onJsonConvertError(Throwable t);

//    void show(Context context);

//    void dismiss(Context context);

    P createPackage(B bodyObject, H header, String code);

    H createHeader(String requestCode);

    /**
     * @param h
     *
     * @return return the header of the package data
     */
    boolean handleHeader(H h,Context context) throws XhsException;

    /**
     * @param P
     *
     * @return header of the package data
     */
    H obtainHeader(P P);

    /**
     *
     * @param p package data
     *
     * @return Body of the package data
     */
    B obtainBody(P p);
    String getToken();
}
