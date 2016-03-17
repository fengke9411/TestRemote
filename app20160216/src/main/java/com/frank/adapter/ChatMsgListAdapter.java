package com.frank.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.example.frank.myapplication.R;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by frank on 2016/2/19.
 */
public class ChatMsgListAdapter extends BaseAdapter {

    private Context context;

    private EMConversation conversation;
    EMMessage[] messages = null;

    private LayoutInflater inflater;

    public ChatMsgListAdapter(Context context, EMMessage[] messages) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.messages = messages;
    }


    public void refreshList(String username) {
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        messages = (EMMessage[]) conversation.getAllMessages().toArray(new EMMessage[conversation.getAllMessages().size()]);
        notifyDataSetChanged();
    }

    public void setMessages(EMMessage[] messages) {

        this.messages = messages;
        for (int i = 0; i < messages.length; i++) {
            Log.i("Mesg", this.messages[i].toString());
        }

        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return messages == null ? 0 : messages.length;
    }

    @Override
    public Object getItem(int position) {

//        if (messages != null && position < messages.length) {
//            return messages[position];
//        }
        return messages[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return messages[position].direct== EMMessage.Direct.SEND?0:1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final EMMessage message = messages[position];
//        message.direct
//message.getType()
        ViewHolder viewHolder;

//        Log.i("Chat", "message=" + message.getBody());
        Log.d("messages", position+"message.direct=" + message.direct + "  messageisUnread=" + message.isUnread() + "  messagegetFrom=" + message.getFrom()+ "  messageBody=" + message.getBody());
        if (convertView == null) {
            viewHolder = new ViewHolder();
            viewHolder.id = position;
            convertView = createViewByMsg(message);
            Log.i("Chat", "convertView=" + message.getBody());
            try{

            viewHolder.headview = (ImageView) convertView.findViewById(R.id.headview);
            viewHolder.msgbody = (TextView) convertView.findViewById(R.id.msgbody);
            viewHolder.msgStatus = (TextView) convertView.findViewById(R.id.msgStatus);
            viewHolder.sendStatus = (ProgressBar) convertView.findViewById(R.id.sendStatus);
            viewHolder.timeStmap = (TextView) convertView.findViewById(R.id.timeStamp);

            convertView.setTag(viewHolder);
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            Log.i("viewHolder", "viewHolderid=" + viewHolder.id);

        }

        String msgtime ="";
        if (position>0){
            msgtime = getMsgTime(message,position);
        }else {
            msgtime = new Date(message.getMsgTime()).toString();
        }

        if (msgtime!=""){

            viewHolder.timeStmap.setVisibility(View.VISIBLE);
            viewHolder.timeStmap.setText(msgtime);
        }else{
            viewHolder.timeStmap.setVisibility(View.GONE);
        }


        //标记消息阅读状态
   /*     if (message.direct== EMMessage.Direct.SEND){
//            Log.i("SEND","发送状态："+message.isUnread());
            if(message.isUnread()){
                viewHolder.msgStatus.setText("未读");

            }else {
                viewHolder.msgStatus.setText("已读");
            }
        }else {
            viewHolder.msgStatus.setVisibility(View.GONE);
            viewHolder.sendStatus.setVisibility(View.GONE);
        }*/
        viewHolder.msgStatus.setVisibility(View.GONE);
        viewHolder.sendStatus.setVisibility(View.GONE);

        TextMessageBody body = (TextMessageBody) message.getBody();
        viewHolder.msgbody.setText(body.getMessage());

        final View view = viewHolder.msgbody;

        viewHolder.msgbody.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                showLongPressWds(view, message);


                return false;
            }
        });

        return convertView;
    }


    private View createViewByMsg(EMMessage msg) {
//        Log.i("Chat", "message=" + (msg.direct == EMMessage.Direct.RECEIVE));
        return msg.direct == EMMessage.Direct.RECEIVE ? inflater.inflate(R.layout.item_othermsg, null) : inflater.inflate(R.layout.item_mymsg, null);

    }


    class ViewHolder {
        int id;

        ImageView headview;
        TextView msgbody;
        TextView timeStmap;
        TextView msgStatus;
        ProgressBar sendStatus;
    }


    private void showLongPressWds(View pressView, final EMMessage msg) {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_pressmsg, null);
        final TextView deleteMsg = (TextView) view.findViewById(R.id.deleteMsg);


        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAsDropDown(pressView);
        deleteMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMConversation conversation =null;
                if (msg.direct== EMMessage.Direct.SEND){
                    conversation = EMChatManager.getInstance().getConversation(msg.getTo());
                }else {

                    conversation = EMChatManager.getInstance().getConversation(msg.getFrom());
                }
                conversation.removeMessage(msg.getMsgId());
                messages = (EMMessage[]) conversation.getAllMessages().toArray(new EMMessage[conversation.getAllMessages().size()]);
                notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 计算Msg显示的时间
     * @param message
     * @param pos
     * @return
     */
    private String getMsgTime(EMMessage message,int pos){
        String result="";

        EMMessage messageup = messages[pos-1];
        long time = message.getMsgTime();

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);

//        Log.i("SEND", "消息时间：" + time+"  上一条消息："+messageup.getMsgTime());

        if((time-messageup.getMsgTime())>5*60*1000){
            result =calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
        }

//        Log.i("SEND", "消息时间 result：" + result);
        return result;

    }

}
