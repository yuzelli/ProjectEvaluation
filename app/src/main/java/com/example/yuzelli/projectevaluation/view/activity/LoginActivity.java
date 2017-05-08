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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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

public class LoginActivity extends BaseActivity {

    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    private EditText et_userPhone;   //用户账号
    private EditText et_passWord;   //用户密码
    private Button btn_login;      //登录按钮
    private RelativeLayout activity_login;  //根布局
    private Context context;
    private UserInfo userInfo;
    private LoginHandler handler;
    private boolean selectUserType = false;

    @Override
    protected int layoutInit() {
        return R.layout.activity_login;
    }

    @Override
    protected void binEvent() {
        context = this;
        handler = new LoginHandler();
        userInfo = null;
        et_userPhone = (EditText) this.findViewById(R.id.et_userPhone);
        et_passWord = (EditText) this.findViewById(R.id.et_passWord);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        radioButton0 = (RadioButton) this.findViewById(R.id.radio0);
        radioButton1 = (RadioButton) this.findViewById(R.id.radio1);
        radioButton2 = (RadioButton) this.findViewById(R.id.radio2);

        activity_login = (RelativeLayout) this.findViewById(R.id.activity_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectUserType) {
                    doLogin();
                } else {
                    showToast("请选择用户类别！");
                }
            }
        });
        activity_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                return imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    private void doLogin() {
        String userPhone = et_userPhone.getText().toString().trim();
        String passWord = et_passWord.getText().toString().trim();
        if (!LoginUtils.isPhoneEnable(userPhone)) {
            et_userPhone.setText("");
            return;
        }
        if (passWord == null || passWord.equals("")) {
            showToast("请填写密码！");
        }

        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.USER_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "login");
        map.put("u_phone", userPhone);
        map.put("u_password", passWord);
        String url = OkHttpClientManager.attachHttpGetParams(buffer.toString(), map);
        manager.getAsync(url, new OkHttpClientManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                showToast("加载网路数据失败！");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                JSONObject object = new JSONObject(result);
                String flag = object.getString("error");
                if (flag.equals("ok")) {
                    String body = object.getString("object");
                    userInfo = gson.fromJson(body, UserInfo.class);
                    handler.sendEmptyMessage(ConstantUtils.LOGIN_GET_DATA);
                } else {
                    showToast("用户名或密码错误！");
                }
            }
        });
    }

    class LoginHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.LOGIN_GET_DATA:
                    SharePreferencesUtil.saveObject(context, ConstantUtils.USER_LOGIN_INFO, userInfo);
                    ConstantUtils.USER_TYPE = userInfo.getU_type();
                    if (ConstantUtils.USER_TYPE == 1) {
                       UserTypeOneActivity.actionStart(context);
                    } else if (ConstantUtils.USER_TYPE == 2) {
                        UserTypeTwoActivity.actionStart(context);
                    } else if (ConstantUtils.USER_TYPE == 3) {
                        ProjectListActivity.actionStart(context);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        switch (view.getId()) {
            case R.id.radio0:
                if (isChecked) {
                    et_userPhone.setText("13133440001");
                    et_passWord.setText("123456");
                    ConstantUtils.USER_TYPE = 1;
                    selectUserType = true;
                }
                break;
            case R.id.radio1:
                if (isChecked) {
                    et_userPhone.setText("13133440002");
                    et_passWord.setText("123456");
                    ConstantUtils.USER_TYPE = 2;
                    selectUserType = true;
                }
                break;
            case R.id.radio2:
                if (isChecked) {
                    et_userPhone.setText("13133440003");
                    et_passWord.setText("123456");
                    ConstantUtils.USER_TYPE = 3;
                    selectUserType = true;
                }


                break;

        }
    }


}
