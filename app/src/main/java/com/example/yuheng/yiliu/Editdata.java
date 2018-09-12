package com.example.yuheng.yiliu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Editdata extends AppCompatActivity implements View.OnClickListener{
    ImageView editdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_editdata );
        editdata =(ImageView)findViewById(R.id.editdata_back);
        editdata.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.editdata_back:
                Intent intent =new Intent(Editdata.this,MainActivity.class);
                startActivity(intent);
                finish();
        }
    }
}
