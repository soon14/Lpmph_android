package wangke.netlib.utils;


import com.apkfuns.logutils.LogUtils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import wangke.netlib.interfaces.NetInterface;

/**
 * Project : Net Lib
 * Created by 王可 on 16/8/31.
 */
public class JsonConvert<P, B, H> {
    private ObjectMapper objectMapper;
    private NetInterface<P, B, H> netInterface;

    public JsonConvert(NetInterface netInterface) {
        this.setObjectMapper(new ObjectMapper());
        this.netInterface = netInterface;
    }

    private ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * bean对象转换为字符串
     *
     * @param bean
     *
     * @return
     */
    public String writeObjectToJsonString(Object bean) {
        try {
            return getObjectMapper().writeValueAsString(bean);
        } catch (IOException e) {
            LogUtils.e("Error when write Object to String :" + e);
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符串转换为对象
     *
     * @param json       json字符串
     * @param valueClass 类
     * @param <T>        泛型
     *
     * @return
     */
    public <T> T readJsonStringToObject(String json, Class<T> valueClass) throws IOException {
        try {
            if (valueClass == null)
                return null;
            if (json == null)
                return null;
            ClassLoader loader = (valueClass.getClassLoader() == null) ? Thread
                    .currentThread().getContextClassLoader() : valueClass
                    .getClassLoader();
            Thread.currentThread().setContextClassLoader(loader);
            return getObjectMapper().readValue(json, valueClass);
        } catch (IOException e) {
            LogUtils.e("Error when reading String to Object" + e);
            netInterface.onJsonConvertError(e);
            throw e;
        }
    }
}
