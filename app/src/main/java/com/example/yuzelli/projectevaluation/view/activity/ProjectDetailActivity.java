package com.example.yuzelli.projectevaluation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yuzelli.projectevaluation.R;
import com.example.yuzelli.projectevaluation.base.BaseActivity;
import com.example.yuzelli.projectevaluation.constants.ConstantUtils;
import com.example.yuzelli.projectevaluation.entity.Defect;
import com.example.yuzelli.projectevaluation.entity.Project;
import com.example.yuzelli.projectevaluation.entity.Quality;
import com.example.yuzelli.projectevaluation.https.OkHttpClientManager;
import com.example.yuzelli.projectevaluation.uitls.CommonAdapter;
import com.example.yuzelli.projectevaluation.uitls.GsonUtils;
import com.example.yuzelli.projectevaluation.uitls.ViewHolder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class ProjectDetailActivity extends BaseActivity {
    private ListView lv_defect;
    private ListView lv_quality;
    private TextView tv_detail;

    private List<Defect> defects ;
    private List<Quality> qualitys ;

    private ProjectDetailHandler handler;
    private Project project;
    private Context context;
    @Override
    protected int layoutInit() {
        return R.layout.activity_project_detail;
    }

    @Override
    protected void binEvent() {
        context = this;
        Intent intent = getIntent();
        handler = new ProjectDetailHandler();
        project = (Project) intent.getSerializableExtra("project");

        lv_defect = (ListView)this.findViewById(R.id.lv_defect);
        lv_quality = (ListView) this.findViewById(R.id.lv_quality);
        tv_detail = (TextView) this.findViewById(R.id.tv_detail);
        tv_detail.setText("<"+project.getP_name()+">项目详情");

        getDefectData();


    }
    /**
     * 跳转
     *
     * @param context
     */
    public static void actionStart(Context context, Project project) {
        Intent intent = new Intent(context, ProjectDetailActivity.class);
        intent.putExtra("project", project);
        context.startActivity(intent);
    }

    private void getDefectData() {
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
                    handler.sendEmptyMessage(0);
                } else {
                    showToast("获取数据失败！");
                }
            }
        });
    }
    private void getQualityData() {
        OkHttpClientManager manager = OkHttpClientManager.getInstance();
        StringBuffer buffer = new StringBuffer(ConstantUtils.USER_ADDRESS).append(ConstantUtils.QUALITY_REGISTER);
        Map<String, String> map = new HashMap<>();
        map.put("type", "findAllQualityByProjectID");
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
                    qualitys = GsonUtils.jsonToArrayList(body, Quality.class);
                    handler.sendEmptyMessage(1);
                } else {
                    showToast("获取数据失败！");
                }
            }
        });
    }
    class ProjectDetailHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    upDefectList();
                    getQualityData();
                    break;
                case 1:
                    upQualityList();
                    break;
                default:
                    break;
            }
        }
    }

    private void upQualityList() {
        lv_quality.setAdapter(new CommonAdapter<Quality>(context,qualitys, R.layout.cell_project_quality) {
            @Override
            public void convert(ViewHolder helper, Quality item) {
                helper.setText(R.id.tv_time,"时间： "+item.getQ_time()+"");
                helper.setText(R.id.tv_density,"缺陷密度："+item.getQ_density()+"");
                helper.setText(R.id.tv_eliminate,"清除率："+item.getQ_eliminate()+"");
            }
        });
        lv_quality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AddQualityActivity.actionStart(context,project,qualitys.get(position));
            }
        });
    }

    private void upDefectList() {
       final ArrayList<String> typelist = new ArrayList<>();
        typelist.add("测试缺陷管理");
        typelist.add("里程碑评审缺陷");
        typelist.add("过程评估缺陷");
        typelist.add("PPQA检测缺陷");
        final ArrayList<String> statelist = new ArrayList<>();
        statelist.add("等待处理");
        statelist.add("已经处理");
        statelist.add("关闭");
        statelist.add("再次出现");
        lv_defect.setAdapter(new CommonAdapter<Defect>(context,defects,R.layout.cell_project_defect) {
            @Override
            public void convert(ViewHolder helper, Defect item) {
                helper.setText(R.id.tv_number,"编号"+item.getD_number()+"");
                helper.setText(R.id.tv_type,"类型："+typelist.get(item.getD_type()-1));
                helper.setText(R.id.tv_state,statelist.get(item.getD_state()-1));
            }
        });
        lv_defect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AddDefectActivity.actionStart(context,project,defects.get(position));
            }
        });
    }
}
