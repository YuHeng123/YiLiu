package com.example.yuheng.yiliu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;


import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText textView;
    private ToggleButton lock;
    private Calendar Calendar1;
    private EditText local;
    private int year;
    private int month;
    private int day;
    private int hours;
    private int minute;
    private EditText date;
    private boolean aBoolean=true;
    private List<LocalMedia> selectList =new ArrayList<>();
    private RecyclerView  mRecyclerView;
    private GridImageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("创建时光印记");
        setContentView(R.layout.activity_add);


        local = (EditText)findViewById(R.id.address);
        date = (EditText)findViewById(R.id.dd);
        lock = (ToggleButton) findViewById(R.id.lock);
        ImageButton local = (ImageButton) findViewById(R.id.local);
        ImageButton calendar = (ImageButton) findViewById(R.id.calendar);
        ImageButton photo = (ImageButton) findViewById(R.id.photo);
        ImageButton video = (ImageButton) findViewById(R.id.video);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        textView= (EditText)findViewById(R.id.et_context);
        initWidget();



        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();



        Calendar1=Calendar.getInstance();
        year =Calendar1.get(Calendar.YEAR);
        month =Calendar1.get(Calendar.MONTH);
        day =Calendar1.get(Calendar.DAY_OF_MONTH);
        hours = Calendar1.get(Calendar.HOUR);
        minute = Calendar1.get(Calendar.MINUTE);
        lock.setOnClickListener(this);
        local.setOnClickListener(this);
        calendar.setOnClickListener(this);
        photo.setOnClickListener(this);
        video.setOnClickListener(this);

    }
    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this);
        adapter.setList(selectList);
        adapter.setSelectMax(9);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AddActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                //定位
                case R.id.local:
                    Intent intent = getIntent();
                    String address = intent.getStringExtra("address");
                    local.setText(address);
                    break;
                //日期
                case R.id.calendar:
                    date.setText(year + "-" + month + "-" + day + "  " + hours +  ":" + minute);

                    break;
                //图片
                case R.id.photo:
                    PictureSelector.create(AddActivity.this)
                            .openGallery(PictureMimeType.ofAll())
                            .maxSelectNum(9)// 最大图片选择数量
                            .imageSpanCount(3)//每行显示个数
                            .selectionMode(PictureConfig.MULTIPLE)//多选
                            .previewImage(true)// 是否可预览图片
                            .isCamera(true)// 是否显示拍照按钮
                            .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


                    break;
                //视频
                case R.id.video:
                    PictureSelector.create(AddActivity.this)
                            .openGallery(PictureMimeType.ofVideo())
                            .imageSpanCount(3)
                            .selectionMode(PictureConfig.MULTIPLE)
                            .previewVideo(true)// 是否可预览视频
                            .isZoomAnim(true)
                            .compress(true)
                            .isGif(true)
                            .videoQuality(1)// 视频录制质量 0 or 1 int
                            .videoMaxSecond(60)
                            .recordVideoSecond(60)//视频秒数录制 默认 60s int
                            .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调 onActivityResult code

                    break;

                case R.id.lock:
                    lock.setSelected(aBoolean);
                    aBoolean = !aBoolean;


            }
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调

                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);

//                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }





    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save://监听保存的菜单按钮
                Toast.makeText(getApplicationContext(), "发表成功", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }
}


