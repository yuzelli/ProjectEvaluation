package com.example.yuzelli.projectevaluation.constants;

/**
 * Created by 51644 on 2017/4/28.
 */

public class ConstantUtils {
    //用户登录成功
    public static final int LOGIN_GET_DATA = 0x00001001;
    public static final int PROJECT_ADD_DATA = 0x00001002;
    public static final int PROJECT_LIST_ADD_DATA = 0x00001003;
    public static final int PROJECT_FIND_NAME_ADD_DATA = 0x00001004;
    public static final int DEFECT_ADD_DATA = 0x00001005;
    public static final int DEFECT_FINDALL_DATA = 0x00001006;
    public static final int QUALITY_ADD_DATA = 0x00001007;
    public static int USER_TYPE = 1;



    public static final String USER_ADDRESS = "http://192.168.191.1:8080/PEService/";
    //用户操作
    public static final String USER_REGISTER = "UserInfoServlet";
    public static final String PROJECT_REGISTER = "ProjectServlet";
    public static final String DEFECT_REGISTER = "DefectServlet";
    public static final String QUALITY_REGISTER = "QualityServlet";
    //登录用户信息
    public static final String USER_LOGIN_INFO = "currentUserInfo";
}
