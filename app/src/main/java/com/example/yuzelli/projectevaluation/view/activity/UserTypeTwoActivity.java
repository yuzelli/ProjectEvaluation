package com.example.yuzelli.projectevaluation.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.Project;
import com.example.yuzelli.projectevaluation.entity.UserInfo;
import com.example.yuzelli.projectevaluation.https.OkHttpClientManager;
import com.example.yuzelli.projectevaluation.uitls.SharePreferencesUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class UserTypeTwoActivity extends BaseActivity {
    String userType[] = {"一类用户", "二类用户", "三类用户"};
    private Context context;
    private Project proj;
    private UserTypeTwoHandler handler;
private  Dialog dialog;
    @Override
    protected int layoutInit() {
        return R.layout.activity_user_type_two;
    }

    @Override
    protected void binEvent() {
        handler = new UserTypeTwoHandler();
        context = this;
        UserInfo userInfo = (UserInfo) SharePreferencesUtil.readObject(this, ConstantUtils.USER_LOGIN_INFO);
        TextView tv_name = (TextView) this.findViewById(R.id.tv_name);
        TextView tv_user_type = (TextView) this.findViewById(R.id.tv_user_type);
        tv_name.setText("用户名：" + userInfo.getU_name());
        int userMode = ConstantUtils.USER_TYPE;
        if (userMode == 1) {
            tv_user_type.setText("用户类别：" + userType[0]);
        } else if (userMode == 2) {
            tv_user_type.setText("用户类别：" + userType[1]);
        } else if (userMode == 3) {
            tv_user_type.setText("用户类别：" + userType[2]);
        }

        this.findViewById(R.id.tv_assessment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReducePriceDialog();
            }
        });

        this.findViewById(R.id.tv_queryProject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ProjectListActivity.actionStart(context);
            }
        });
    }

    private void showReducePriceDialog() {
         dialog = new Dialog(this, R.style.PhotoDialog);
        //使用xml文件定义视图
        int reduceXML;

        reduceXML = R.layout.view_get_project_name;

        final View view = LayoutInflater.from(UserTypeTwoActivity.this).inflate(reduceXML, null);
        view.findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_project_name = (EditText) view.findViewById(R.id.et_project_name);
                String input = et_project_name.getText().toString().trim();
                if (input == null || input.equals("")) {
                    showToast("请输入项目名称！");
                    return;
                }
                doGetProjectRequest(input);
            }
        });
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        //将对话框的大小按屏幕大小的百分比设置
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        p.height = WindowManager.LayoutParams.WRAP_CONTENT;

        p.width = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogWindow.setAttributes(p);

//        android Activity改成dialog样式后 怎设置点击空白处关闭窗体，如图点击窗体意外的地方关闭窗体
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void doGetProjectRequest(String input) {
        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.PROJECT_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "findProjectByName");
        map.put("p_name", input);
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
                    proj = gson.fromJson(body, Project.class);
                    handler.sendEmptyMessage(ConstantUtils.PROJECT_FIND_NAME_ADD_DATA);
                } else {
                    showToast("项目不存在！");
                }
            }
        });
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserTypeTwoActivity.class);
        context.startActivity(intent);
    }

    class UserTypeTwoHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.PROJECT_FIND_NAME_ADD_DATA:
                    if (proj != null && proj.getP_name() != null && proj.getP_name().length() > 0) {
                        dialog.dismiss();
                        AddDefectActivity.actionStart(context,proj,null);
                    } else {
                        showToast("项目不存在！");
                    }

                    break;
                default:
                    break;
            }
        }
    }
}
