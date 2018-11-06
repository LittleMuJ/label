package com.label.common.sms;


import java.util.ArrayList;

public class SmsContentUtil {
    //短信应用SDK AppID
    public static final int APPID=1400140562;

    //短信应用SDK APPKey
    public static final String APPKEY="cef4183b6e42cecff8262a6a5f1e4e75";

    //短信模板ID，需要在短信应用中申请
    public static final int TTEMPLATEID=195834;

    //签名
    public static final String SMSSIGN = "易码云天";

    //中国地区电话国家码
    public static  final  String nationCode="86";

    //验证码，时间
    public static final ArrayList<String> list = new ArrayList<String>(){{add(getcode());add("5");}};

    //获取随机四位数的验证码
    private static String getcode() {
    	String code = (int) (Math.random() * 9000 + 1000) + "";
    	return code;
    }

}
