package com.example.yangenneng0.myapplication.viewUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.yangenneng0.myapplication.MainActivity;
import com.example.yangenneng0.myapplication.R;
import com.example.yangenneng0.myapplication.adapter.ChatMsgViewAdapter;
import com.example.yangenneng0.myapplication.model.ChatMsgEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: yangenneng
 * DateTime: 2016/12/11 17:11
 * Description:
 */
public class ChatMainActivity extends Activity implements View.OnClickListener {

    private Button mBtnSend;            // 发送btn
    private Button mBtnBack;            // 返回btn
    private EditText mEditTextContent;  // 内容框
    private ListView mListView;         // 聊天记录列表
    private ChatMsgViewAdapter mAdapter;// 聊天记录视图的Adapter
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 聊天记录对象数组


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmain);

        initView();// 初始化view
        initData();// 初始化数据
        mListView.setSelection(mAdapter.getCount() - 1);

    }

    /**
     * 初始化view
     * 找出页面的控件
     */
    public void initView() {
        mListView= (ListView) findViewById(R.id.listview);
        mBtnSend=(Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mBtnBack = (Button) findViewById(R.id.chatGoHome);
        mBtnBack.setOnClickListener(this);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    }

    //消息数组
    private String[] msgArray = new String[] { "在吗，有时间吗?", "有！你呢？", "我也有", "那走吧",
            "走啊！去打球啊！", "要先吃点东西吗？", "不吃了", "好吧...",
            "今晚去吃东西吧？", "确定吗？", "不然呢？", "OK,走起！！" };
    //时间数组
    private String[] dataArray = new String[] { "2016-12-11 18:00:02",
            "2016-12-11 18:10:22", "2016-12-11 18:11:24",
            "2016-12-11 18:20:23", "2016-12-11 18:30:31",
            "2016-12-11 18:35:37", "2016-12-11 18:40:13",
            "2016-12-11 18:50:26", "2016-12-11 18:52:57",
            "2016-12-11 18:55:11", "2016-12-11 18:56:45",
            "2016-12-11 18:57:33", };
    //数组总数
    private final static int COUNT = 12;

    /**
     * 模拟加载消息历史，实际开发可以从数据库中读出
     */
    public void initData() {
        for (int i = 0; i < COUNT; i++) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setDate(dataArray[i]);
            if (i % 2 == 0) {
                entity.setName("CCC");   //设置对方姓名
                entity.setMsgType(true); // 收到的消息
            } else {
                entity.setName("YEN");   //设置自己姓名
                entity.setMsgType(false);// 发送的消息
            }
            entity.setMessage(msgArray[i]);//消息内容
            mDataArrays.add(entity);
        }

        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:// 发送按钮点击事件
                send();
                break;
            case R.id.chatGoHome:// 返回按钮点击事件
                Intent intent = new Intent();
                intent.setClass(ChatMainActivity.this, MainActivity.class);
                ChatMainActivity.this.startActivity(intent);
                break;
        }
    }


    /**
     * 发送消息
     */
    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            ChatMsgEntity entity = new ChatMsgEntity();
            entity.setName("YEN");      //设置发送消息消息者姓名
            entity.setDate(getDate());  //设置格式化的发送时间
            entity.setMessage(contString); //设置发送内容
            entity.setMsgType(false);      //设置消息类型，true 接受的 false发送的

            mDataArrays.add(entity);
            mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

            mEditTextContent.setText("");// 清空编辑框数据
            mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项

        }
    }


    /**
     * 发送消息时，获取当前事件
     * @return 当前时间
     */
    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

}
