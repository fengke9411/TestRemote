package com.example.frank.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;
import com.frank.utils.Utils;

/**
 * 用户注册
 * Created by frank on 2016/2/19.
 */
public class UserRegisterActivity extends Activity implements View.OnClickListener {


    private EditText userName, userPwd;

    private Button registerBtn;

    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userregister_layout);
        userName = (EditText) findViewById(R.id.userName);
        userPwd = (EditText) findViewById(R.id.userPwd);
        registerBtn = (Button) findViewById(R.id.registerSubmit);
        back = (TextView) findViewById(R.id.back);
        registerBtn.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.registerSubmit:
                register();

                break;

            case R.id.back:

                finish();
                break;
        }
    }


    /**
     * 注册请求
     */

    private void register() {

        final String username = userName.getText().toString();
        final String userPaswd = userPwd.getText().toString();
        Utils.Logd("注册 ：UserName:" + username + "       UserPwd:" + userPaswd);
        final ProgressDialog dp = new ProgressDialog(this);
        dp.setMessage("Loading...");

        dp.show();




        new Thread(){
            @Override
            public void run() {

                Looper.prepare();
                try {
                    // 调用sdk注册方法
                    EMChatManager.getInstance().createAccountOnServer(username, userPaswd);
                    dp.dismiss();
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();

                    setResult(RESULT_OK);
                    finish();

                } catch (final EaseMobException e) {

                    //注册失败
                    int errorCode = e.getErrorCode();
                    Utils.Logd("注册结果 ：" + e.getErrorCode());

                    if (errorCode == EMError.NONETWORK_ERROR) {
                        Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
                    } else if (errorCode == EMError.USER_ALREADY_EXISTS) {
                        Toast.makeText(getApplicationContext(), "用户已存在！", Toast.LENGTH_SHORT).show();
                    } else if (errorCode == EMError.UNAUTHORIZED) {
                        Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Utils.Logd("注册失败 ：" + e.getMessage());
                    }
                }
            }
        }.start();

//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    // 调用sdk注册方法
//                    EMChatManager.getInstance().createAccountOnServer(username, userPaswd);
//                } catch (final EaseMobException e) {
//                    //注册失败
//                    int errorCode = e.getErrorCode();
//                    if (errorCode == EMError.NONETWORK_ERROR) {
//                        Toast.makeText(getApplicationContext(), "网络异常，请检查网络！", Toast.LENGTH_SHORT).show();
//                    } else if (errorCode == EMError.USER_ALREADY_EXISTS) {
//                        Toast.makeText(getApplicationContext(), "用户已存在！", Toast.LENGTH_SHORT).show();
//                    } else if (errorCode == EMError.UNAUTHORIZED) {
//                        Toast.makeText(getApplicationContext(), "注册失败，无权限！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "注册失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//        ).start();

    }
}
