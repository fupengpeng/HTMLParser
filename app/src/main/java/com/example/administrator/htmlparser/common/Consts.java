package com.example.administrator.htmlparser.common;

/**
 * 常量
 */
public class Consts {
    /**
     * 网络请求URL
     */
    public class NetUrl {
        /**
         * 域名（IP）
         */
        public static final String BASE_URL = "http://sdjn.wenming.cn/";
        public static final String CIVILIZATIONFOCUSING_URL = BASE_URL+"wmjj/";
        public static final String CIVILIZEDCITY_URL = BASE_URL+"wmcscj/";
        public static final String CHARMSHOOL_URL = BASE_URL+"wmlyxx/";
        public static final String VOLUNTEERSERVICE_URL = BASE_URL+"zyfw/";
        public static final String CRAFT="http://www.wenming.cn/syzhq/tjq/wmwlmlb/index.shtml";
public static final String PANELS="http://www.wenming.cn/jwmsxf_294/zggygg/zgfzbl/";
        public static final String RAIL="http://www.wenming.cn/jwmsxf_294/zggygg/hwwdgy/";
        public static final String PLANE="http://www.wenming.cn/syjj/gyggzpz/";
        public static final String PHONE="http://www.wenming.cn/jwmsxf_294/zggygg/sjgygg/";
    }

    /**
     * 等待对话框提示的信息
     */
    public class WaitDialogMessage {
        /**
         * 登录
         */
        public static final String LOGIN = "登录中...";
        /**
         * 数据加载中
         */
        public static final String DATA_LODING = "数据加载中...";
    }

    /**
     * 网络请求失败
     */
    public static final String NET_REQUEST_EXCEPTION = "数据加载失败，请重新加载";

}
