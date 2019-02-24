package com.sangxiang.app.utils;


/**
 * @author: river
 * @description:
 * @date: created at 2018/1/30 14:57
 * @modified by:
 */
public interface ISystem {
    /**
     * 系统默认的 logic number
     */
    public static final long SYS_DEFALT_DEPT = 0;
    public static final long SYS_DEPT_ROOT = 0;
    public static final long SYS_ROLE_ROOT = 0;
    public static final long SYS_DIMENSION_ROOT = 0;

    public static final String SYS_DEPT_ROOT_NAME = "ROOT";

    /**
     * 在 用户 部门 角色 unit 时候 不匹配的项为 -1
     */
    public static final long UN_MATCH_DPT = -1;
    public static final long UN_MATCH_USER = -1;
    public static final long UN_MATCH_ROLE = -1;
    public static final long UN_MATCH_ROLE_LABLE = -1;
    public static final long UN_MATCH_DPT_LABLE = -1;

    public static final long UN_MATCH_LABLE_TYPE = -1;


    //角色关系
    public static final int RLAT_ROLE = 0;
    //用户关系
    public static final int RLAT_USER = 1;

    public static interface IUSER
    {
    	 public static final String USER_TOKEN = "user:token:store:";
    	 public static final String USER = "user:user:store:";
    	 //最近联系人
        public static final String USER_RECENT = "user:recent:store:";
        public static final int USER_TOKEN_EXPIRATION_TIME             = 60*360000;
    }
    public static interface ISMSTemplateCode
    {
        /**
         * redis中短信验证码的key
         */
        public static final String SMS_VCODE_STORE_KEY = "sms:vcode:store:";
        /**
         * 短信计数count的KEY
         */
        public static final String SMS_VCODE_STORE_KEY_COUNT = "sms:vcode:count:";
        
        public static final int smsVcodeCountMax            = 5;
        public static final int EXPIRATION_TIME             = 60*15;
        
        public static final int REGV                        = 6984; //发送验证码模板ID
        public static final int RESET_PWD                   = 13105; //发送修改密码验证码ID , 待审核
        public static final int ERROR                       = 8769; //程序异常通知模板
        public static final int JOB_EXCU_ERROR              = 8570; //JOB异常通知模板
    }
}
