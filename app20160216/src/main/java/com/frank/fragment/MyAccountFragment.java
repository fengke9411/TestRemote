package com.frank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.frank.myapplication.R;
import com.frank.okhttp.OKHttpManager;
import com.frank.utils.JSONUtil;
import com.frank.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by frank on 2016/2/19.
 *
 * 心知天气
 * API密钥
 * 4TDO1726G2
 * 用户ID
 * U030508030
 *https://api.thinkpage.cn/v3/weather/now.json?key=4TDO1726G2&location=beijing&language=zh-Hans&unit=c
 * https://api.thinkpage.cn/v3/weather/daily.json?key=4TDO1726G2&location=beijing&language=zh-Hans&unit=c&start=-1&days=5
 */
public class MyAccountFragment extends Fragment implements AMapLocationListener {

    private View view;  //根视图
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;



    private TextView weather1,weather2;
    private  String weatherUrl = "https://api.thinkpage.cn/v3/weather/now.json?key=4TDO1726G2&location=beijing&language=zh-Hans&unit=c";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_myaccount, container, false);

        initView(view);

        initAMapLocation();

        return view;
    }




    private void initView(View view){
        weather1 = (TextView) view.findViewById(R.id.weather1);
        weather2 = (TextView) view.findViewById(R.id.weather2);

        OKHttpManager.getRequest(weatherUrl, new OKHttpManager.OnOkHttpListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Utils.Logd(jsonObject.toString());
                JSONArray result = JSONUtil.getJSONArray(jsonObject,"results");
                JSONObject object = JSONUtil.getJSONObject(result, 0);
                JSONObject nowJson = JSONUtil.getJSONObject(object, "now");
                String temp = JSONUtil.getString(nowJson, "temperature");
                String text  = JSONUtil.getString(nowJson,"text");
                String code  = JSONUtil.getString(nowJson,"code");
                weather1.setText(text+" "+temp);
                weather2.setText(text+" "+temp);
            }

            @Override
            public void onFailed(JSONObject jsonObject) {
                Utils.Logd(jsonObject.toString());
            }
        });
    }


    /**
     * 初始化定位
     */
    private void initAMapLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                String country = amapLocation.getCountry();//国家信息
                String province = amapLocation.getProvince();//省信息
                String city = amapLocation.getCity();//城市信息
                String district = amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码

                Utils.Logd("    country:" + country + "    province:" + province + "    city:" + city);
                mLocationClient.stopLocation();//停止定位
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();//销毁定位客户端
    }
}
