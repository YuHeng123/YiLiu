package com.example.yuheng.yiliu;

import android.content.Context;

import org.litepal.LitePal;

import java.util.List;

public class litePalUtils {

        private Context context;

        public litePalUtils() {

        }

    public litePalUtils(Context context) {
        this.context = context;
    }
    public List<LitePalUser> findUser(String phoneNumber) {
        List<LitePalUser> list = LitePal.where("username = ?", phoneNumber)
                .find(LitePalUser.class);
        return list;
    }
    public void addUser(String phoneNumber, String password) {
        LitePalUser user = new LitePalUser();
        user.setUsername(phoneNumber);
        user.setPassword(password);
        user.save();
    }

}
