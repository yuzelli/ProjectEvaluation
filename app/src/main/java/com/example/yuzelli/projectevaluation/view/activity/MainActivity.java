package com.example.yuzelli.projectevaluation.view.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.UserInfo;
import com.example.yuzelli.projectevaluation.https.OkHttpClientManager;
import com.example.yuzelli.projectevaluation.uitls.LoginUtils;
import com.example.yuzelli.projectevaluation.uitls.SharePreferencesUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class MainActivity extends BaseActivity  {


    @Override
    protected int layoutInit() {
        return R.layout.activity_main;
    }

    @Override
    protected void binEvent() {

    }


}
