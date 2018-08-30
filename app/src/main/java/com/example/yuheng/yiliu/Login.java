package com.example.yuheng.yiliu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressLint("SdCardPath")
public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button bt1;
    private ImageButton btnTakephoto;//拍照
    private ImageButton btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径
    private ImageView head_login;
    private MyPopupWindow myPopupWindow;
    private View popupView;
    private  ImageView cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_in_enter);
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            layoutParams.flags=(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|layoutParams.flags);
        }
        popupView = View.inflate(this, R.layout.popup_view,null);
        myPopupWindow = new MyPopupWindow(Login.this,popupView,findViewById(R.id.image_sign9957));
        bt1 = (Button) findViewById(R.id.login_button);
        bt1.setOnClickListener(this);
        head_login = (ImageView)findViewById(R.id.image_sign9957);
        head_login.setOnClickListener(this);
        cancel = popupView.findViewById(R.id.cancel_imageButton);
        cancel.setOnClickListener(this);
        initView();

    }

    private void initView() {

        btnPhotos = popupView.findViewById(R.id.manual_add_imageButton);
        btnTakephoto = popupView.findViewById(R.id.photo_scanning_imageButton);
        btnPhotos.setOnClickListener(this);
        btnTakephoto.setOnClickListener(this);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            head_login.setImageDrawable(drawable);
        }else{
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.image_sign9957:
                myPopupWindow.popup();
                break;
            case  R.id.cancel_imageButton:
                myPopupWindow.popupWindow.dismiss();
                myPopupWindow.lightOn();
                break;
            case R.id.manual_add_imageButton://从相册里面取照片
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                break;
            case R.id.photo_scanning_imageButton://调用相机拍照
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));
                startActivityForResult(intent2, 2);//采用ForResult打开
                break;

            default:
                   break;

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if(head!=null){
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        head_login.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    };
    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}








