package com.example.yuzelli.projectevaluation.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.yuzelli.projectevaluation.uitls.MYToast;


public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // 隐藏actionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutInit());

        binEvent();
    }
    protected abstract int layoutInit();
    protected abstract void binEvent();
    public void showToast(String msg) {
        MYToast.show(msg);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
