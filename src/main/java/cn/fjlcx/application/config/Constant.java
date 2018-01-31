package cn.fjlcx.application.config;

public class Constant {
	/**
     * jwt
     */
    public static final String JWT_ID = "jwt_apply";
    public static final String JWT_SECRET = "fu1jian2fu3zhou4ling5chuan6xian";
    public static final int JWT_TTL = 60*60*1000;  //millisecond
    /**
     * session
     */
    public static final String LOGIN_USER = "CURRENT_USER";
    public static final String LOGIN_USER_MENU = "CURRENT_USER_MENU";
    
    /**
     * other
     */
    public static final String ENCRYPTION_TYPE = "MD5";
    public static final String RESET_PWD = "123456";

}
