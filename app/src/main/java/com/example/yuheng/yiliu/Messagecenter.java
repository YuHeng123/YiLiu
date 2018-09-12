package com.example.yuheng.yiliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by 29673 on 2018/8/29.
 */

public class Messagecenter extends AppCompatActivity implements View.OnClickListener{
    private ListView message_listview;
    private ImageView message_back;
    private ImageView message_head;
    private TextView message_textview;
    private String[] message_titles = {"消息中心", "编辑资料","我的关注","建议反馈", "重置密码", "版本更新", "关于我们"};
    private int[] message_imageId = {R.drawable.message_center, R.drawable.edit_data, R.drawable.my_attention,
            R.drawable.suggests, R.drawable.reset_password, R.drawable.updata, R.drawable.for_us};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagecenter);
        message_listview = (ListView)findViewById(R.id.message_listview);
        MyAdapter_Message myAdapter_message = new MyAdapter_Message();
        message_listview.setAdapter(myAdapter_message);
        message_back =(ImageView)findViewById(R.id.message_back);
        message_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.message_back:
                Intent intent =new Intent(Messagecenter.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    class MyAdapter_Message extends BaseAdapter{
        @Override
        public int getCount() {
            return message_titles.length;
        }

        @Override
        public Object getItem(int position) {
            return message_titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View v, ViewGroup viewGroup) {
            View view=View.inflate(getApplicationContext(),R.layout.message_item,null);
             message_head = (ImageView) view.findViewById(R.id.message_head);
             message_textview = (TextView) view.findViewById(R.id.message_textview);
            message_head.setBackgroundResource(message_imageId[position]);
            message_textview.setText(message_titles[position]);
            return view;
        }
    }
}