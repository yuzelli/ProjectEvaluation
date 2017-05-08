package com.example.yuzelli.projectevaluation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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

public class AddProjectActivity extends BaseActivity {

    private TextView tv_test;
    private TextView tv_milepost;
    private TextView tv_project;
    private TextView tv_ppqa;
    private TextView tv_finish;
    private EditText et_project_name;

    private ImageView img_one;
    private ImageView img_two;
    private ImageView img_three;
    private ImageView img_four;

    private PopupWindow mPopWindow;
    private Project project;
    private Project proj;
    private Context context;
    boolean flagPaths [] = {false,false,false,false};
    private AddProjectHandler handler;

    @Override
    protected int layoutInit() {
        return R.layout.activity_add_project;
    }

    @Override
    protected void binEvent() {
        project = new Project();
        handler = new AddProjectHandler();
        context = this;
        et_project_name = (EditText) this.findViewById(R.id.et_project_name);
        tv_test = (TextView) this.findViewById(R.id.tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagPaths[0]){
                    showToast("该项目已完成添加！");
                }else {
                    showPopupWindow(0);
                }

            }
        });
        tv_milepost = (TextView) this.findViewById(R.id.tv_milepost);
        tv_milepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagPaths[1]){
                    showToast("该项目已完成添加！");
                }else {
                    showPopupWindow(1);
                }
            }
        });
        tv_project = (TextView) this.findViewById(R.id.tv_project);
        tv_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagPaths[2]){
                    showToast("该项目已完成添加！");
                }else {
                    showPopupWindow(2);
                }
            }
        });
        tv_ppqa = (TextView) this.findViewById(R.id.tv_ppqa);
        tv_ppqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagPaths[3]){
                    showToast("该项目已完成添加！");
                }else {
                    showPopupWindow(3);
                }
            }
        });

        tv_finish = (TextView) this.findViewById(R.id.tv_finish);
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String project_name = et_project_name.getText().toString().trim();
                if (project_name==null||project_name.equals("")){
                    showToast("请输入项目名称");
                    return;
                }
                project.setP_name(project_name);
                for (int i = 0 ; i <flagPaths.length;i++){
                    if (flagPaths[i]==false){
                        showToast("请完成项目的填写");
                        return;
                    }
                }
                doRequestAddProject();

            }
        });

        img_one = (ImageView) this.findViewById(R.id.img_one);
        img_two = (ImageView) this.findViewById(R.id.img_two);
        img_three = (ImageView) this.findViewById(R.id.img_three);
        img_four = (ImageView) this.findViewById(R.id.img_four);
    }

    private void doRequestAddProject() {

        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.PROJECT_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "addProject");
        map.put("p_name", project.getP_name());
        map.put("p_td_probability", project.getP_td_probability());
        map.put("p_td_serious", project.getP_td_serious());
        map.put("p_td_time", project.getP_td_time());
        map.put("p_td_min", project.getP_td_min());
        map.put("p_td_max", project.getP_td_max());

        map.put("p_md_probability", project.getP_md_probability());
        map.put("p_md_serious", project.getP_md_serious());
        map.put("p_md_time", project.getP_md_time());
        map.put("p_md_min", project.getP_md_min());
        map.put("p_md_max", project.getP_md_max());

        map.put("p_pd_probability", project.getP_pd_probability());
        map.put("p_pd_serious", project.getP_pd_serious());
        map.put("p_pd_time", project.getP_pd_time());
        map.put("p_pd_min", project.getP_pd_min());
        map.put("p_pd_max", project.getP_pd_max());

        map.put("p_ppqa_probability", project.getP_ppqa_probability());
        map.put("p_ppqa_serious", project.getP_ppqa_serious());
        map.put("p_ppqa_time", project.getP_ppqa_time());
        map.put("p_ppqa_min", project.getP_ppqa_min());
        map.put("p_ppqa_max", project.getP_ppaq_max());

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
                    proj = gson.fromJson(body,Project.class);
                    handler.sendEmptyMessage(ConstantUtils.PROJECT_ADD_DATA);
                } else {
                    showToast("添加失败！项目重名!");
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
        Intent intent = new Intent(context, AddProjectActivity.class);
        context.startActivity(intent);
    }
    private void showPopupWindow(final int index) {

        View contentView = LayoutInflater.from(AddProjectActivity.this).inflate(R.layout.popupwindow, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(contentView);//设置包含视图

        final EditText et_probability = (EditText) contentView.findViewById(R.id.et_probability);
        final EditText et_serious = (EditText) contentView.findViewById(R.id.et_serious);
        final EditText et_time = (EditText) contentView.findViewById(R.id.et_time);
        final EditText et_minNum = (EditText) contentView.findViewById(R.id.et_minNum);
        final EditText et_maxNum = (EditText) contentView.findViewById(R.id.et_maxNum);
        final TextView tv_risk = (TextView) contentView.findViewById(R.id.tv_risk);
        String [] titles = {"测试缺陷管理","里程碑评审缺陷","过程评估缺陷","PPQA检测缺陷"};
        TextView tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        tv_title.setText(titles[index]);

        final TextView tv_ok = (TextView) contentView.findViewById(R.id.tv_ok);
        final TextView tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPopWindow.dismiss();
            }
        });
        tv_risk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prob =  et_probability.getText().toString().trim();
                if (prob==null||prob.equals("")){
                    showToast("发生概率不能为空！");
                    return;
                }
                int probability = Integer.valueOf(prob);
                if (probability>100||probability<0){
                    showToast("发生概率输入不合法");
                    return;
                }

                String ser =  et_serious.getText().toString().trim();
                if (ser==null||ser.equals("")){
                    showToast("严重程度不能为空！");
                    return;
                }
                int serious = Integer.valueOf(et_serious.getText().toString().trim());
                if (serious>100||serious<0){
                    showToast("严重程度输入不合法");
                    return;
                }

                String times =  et_time.getText().toString().trim();
                if (times==null||times.equals("")){
                    showToast("发生时长不能为空！");
                    return;
                }
                int time = Integer.valueOf(et_time.getText().toString().trim());
                if (time>100||time<0){
                    showToast("发生时长输入不合法");
                    return;
                }

                tv_risk.setText(probability*serious*time+"");
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prob =  et_probability.getText().toString().trim();
                if (prob==null||prob.equals("")){
                    showToast("发生概率不能为空！");

                    return;
                }
                int probability = Integer.valueOf(prob);
                if (probability>100||probability<0){
                    showToast("发生概率输入不合法");
                    return;
                }

                String ser =  et_serious.getText().toString().trim();
                if (ser==null||ser.equals("")){
                    showToast("严重程度不能为空！");
                    return;
                }
                int serious = Integer.valueOf(et_serious.getText().toString().trim());
                if (serious>100||serious<0){
                    showToast("严重程度输入不合法");
                    return;
                }

                String times =  et_time.getText().toString().trim();
                if (times==null||times.equals("")){
                    showToast("发生时长不能为空！");
                    return;
                }
                int time = Integer.valueOf(et_time.getText().toString().trim());
                if (time>100||time<0){
                    showToast("发生时长输入不合法");
                    return;
                }

                String min =  et_minNum.getText().toString().trim();
                if (min==null||min.equals("")){
                    showToast("最小发现数不能为空！");
                    return;
                }
                int minNum = Integer.valueOf(et_minNum.getText().toString().trim());
                if (minNum>100||minNum<0){
                    showToast("最小发现数输入不合法");
                    return;
                }
                String max =  et_maxNum.getText().toString().trim();
                if (max==null||max.equals("")){
                    showToast("最多发现数不能为空！");
                    return;
                }
                int maxNum = Integer.valueOf(et_maxNum.getText().toString().trim());
                if (maxNum>100||maxNum<0){
                    showToast("最多发现数输入不合法");
                    return;
                }
                if (index==0){
                    project.setP_td_probability(prob);
                    project.setP_td_serious(ser);
                    project.setP_td_time(times);
                    project.setP_td_min(min);
                    project.setP_td_max(max);
                    img_one.setImageResource(R.drawable.one_yes);

                }else if (index == 1){
                    project.setP_md_probability(prob);
                    project.setP_md_serious(ser);
                    project.setP_md_time(times);
                    project.setP_md_min(min);
                    project.setP_md_max(max);
                    img_two.setImageResource(R.drawable.two_yes);
                }else if (index == 2){
                    project.setP_pd_probability(prob);
                    project.setP_pd_serious(ser);
                    project.setP_pd_time(times);
                    project.setP_pd_min(min);
                    project.setP_pd_max(max);
                    img_three.setImageResource(R.drawable.three_yes);
                }else if (index==3){
                    project.setP_ppqa_probability(prob);
                    project.setP_ppqa_serious(ser);
                    project.setP_ppqa_time(times);
                    project.setP_ppqa_min(min);
                    project.setP_ppaq_max(max);
                    img_four.setImageResource(R.drawable.four_yes);
                }
                flagPaths[index] =true;

                mPopWindow.dismiss();

            }
        });


        View rootview = LayoutInflater.from(AddProjectActivity.this).inflate(R.layout.activity_main, null);
        // 控制popupwindow点击屏幕其他地方消失
        mPopWindow.setBackgroundDrawable(this.getResources().getDrawable(
                R.drawable.bg_popupwindow));// 设置背景图片，不能在布局中设置，要通过代码来设置
        mPopWindow.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
        mPopWindow.setAnimationStyle(R.style.contextMenuAnim);//设置动画
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//设置模式，和Activity的一样，覆盖，调整大小。
        mPopWindow.setFocusable(true);
        mPopWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

    }

    class AddProjectHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.PROJECT_ADD_DATA:
                    if (proj!=null&&proj.getP_name()!=null&&proj.getP_name().length()>0){
                       ProjectListActivity.actionStart(context);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
