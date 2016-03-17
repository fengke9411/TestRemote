package com.example.frank.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;
import com.frank.adapter.ChatMsgListAdapter;

/**
 * Created by frank on 2016/2/19.
 */
public class ChatActivity extends Activity implements EMEventListener, View.OnClickListener {

    private ListView msgList;
    private EditText editMsg;
    private Button sendMsgBtn;
    private ChatMsgListAdapter chatMsgListAdapter;
    private EMConversation conversation;
    private EMMessage[] messages = null;
    private long messageLen = 0;
    private long messagePos = 0;
    private String recUser;
    private TextView chatuser;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chating_layout);

        recUser = getIntent().getStringExtra("RECUSER");
        msgList = (ListView) findViewById(R.id.msglist);
        editMsg = (EditText) findViewById(R.id.editmsg);
        sendMsgBtn = (Button) findViewById(R.id.sendMsg);
        chatuser = (TextView) findViewById(R.id.chatuser);
        chatuser.setText(recUser);
        EMChatManager.getInstance().registerEventListener(this);
        conversation = EMChatManager.getInstance().getConversation(recUser);
        messages = (EMMessage[]) conversation.getAllMessages().toArray(new EMMessage[conversation.getAllMessages().size()]);
        messageLen = messages.length - 1;
        chatMsgListAdapter = new ChatMsgListAdapter(this, messages);
        msgList.setAdapter(chatMsgListAdapter);
        msgList.setSelection(messages.length - 1);
        sendMsgBtn.setOnClickListener(this);


    }


    @Override
    public void onEvent(EMNotifierEvent emNotifierEvent) {
        switch (emNotifierEvent.getEvent()) {
            case EventNewMessage:       //收到新消息
                final EMMessage message = (EMMessage) emNotifierEvent.getData();


                String username = message.getFrom();   //发送人
                Log.i("Chat", "username=" + username + message.getBody().toString());
                if (username.equals(recUser)) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            message.setUnread(false);
                            refreshMsgList();
                        }
                    });
//                        chatMsgListAdapter.refreshList();   //收到新消息刷新消息列表
                }
                break;
        }

    }

    /**
     * 刷新消息列表
     */
    private void refreshMsgList() {

        messagePos = msgList.getSelectedItemPosition();

        messageLen = msgList.getCount();

        messages = (EMMessage[]) conversation.getAllMessages().toArray(new EMMessage[conversation.getAllMessages().size()]);
        for (int i = 0; i < messages.length; i++) {
            // getMessage will set message as read status
            conversation.getMessage(i);
        }

        chatMsgListAdapter.setMessages(messages);

        //设置消息定位
        if (messagePos == messageLen) {
            msgList.setSelection(messages.length - 1);
        }

        Log.d("POS", "messageLen=" + messageLen + "      messagePos=" + messagePos);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMsg:



                //创建一条文本消息,content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                String content = editMsg.getText().toString();
                EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
//                EMMessage message =EMMessage.createReceiveMessage(EMMessage.Type.TXT);

                TextMessageBody txtBody = new TextMessageBody(content);
                // 设置消息body
                message.addBody(txtBody);
                // 设置要发给谁,用户username或者群聊groupid
                message.setReceipt(recUser);
                // 把messgage加到conversation中
                conversation.addMessage(message);
//                Looper.prepare();
                EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(ChatActivity.this, "发送成功！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(ChatActivity.this, "发送失败！" + s, Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onProgress(int i, String s) {
                        Log.i("发送", "发送中...");
                    }
                });
                refreshMsgList();

                break;
        }
    }
}
