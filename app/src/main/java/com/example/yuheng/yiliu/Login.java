package com.example.yuheng.yiliu;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
public class Login extends AppCompatActivity{
    private Button bt1;
    private TextView tv_yzm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_in_enter);
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            layoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|layoutParams.flags);
        }
        bt1 = (Button) findViewById(R.id.login_button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_yzm = (TextView) findViewById(R.id.verification_code);
        tv_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tv_yzm, 60000, 1000);
                mCountDownTimerUtils.start();
            }
        });
    }

    public class CountDownTimerUtils extends CountDownTimer {
        private TextView verification_code;

        public CountDownTimerUtils(TextView textView, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.verification_code = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            verification_code.setClickable(false); //设置不可点击
            verification_code.setText(millisUntilFinished / 1000 + "秒后可重新发送");  //设置倒计时时间
            SpannableString spannableString = new SpannableString(verification_code.getText().toString());
            ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
            spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            verification_code.setText(spannableString);
        }

        @Override
        public void onFinish() {
            verification_code.setText("重新获取验证码");
            verification_code.setClickable(true);//重新获得点击
        }
    }
}








