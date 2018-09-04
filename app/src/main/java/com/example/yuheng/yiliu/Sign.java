package com.example.yuheng.yiliu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Sign extends AppCompatActivity implements View.OnClickListener {
    //application ID
    private static final String APPLICATION_ID = "16dfbb1af5c0a3b7c4df723cc61d3e16";
    private EditText phoneNumberEditText;           //手机号（帐号）输入框
    private EditText passwordEditText;              //密码输入框
    private String phoneNumber;                     //手机号（帐号）
    private String password;                        //密码
    private Button bt;
    private TextView tv;
    private litePalUtils litePalUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_in);
        super.onCreate(savedInstanceState);
        //初始化Bmob
        //Bmob.initialize(上下文，application id)
        Bmob.initialize(this,APPLICATION_ID);
        if (BmobUser.getCurrentUser() != null) {
            startActivity(new Intent(Sign.this, MainActivity.class));
            finish();
        }

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            layoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|layoutParams.flags);
        }
        bt = (Button) findViewById(R.id.sign_button);
        phoneNumberEditText = findViewById(R.id.phone_number_editText);
        passwordEditText = findViewById(R.id.password_editText);
        tv=(TextView)findViewById(R.id.tv);

        litePalUtils = new litePalUtils(this);
        bt.setOnClickListener(this);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.sign_button:
                login();
                break;
            case R.id.tv:
                Intent intent1= new Intent(Sign.this,Login.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
    private void login() {
        phoneNumber = phoneNumberEditText.getText().toString();
        password = passwordEditText.getText().toString();
        if(TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)){
            ToastUtils.showToast("帐号或密码不能为空");
            return;
        }
        boolean available = NetworkUtil.isNetworkAvailable(this);
        if (available){
            //根据用户名查询当前用户是否存在
            BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
            query.addWhereEqualTo("username",phoneNumber);
            query.findObjects(new FindListener<BmobUser>() {
                @Override
                public void done(List<BmobUser> list, BmobException e) {
                    //如果当前用户存在，进行登陆
                    if (e == null && list.size() != 0){
                        //登陆
                        final BmobUser user = new BmobUser();
                        user.setUsername(phoneNumber);
                        user.setPassword(password);
                        user.login(new SaveListener<BmobUser>() {
                            @Override
                            public void done(BmobUser bmobUser, BmobException e) {
                                if (e == null){
                                    startActivity(new Intent(Sign.this,MainActivity.class));
                                    litePalUtils = new litePalUtils();
                                    List<LitePalUser> litePalUserList = litePalUtils.findUser(phoneNumber);
                                    if (litePalUserList.size() == 0){
                                        litePalUtils.addUser(phoneNumber,password);
                                    }
                                    finish();
                                }else {
                                    ToastUtils.showToast("密码错误");
                                }
                            }
                        });
                    }else {
                        ToastUtils.showToast("当前用户不存在");
                    }
                }
            });
        }else {
            List<LitePalUser> litePalUser =  LitePal.where("username = ?",phoneNumber)
                    .find(LitePalUser.class);
            if (litePalUser.size() == 0){
                ToastUtils.showToast("请打开数据连接进行登陆");
                return;
            }
            if (password.equals(litePalUser.get(0).getPassword())){
                startActivity(new Intent(Sign.this,MainActivity.class));
                finish();
            }else {
                ToastUtils.showToast("密码错误");
            }
        }

    }
}
