package com.example.yuheng.yiliu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by 29673 on 2018/8/29.
 */

public class Messagecenter extends AppCompatActivity {
    private ListView messagecenter;
    private String[] communication = {"消息中心", "建议反馈", "编辑资料", "重置密码", "清理缓存", "版本更新", "关于我们", "退出"};
    private int[] headimage = {R.drawable.name6, R.drawable.name1, R.drawable.name7, R.drawable.name5, R.drawable.name2, R.drawable.name8, R.drawable.name3, R.drawable.name4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        messagecenter = (ListView)findViewById(R.id.mylistview);
        MyAdapter1 myAdapter =new MyAdapter1();
        messagecenter.setAdapter(myAdapter);
    }


    private class MyAdapter1 extends BaseAdapter{

        @Override
        public int getCount() {
            return communication.length;
        }

        @Override
        public Object getItem(int position) {
            return communication[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =View.inflate(getApplicationContext(), R.layout.left_list,null);
            ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            iv_photo.setBackgroundResource(headimage[position]);
            tv_title.setText(communication[position]);
            return view;
        }
    }
}