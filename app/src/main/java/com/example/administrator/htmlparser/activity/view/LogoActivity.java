package com.example.administrator.htmlparser.activity.view;

import android.os.Bundle;

import com.example.administrator.htmlparser.activity.BaseActivity;
import com.example.administrator.htmlparser.MainActivity;
import com.example.administrator.htmlparser.R;

/**
 * app进入界面
 */

public class LogoActivity extends BaseActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_logo);
        new Thread(){
            @Override
            public void run() {
                try{
                    //设置线程睡眠3秒
                    sleep(2000);
                    startActivity(MainActivity.class);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {

                }
                super.run();
            }
        }.start();
    }
}
