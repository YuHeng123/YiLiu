package com.example.yuheng.yiliu;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
/**
 * 自定义PopupWindow
 */
public class MyPopupWindow extends PopupWindow {

    private Activity activity;                  //上下文的活动
    public PopupWindow popupWindow;            //popupWindow
    private View popupView;                     //popupView的布局
    private TranslateAnimation animation;       //popupWindow动画
    private View flag;                          //获取window的标识控件

    /**
     * 构造器
     * @param activity  上下文活动
     * @param popupView popupWindow的布局
     */
    public MyPopupWindow(Activity activity,View popupView,View flag){
        this.activity = activity;
        this.popupView = popupView;
        this.flag = flag;
    }
    /**
     * 恢复屏幕亮度
     */
    public void lightOn() {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 调暗屏幕亮度
     */
    private void lightOff(){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * popupWindow弹出
     */
    public void popup(){
        if (popupWindow == null){
            //初始化
            //参数1：popupWindow的视图；
            //参数2：popupWindow的宽度；
            //参数3：popupWindow的高度；
            popupWindow = new PopupWindow(popupView,WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            //消失监听，popupWindow消失的时候，将恢复屏幕亮度。
            popupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    lightOn();
                }
            });
            //setBackgroundDrawable：可以实现点击popupWindow以外的区域时，隐藏popupWindow
            //以及响应back键。
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置动画格式
            popupWindow.setAnimationStyle(R.style.popupAnimation);
            //获取焦点
            popupWindow.setFocusable(true);
            //获取外部点击事件（点击外部popupWindow消失）。
            popupWindow.setOutsideTouchable(true);
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,0,
                    Animation.RELATIVE_TO_PARENT,0,
                    Animation.RELATIVE_TO_PARENT,1,
                    Animation.RELATIVE_TO_PARENT,0);
            animation.setInterpolator(new AccelerateInterpolator());
            //设置时长
            animation.setDuration(200);
        }
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
            lightOn();
        }
        //showAtLocation控制window显示位置
        //参数1：获取window的唯一标识；
        //参数2：出现位置；
        //参数3：方向偏移量。
        popupWindow.showAtLocation(flag,Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        lightOff();
        popupView.startAnimation(animation);
        popupWindow.update();
    }
}
