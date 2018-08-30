package com.example.yuheng.yiliu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Sign extends AppCompatActivity implements View.OnClickListener {

    private Button bt;
    private Button fbt;
    private TextView tv;
    private ImageView head1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_in);
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            layoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|layoutParams.flags);
        }
        bt = (Button) findViewById(R.id.sign_button);
        bt.setOnClickListener(this);
        tv=(TextView)findViewById(R.id.tv);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sign_button:
                Intent intent = new Intent(Sign.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv:
                Intent intent1= new Intent(Sign.this,Login.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
