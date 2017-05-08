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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.Defect;
import com.example.yuzelli.projectevaluation.entity.Project;
import com.example.yuzelli.projectevaluation.entity.UserInfo;
import com.example.yuzelli.projectevaluation.https.OkHttpClientManager;
import com.example.yuzelli.projectevaluation.uitls.DateTimePickDialogUtil;
import com.example.yuzelli.projectevaluation.uitls.GsonUtils;
import com.example.yuzelli.projectevaluation.uitls.SharePreferencesUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import okhttp3.Request;

public class AddDefectActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private Project project;

    private TextView tv_defect_number;
    private TextView tv_time;
    private TextView tv_project_name;
    private TextView tv_reason;
    private TextView tv_result;
    private Spinner spinner_type;
    private Spinner spinner_state;
    private RelativeLayout rl_time;
    private RelativeLayout rl_reason;
    private RelativeLayout rl_result;
    private Button btn_finish;

    private int defectType = 1;
    private int defectState = 1;
    private int number = 0;

    private ArrayList<Defect> defects;
    private AddDefectHandler handler;


    @Override
    protected int layoutInit() {
        return R.layout.activity_add_defect;
    }

    @Override
    protected void binEvent() {
        context = this;
        Intent intent = getIntent();
        handler = new AddDefectHandler();
        project = (Project) intent.getSerializableExtra("project");
        Defect updataDefect = (Defect) intent.getSerializableExtra("defect");

        tv_defect_number = (TextView) this.findViewById(R.id.tv_defect_number);
        tv_time = (TextView) this.findViewById(R.id.tv_time);
        tv_project_name = (TextView) this.findViewById(R.id.tv_project_name);
        tv_reason = (TextView) this.findViewById(R.id.tv_reason);
        tv_result = (TextView) this.findViewById(R.id.tv_result);
        spinner_type = (Spinner) this.findViewById(R.id.spinner_type);
        spinner_state = (Spinner) this.findViewById(R.id.spinner_state);
        rl_time = (RelativeLayout) this.findViewById(R.id.rl_time);
        rl_reason = (RelativeLayout) this.findViewById(R.id.rl_reason);
        rl_result = (RelativeLayout) this.findViewById(R.id.rl_result);
        btn_finish = (Button) this.findViewById(R.id.btn_finish);
        rl_time.setOnClickListener(this);
        rl_reason.setOnClickListener(this);
        rl_result.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        tv_project_name.setText(project.getP_name());
        updataView();
        if (updataDefect!=null){
            showDefect(updataDefect);
            btn_finish.setVisibility(View.GONE);
        }else {
            getProjectDefect();
        }
    }

    private void showDefect(Defect updataDefect) {
        tv_defect_number.setText(updataDefect.getD_number()+"");
        tv_time.setText(updataDefect.getD_time()+"");
        spinner_type.setSelection(updataDefect.getD_type());

        tv_reason.setText(updataDefect.getD_reason()+"");
        tv_result.setText(updataDefect.getD_consequence()+"");
        spinner_state.setSelection(updataDefect.getD_state());
    }

    private void updataView() {
        ArrayList<String> typelist = new ArrayList<>();
        typelist.add("测试缺陷管理");
        typelist.add("里程碑评审缺陷");
        typelist.add("过程评估缺陷");
        typelist.add("PPQA检测缺陷");
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(AddDefectActivity.this, android.R.layout.simple_spinner_dropdown_item, typelist);
        spinner_type.setAdapter(typeadapter);
        spinner_type.setSelection(defectType-1);
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defectType = position + 1;
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.white));    //设置颜色
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> statelist = new ArrayList<>();
        statelist.add("等待处理");
        statelist.add("已经处理");
        statelist.add("关闭");
        statelist.add("再次出现");
        ArrayAdapter<String> stateadapter = new ArrayAdapter<String>(AddDefectActivity.this, android.R.layout.simple_spinner_dropdown_item, statelist);
        spinner_state.setAdapter(stateadapter);
        spinner_state.setSelection(defectState-1);
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view;
                tv.setTextColor(getResources().getColor(R.color.white));    //设置颜色
                defectState = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getProjectDefect() {
        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.DEFECT_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "findAllDefectByProjcetID");
        map.put("project_id", project.getProject_id() + "");

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
                    defects = GsonUtils.jsonToArrayList(body, Defect.class);
                    handler.sendEmptyMessage(ConstantUtils.DEFECT_FINDALL_DATA);
                } else {
                    showToast("用户名或密码错误！");
                }
            }
        });
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void actionStart(Context context, Project project,Defect defect) {
        Intent intent = new Intent(context, AddDefectActivity.class);
        intent.putExtra("project", project);
        intent.putExtra("defect", defect);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_time:
                showDataPicker(tv_time);
                break;
            case R.id.rl_reason:
                showMyDialog(0);
                break;
            case R.id.rl_result:
                showMyDialog(1);
                break;
            case R.id.btn_finish:
                doAddDefectRequest();
                break;
            default:
                break;
        }
    }

    private void showMyDialog(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDefectActivity.this);
        //使用xml文件定义视图
        View view = LayoutInflater.from(AddDefectActivity.this).inflate(R.layout.dialog, null);
        final EditText et = (EditText)view.findViewById(R.id.editText1);
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String content =  et.getText().toString().trim();
                if (content!=null&&!content.equals("")){
                    if (index==0){
                        tv_reason.setText(content);
                    }else {
                        tv_result.setText(content);
                    }
                }else {
                    showToast("请输入内容！");
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();


    }

    private void doAddDefectRequest() {
        String time = tv_time.getText().toString().trim();
        String reason = tv_reason.getText().toString().trim();
        String result =tv_result.getText().toString().trim();

        if (time==null||time.equals("")){
            showToast("请填写数据！");
            return;
        }

        if (reason==null||reason.equals("")){
            showToast("请填写数据！");
            return;
        }

        if (result==null||result.equals("")){
            showToast("请填写数据！");
            return;
        }

        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.DEFECT_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "addDefect");
        map.put("d_project_id", project.getProject_id() + "");
        map.put("d_number", (number+1 )+"");
        map.put("d_type", defectType + "");
        map.put("d_time", tv_time.getText().toString().trim() + "");
        map.put("d_reason", tv_reason.getText().toString().trim() + "");
        map.put("d_consequence", tv_result.getText().toString().trim() + "");
        map.put("d_state", defectState + "");

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
                    Defect defect = (Defect) GsonUtils.getInstanceByJson(Defect.class,body);
                    if (defect!=null) {
                        handler.sendEmptyMessage(ConstantUtils.DEFECT_ADD_DATA);
                    }
                } else {
                    showToast("添加失败！");
                }
            }
        });
    }

    /**
     * 时间选择器
     */
    private void showDataPicker(final TextView tv_time) {
        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                this, tv_time.getText().toString().trim());
        dateTimePicKDialog.dateTimePicKDialog(tv_time);
    }

    class AddDefectHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.DEFECT_ADD_DATA:

                    AddQualityActivity.actionStart(context,project,null);
                    finish();


                    break;
                case ConstantUtils.DEFECT_FINDALL_DATA:
                    if (defects != null && defects.size() > 0) {
                        setNumber();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setNumber() {
        for (Defect defect : defects) {
             if (defect.getD_number()>number){
                 number = defect.getD_number();
             }
        }
        tv_defect_number.setText(number+1+"");
    }

}
