package com.example.yuzelli.projectevaluation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.UserInfo;
import com.example.yuzelli.projectevaluation.uitls.SharePreferencesUtil;

public class UserTypeOneActivity extends BaseActivity {
    String userType [] = {"一类用户","二类用户","三类用户"};
   private Context context;

    @Override
    protected int layoutInit() {
        return R.layout.activity_user_type_one;
    }

    @Override
    protected void binEvent() {
        context = this;
        UserInfo userInfo = (UserInfo) SharePreferencesUtil.readObject(this, ConstantUtils.USER_LOGIN_INFO);
        TextView tv_name = (TextView) this.findViewById(R.id.tv_name);
        TextView tv_user_type = (TextView) this.findViewById(R.id.tv_user_type);
        tv_name.setText("用户名："+userInfo.getU_name());
        int userMode =ConstantUtils.USER_TYPE;
        if (userMode==1){
            tv_user_type.setText("用户类别："+userType[0]);
        }else if (userMode==2){
            tv_user_type.setText("用户类别："+userType[1]);
        }else if (userMode == 3){
            tv_user_type.setText("用户类别："+userType[2]);
        }

        this.findViewById(R.id.tv_addProject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProjectActivity.actionStart(context);
            }
        });

        this.findViewById(R.id.tv_queryProject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     ProjectListActivity.actionStart(context);
            }
        });



    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UserTypeOneActivity.class);
        context.startActivity(intent);
    }
}
