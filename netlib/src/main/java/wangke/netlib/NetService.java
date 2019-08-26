package wangke.netlib;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Project : Net Lib
 * Created by 王可 on 16/8/31.
 */
public interface NetService {
    @POST("app/json.do")
    Observable<ResponseBody> getResult(@Query("msg") String jsonStr);
}
