package com.sangxiang.dao.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CheckoutUtil {
    //测试需要手机号 190 - 199 开头
    //final static Pattern isMobile = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[06-8])\\d{8}$");
    final static Pattern isMobile = Pattern.compile("^((19[0-9])|(13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
    final static Pattern isEmail = Pattern
            .compile("^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$");
    final static Pattern isUserName = Pattern.compile("^[0-9a-zA-Z_]+$");

    public static boolean isMobileNO(String mobiles) {
        if (mobiles.length() != 11) {
            return false;
        }
        Matcher m = isMobile.matcher(mobiles);
        return m.matches();
//        return true;
    }

    public static boolean isEmail(String email) {
        if (email.length() > 50) {
            return false;
        }
        Matcher m = isEmail.matcher(email);
        return m.matches();
    }

    public static boolean isNotBlank(String val) {
        return val != null && !val.trim().isEmpty();
    }

    public static boolean isUserName(String userName) {
        if (userName.length() > 15 || userName.length() < 5) {
            return false;
        }
        Matcher m = isUserName.matcher(userName);
        return m.matches();
    }
}
