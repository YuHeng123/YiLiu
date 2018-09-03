package com.example.yuheng.yiliu;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private Button bt1;
    private TextView tv_yzm;
    private EditText phoneNumberEditText;           //手机号（帐号）输入框
    private EditText codeEditText;                  //验证码输入框
    private EditText passwordEditText;              //密码输入框
    private String phoneNumber;                     //手机号（帐号）
    private String code;                            //验证码
    private String password;                        //密码
    private litePalUtils litePalUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_in_enter);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
        }
        bt1 = (Button) findViewById(R.id.login_button);
        codeEditText = findViewById(R.id.code_editText);
        phoneNumberEditText = findViewById(R.id.phone_number_editText);
        passwordEditText = findViewById(R.id.password_editText);
        litePalUtils = new litePalUtils(this);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordEditText.getText().toString();
                code = codeEditText.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.showToast("请输入密码");
                    return;
                }
                //注册
                BmobUser user = new BmobUser();
                user.setUsername(phoneNumber);
                user.setPassword(password);
                user.setMobilePhoneNumber(phoneNumber);
                //signOrLogin(验证码，Listener)
                user.signOrLogin(code, new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e == null) {
                            litePalUtils.addUser(phoneNumber,password);
                            ToastUtils.showToast("注册成功");
                            //成功后，直接跳转到登录界面
                        Intent intent = new Intent(Login.this, Sign.class);
                        startActivity(intent);
                        finish();
            }else {
                            ToastUtils.showToast("注册失败（RegisterActivity.register）：" +
                                    e.toString());
                        }
                    }
                });
            }
        });
        tv_yzm = (TextView) findViewById(R.id.verification_code);
        tv_yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phoneNumberEditText.getText().toString();
                //检查手机号是否正确
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                if (!PhoneUtil.isLegal(phoneNumber)) {
                    ToastUtils.showToast("手机号不合法");
                    return;
                }
                if (!NetworkUtil.isNetworkAvailable(Login.this)) {
                    ToastUtils.showToast("请打开数据连接");
                    return;
                }
                //检验是否已有该账号
                BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
                //query.addWhereEqualTo（列名，需要查找的值）
                query.addWhereEqualTo("username", phoneNumber);
                query.findObjects(new FindListener<BmobUser>() {
                    @Override
                    public void done(List<BmobUser> list, BmobException e) {
                        //list.size()不为0，表示该用户已存在
                        if (e == null && list.size() != 0) {
                            ToastUtils.showToast("该用户已存在");
                        } else if (e == null && list.size() == 0) {
                            //list.size()为0，表示该用户不存在，可以注册，发送注册验证码
                            //BmobSMS.requestSMSCode(手机号，短信模版，Listener)
                            BmobSMS.requestSMSCode(phoneNumber, "BC", new QueryListener<Integer>() {
                                @Override
                                public void done(Integer integer, BmobException e) {
                                    if (e == null) {
                                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tv_yzm, 60000, 1000);
                                        mCountDownTimerUtils.start();
                                    } else {
                                        ToastUtils.showToast("验证码发送失败（RegisterActivity.sendCode）："
                                                + e.toString());
                                    }
                                }
                            });
                        } else {
                            ToastUtils.showToast("查询错误（RegisterActivity.sendCode）：" +
                                    e.toString());
                        }
                    }
                });
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








