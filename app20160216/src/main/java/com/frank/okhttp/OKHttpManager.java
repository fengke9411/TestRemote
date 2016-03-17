package com.frank.okhttp;


import android.os.Handler;
import android.os.Looper;

import com.frank.utils.JSONUtil;
import com.frank.utils.Utils;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by frank on 2016/2/29.
 */
public class OKHttpManager {

    static OkHttpClient mClient = new OkHttpClient();
    static Handler mainHandler = new Handler(Looper.getMainLooper());

    static {
    }

    static MediaType MEDIA_JSON = MediaType.parse("application/json;charset=utf-8");  //json

    public interface OnOkHttpListener{
       void  onSuccess(JSONObject jsonObject);
        void onFailed(JSONObject jsonObject);
    }


    public static void postRequest(String url,JSONObject jsonObject, final OnOkHttpListener listener){
        RequestBody body = RequestBody.create(MEDIA_JSON, jsonObject.toString());
        Request request =new Request.Builder().url(url).post(body).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Utils.Logd(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();

                    final JSONObject object = JSONUtil.getJSONObject(result);
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                                listener.onSuccess(object);
                        }
                    });

            }
        });
    }

    /**
     * get请求
     * @param url
     * @param listener
     */
    public static void getRequest(String url, final OnOkHttpListener listener){
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Utils.Logd(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Utils.Logd("onResponse result:"+result);
                final JSONObject object = JSONUtil.getJSONObject(result);
                Utils.Logd("onResponse:"+object.toString());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSuccess(object);
                    }
                });
            }
        });
    }

}
