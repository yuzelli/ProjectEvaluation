package com.example.yuzelli.projectevaluation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.Project;
import com.example.yuzelli.projectevaluation.https.OkHttpClientManager;
import com.example.yuzelli.projectevaluation.uitls.CommonAdapter;
import com.example.yuzelli.projectevaluation.uitls.GsonUtils;
import com.example.yuzelli.projectevaluation.uitls.ViewHolder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class ProjectListActivity extends BaseActivity {
    private List<Project> projectList;
    private ListView lv_projectList;
    private CommonAdapter<Project> adapter;
    private ProjectHandler handler;
    private Context context;

    @Override
    protected int layoutInit() {
        return R.layout.activity_project_list;
    }

    @Override
    protected void binEvent() {
        context = this;
        handler = new ProjectHandler();
        lv_projectList = (ListView) this.findViewById(R.id.lv_projectList);
        projectList = new ArrayList<>();
        getProjectListData();
    }

    private void getProjectListData() {
        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.PROJECT_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "findAllProject");
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
                    projectList = GsonUtils.jsonToArrayList(body, Project.class);
                    handler.sendEmptyMessage(ConstantUtils.PROJECT_LIST_ADD_DATA);
                } else {
                    showToast("请求失败！");
                }
            }
        });
    }

    private void updataListView() {
         adapter = new CommonAdapter<Project>(this,projectList,R.layout.cell_project_list) {
             @Override
             public void convert(ViewHolder helper, Project item) {
                     helper.setText(R.id.tv_name,item.getP_name());
             }
         };
        lv_projectList.setAdapter(adapter);
        lv_projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  ProjectDetailActivity.actionStart(context,projectList.get(position));

            }
        });
    }

    /**
     * 跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ProjectListActivity.class);
        context.startActivity(intent);
    }

    class ProjectHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtils.PROJECT_LIST_ADD_DATA:
                    updataListView();
                    break;
                default:
                    break;
            }
        }
    }


}
