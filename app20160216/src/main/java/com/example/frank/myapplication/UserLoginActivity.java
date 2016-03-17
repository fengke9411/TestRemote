package com.example.frank.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.frank.okhttp.OKHttpManager;
import com.frank.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by frank on 2016/2/19.
 */
public class UserLoginActivity extends Activity implements View.OnClickListener{

    private EditText userName,userPwd;

    private Button loginSubmit,registerBtn;

    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin_layout);

        userName = (EditText) findViewById(R.id.userName);
        userPwd = (EditText) findViewById(R.id.userPwd);
        loginSubmit = (Button) findViewById(R.id.loginSubmit);
        registerBtn = (Button) findViewById(R.id.registerSubmit);
//        back = (TextView) findViewById(R.id.back);
        loginSubmit.setOnClickListener(this);
        registerBtn.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginSubmit:
                startActivity(new Intent(UserLoginActivity.this, MainActivity.class));

                finish();

//                login();    //登陆


                break;
            case R.id.registerSubmit:

                startActivity(new Intent(this, UserRegisterActivity.class));
//                finish();
                break;


        }
    }

    /**
     * 登陆服务器
     */
    private void login(){



        String username = userName.getText().toString();
        String userPaswd = userPwd.getText().toString();
        Utils.Logd("登陆：UserName:" + username + "       UserPwd:" + userPaswd);
        final ProgressDialog pd = new ProgressDialog(this);

        pd.setMessage("Loading...");
        pd.show();

        //192.168.1.107
        String url = "http://192.168.155.10:8080/iuway/login.do";
//        OKHttpManager.getRequest(url, new OKHttpManager.OnOkHttpListener() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                Toast.makeText(UserLoginActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailed(JSONObject jsonObject) {
//
//            }
//        });

        JSONObject user = new JSONObject();

        try {
            user.put("account","11111111");
            user.put("password","11111111");
        } catch (JSONException e) {
            e.printStackTrace();
        }



//        OKHttpManager.postRequest(url, user, new OKHttpManager.OnOkHttpListener() {
//            @Override
//            public void onSuccess(JSONObject jsonObject) {
//                Utils.Logd("登陆结果："+jsonObject);
//            }
//
//            @Override
//            public void onFailed(JSONObject jsonObject) {
//                Utils.Logd("登陆结果失败："+jsonObject);
//            }
//        });

        EMChatManager.getInstance().login(username,userPaswd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        EMGroupManager.getInstance().loadAllGroups();
                        EMChatManager.getInstance().loadAllConversations();

                        pd.dismiss();
                        Utils.Logd("登陆聊天服务器成功！");
                        Toast.makeText(UserLoginActivity.this,"登陆成功！",Toast.LENGTH_LONG).show();

                        startActivity(new Intent(UserLoginActivity.this, MainActivity.class));

                        finish();


                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                pd.dismiss();
                Toast.makeText(UserLoginActivity.this,"登陆失败！",Toast.LENGTH_LONG).show();

                Utils.Logd("登陆聊天服务器失败！");
            }
        });

    }
}
