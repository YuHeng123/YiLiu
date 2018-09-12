package com.example.yuheng.yiliu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class resetpasswords extends AppCompatActivity implements View.OnClickListener{
    private ImageView resetpassword_i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_resetpasswords );
        resetpassword_i =(ImageView)findViewById(R.id.reset_back);
        resetpassword_i.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.reset_back:
                Intent intent  =new Intent(resetpasswords.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }

    }
}
