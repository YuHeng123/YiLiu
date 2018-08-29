package com.example.yuheng.yiliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        bt = (Button) findViewById(R.id.sign_button);
        bt.setOnClickListener(this);
        head1 =(ImageView)findViewById(R.id.image_sign);
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
