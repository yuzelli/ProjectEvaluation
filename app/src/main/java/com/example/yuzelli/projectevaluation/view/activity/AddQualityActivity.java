package com.example.yuzelli.projectevaluation.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.Defect;
import com.example.yuzelli.projectevaluation.entity.Project;
import com.example.yuzelli.projectevaluation.entity.Quality;
import com.example.yuzelli.projectevaluation.https.OkHttpClientManager;
import com.example.yuzelli.projectevaluation.uitls.DateTimePickDialogUtil;
import com.example.yuzelli.projectevaluation.uitls.GsonUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class AddQualityActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private AddQualityHandler handler;
    private Project project;

    private TextView tv_project_name;

    private TextView tv_time;
    private TextView tv_density;
    private TextView tv_stage_process;
    private TextView tv_process;
    private TextView tv_eliminate;

    private RelativeLayout rl_time;
    private RelativeLayout rl_density;
    private RelativeLayout rl_stage_process;
    private RelativeLayout rl_process;
    private RelativeLayout rl_eliminate;
    private Button btn_finish;

    private Quality quality;

    @Override
    protected int layoutInit() {
        return R.layout.activity_add_quality;
    }

    @Override
    protected void binEvent() {
        context = this;
        Intent intent = getIntent();
        handler = new AddQualityHandler();
        project = (Project) intent.getSerializableExtra("project");
        Quality udaptaQuality = (Quality) intent.getSerializableExtra("quality");

        tv_project_name = (TextView) this.findViewById(R.id.tv_project_name);
        tv_time = (TextView) this.findViewById(R.id.tv_time);
        tv_density = (TextView) this.findViewById(R.id.tv_density);
        tv_stage_process = (TextView) this.findViewById(R.id.tv_stage_process);
        tv_process = (TextView) this.findViewById(R.id.tv_process);
        tv_eliminate = (TextView) this.findViewById(R.id.tv_eliminate);

        rl_time = (RelativeLayout) this.findViewById(R.id.rl_time);
        rl_density = (RelativeLayout) this.findViewById(R.id.rl_density);
        rl_stage_process = (RelativeLayout) this.findViewById(R.id.rl_stage_process);
        rl_process = (RelativeLayout) this.findViewById(R.id.rl_process);
        rl_eliminate = (RelativeLayout) this.findViewById(R.id.rl_eliminate);
        btn_finish = (Button) this.findViewById(R.id.btn_finish);

        rl_time.setOnClickListener(this);
        rl_density.setOnClickListener(this);
        rl_stage_process.setOnClickListener(this);
        rl_process.setOnClickListener(this);
        rl_eliminate.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        tv_project_name.setText(project.getP_name());

        if (udaptaQuality!=null){
            updataView(udaptaQuality);
            btn_finish.setVisibility(View.GONE);
        }

    }

    private void updataView(Quality udaptaQuality) {
        tv_time.setText(udaptaQuality.getQ_time());
        tv_density.setText(udaptaQuality.getQ_density());
        tv_stage_process.setText(udaptaQuality.getQ_stage_process());
        tv_process.setText(udaptaQuality.getQ_process());
        tv_eliminate.setText(udaptaQuality.getQ_eliminate());
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void actionStart(Context context, Project project,Quality quality) {
        Intent intent = new Intent(context, AddQualityActivity.class);
        intent.putExtra("project", project);
        intent.putExtra("quality", quality);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_time:
                showDataPicker(tv_time);
                break;
            case R.id.rl_density:
                showMyDialog(0);
                break;
            case R.id.rl_stage_process:
                showMyDialog(1);
                break;
            case R.id.rl_process:
                showMyDialog(2);
                break;
            case R.id.rl_eliminate:
                showMyDialog(3);
                break;
            case R.id.btn_finish:
                doAddQuality();
                break;
            default:
                break;


        }
    }

    private void doAddQuality() {
        String time = tv_time.getText().toString().trim();
        String density = tv_density.getText().toString().trim();

        String stage_process =tv_stage_process.getText().toString().trim();
        String process =tv_process.getText().toString().trim();
        String eliminate =tv_eliminate.getText().toString().trim();

        if (time==null||time.equals("")){
            showToast("请填写数据！");
            return;
        }

        if (density==null||density.equals("")){
            showToast("请填写数据！");
            return;
        }

        if (stage_process==null||stage_process.equals("")){
            showToast("请填写数据！");
            return;
        }

        if (process==null||process.equals("")){
            showToast("请填写数据！");
            return;
        }

        if (eliminate==null||eliminate.equals("")){
            showToast("请填写数据！");
            return;
        }

        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.QUALITY_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "addQuality");
        map.put("q_project_id", project.getProject_id() + "");
        map.put("q_time",tv_time.getText().toString().trim() + "");
        map.put("q_density",changBaiFengHao(tv_density.getText().toString().trim()));
        map.put("q_stage_process",changBaiFengHao( tv_stage_process.getText().toString().trim()));
        map.put("q_process", changBaiFengHao(tv_process.getText().toString().trim() + ""));
        map.put("q_eliminate", changBaiFengHao(tv_eliminate.getText().toString().trim() + ""));

        String url = OkHttpClientManager.attachHttpGetParams(buffer.toString(), map);
        manager.getAsync(url, new OkHttpClientManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                showToast("加载网路数据失败！");
            }

            @Override
            public void requestSuccess(String result) throws Exception {

                JSONObject object = new JSONObject(result);
                String flag = object.getString("error");
                if (flag.equals("ok")) {
                    String body = object.getString("object");
                    quality = (Quality) GsonUtils.getInstanceByJson(Quality.class, body);
                    handler.sendEmptyMessage(ConstantUtils.QUALITY_ADD_DATA);
                } else {
                    showToast("用户名或密码错误！");
                }
            }
        });
    }

    private String changBaiFengHao(String str){
      String a =  str.replaceAll("%","%25");
        return URLEncoder.encode(a);
    };

    private void showMyDialog(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddQualityActivity.this);
        //使用xml文件定义视图
        View view = LayoutInflater.from(AddQualityActivity.this).inflate(R.layout.dialog, null);
        final EditText et = (EditText)view.findViewById(R.id.editText1);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String content =  et.getText().toString().trim();
                if (content!=null&&!content.equals("")){
                    if (index==0){
                        tv_density.setText(content);
                    }else if(index == 1){
                        tv_stage_process.setText(content);
                    }else if(index == 2){
                        tv_process.setText(content);
                    }else if(index == 3){
                        tv_eliminate.setText(content);
                    }
                }else {
                    showToast("请输入内容！");
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();


    }

    /**
     * 时间选择器
     */
    private void showDataPicker(final TextView tv_time) {
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                this, tv_time.getText().toString().trim());
        dateTimePicKDialog.dateTimePicKDialog(tv_time);
    }

    class AddQualityHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.QUALITY_ADD_DATA:
                    if (quality!=null){
                        ProjectListActivity.actionStart(context);
                        finish();
                    }else {
                        showToast("添加失败！");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
