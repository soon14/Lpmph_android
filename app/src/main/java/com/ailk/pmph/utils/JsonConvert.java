package com.ailk.pmph.utils;

import com.ailk.pmph.AppContext;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Project : PMPH
 * Created by 王可 on 16/3/4.
 * 用于Json和类对象之间的转换
 */
public class JsonConvert {
    private ObjectMapper objectMapper;

    public JsonConvert() {
        this.setObjectMapper(new ObjectMapper());
    }

    private ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * bean对象转换为字符串
     * @param bean
     *
     * @return
     */
    public String writeObjectToJsonString(Object bean) {
        try {
            return getObjectMapper().writeValueAsString(bean);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符串转换为对象
     * @param json json字符串
     * @param valueClass 类
     * @param <T> 泛型
     *
     * @return
     */
    public <T> T readJsonStringToObject(String json, Class<T> valueClass) {
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
            LogUtil.e(e);
            ToastUtil.show(AppContext.getContext(),
                    "返回数据格式不符,建议您升级到最新版本．");
            return null;
        }
    }
}
