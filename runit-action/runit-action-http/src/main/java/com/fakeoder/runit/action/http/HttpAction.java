package com.fakeoder.runit.action.http;

import com.alibaba.fastjson.JSONObject;
import com.fakeoder.runit.core.action.Action;
import com.fakeoder.runit.core.action.ActionResult;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuo
 */
public class HttpAction extends Action {
    private final static String HEADER = "header";
    private final static String URL = "url";
    private final static String METHOD = "method";
    private final static String REQUEST_PARAMS = "params";
    private final static String MEDIA_TYPE = "mediaType";


    OkHttpClient httpClient = new OkHttpClient();

    @Override
    public ActionResult run() {
        ActionResult result = null;
        Request request = new Request.Builder()
                .headers(buildHeaders())
                .url(buildUrl())
                .method(buildMethod(),buildRequestBody())
                .build();
        Call call = httpClient.newCall(request);

        Response response = null;
        try {
            response = call.execute();
            result = buildResult(response);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            response.close();
        }
        return result;
    }

    private ActionResult buildResult(Response response){
        Map<String,Object> map = new HashMap<>();
        try {
            map.put("response",response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ActionResult(this.id, JSONObject.toJSONString(map));
    }

    private RequestBody buildRequestBody() {
        Object media = context.get(MEDIA_TYPE);
        if(media==null){
            return null;
        }
        Object params = context.get(REQUEST_PARAMS);
        if(params==null){
            return null;
        }
        return RequestBody.create(MediaType.get(media.toString()), params.toString().getBytes(StandardCharsets.UTF_8));
    }

    private String buildMethod() {
        if(context.get(METHOD)==null){
            return null;
        }
        String method = context.get(METHOD).toString();
        return method;
    }

    private String buildUrl() {
        if(context.get(URL)==null){
            return null;
        }
        String url = context.get(URL).toString();
        return url;
    }

    private Headers buildHeaders() {
        Object header = context.get(HEADER);
        if(header==null){
            return Headers.of(Collections.emptyMap());
        }
        String head = header.toString();
        JSONObject headerJson = JSONObject.parseObject(head);
        return Headers.of((Map)headerJson);
    }

    enum HttpMethod{
        GET,POST;
    }

}
