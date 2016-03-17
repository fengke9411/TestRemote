package com.frank.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.example.frank.myapplication.ChatActivity;
import com.example.frank.myapplication.R;
import com.frank.utils.Utils;

/**
 * Created by frank on 2016/2/19.
 */
public class ChatListFragment extends Fragment implements View.OnClickListener{

    private View view;
    private TextView chatTest,chat123,chat1234;
    private ImageView newMsg;
    private  NewMessageBroadcastReceiver msgReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_chatlist,container,false);

        init(view);

        return view;
    }



    private void init(View view){
        chatTest = (TextView) view.findViewById(R.id.chattest);
        chat123 = (TextView) view.findViewById(R.id.chat123);
        chat1234 = (TextView) view.findViewById(R.id.chat1234);
        newMsg = (ImageView) view.findViewById(R.id.newMsg);
        chatTest.setOnClickListener(this);
        chat123.setOnClickListener(this);
        chat1234.setOnClickListener(this);


        msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        getActivity().registerReceiver(msgReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),ChatActivity.class);


        switch (v.getId()){
            case R.id.chattest:
                intent.putExtra("RECUSER","admin");
                startActivity(intent);
                newMsg.setVisibility(View.INVISIBLE);
                break;
            case R.id.chat123:

                intent.putExtra("RECUSER","123");
                startActivity(intent);
                newMsg.setVisibility(View.INVISIBLE);
                break;
            case R.id.chat1234:

                intent.putExtra("RECUSER","1234");
                startActivity(intent);
                newMsg.setVisibility(View.INVISIBLE);
                break;

        }


    }


    private class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 注销广播
            abortBroadcast();

            // 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
            String msgId = intent.getStringExtra("msgid");
            //发送方
            String username = intent.getStringExtra("from");

            if (username.equals("admin")&&!newMsg.isShown()){
                newMsg.setVisibility(View.VISIBLE);
            }

            Utils.Logd("收到消息： msgId="+msgId+"   userName = "+username);
            // 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
            EMMessage message = EMChatManager.getInstance().getMessage(msgId);
            EMConversation conversation = EMChatManager.getInstance().getConversation(username);
            // 如果是群聊消息，获取到group id
            if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                username = message.getTo();
            }
            if (!username.equals(username)) {
                // 消息不是发给当前会话，return
                return;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(msgReceiver);
    }
}
