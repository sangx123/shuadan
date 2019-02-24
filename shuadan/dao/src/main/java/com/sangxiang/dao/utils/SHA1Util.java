package com.sangxiang.dao.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class SHA1Util {
    public static byte[] encode(String origin, String charsetname) {
        if (origin == null) {
            return null;
        }
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            if (charsetname == null || "".equals(charsetname)) {
                result = md.digest(origin.getBytes());
            } else {
                result = md.digest(origin.getBytes(charsetname));
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String encodeBase64(String origin) {
        return Base64.encodeBase64String(encode(origin, null));
    }

    public static String encodeBase64(String origin, String charsetname) {
        return Base64.encodeBase64String(encode(origin, charsetname));
    }

    public static String encodeHex(String origin) {
        return byte2hex(encode(origin, null));
    }

    public static String encodeHex(String origin, String charsetname) {
        return byte2hex(encode(origin, charsetname));
    }

    public static String hex2Base64(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toLowerCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return Base64.encodeBase64String(d);
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer("");
        String temp;
        for (byte x : b) {
            temp = Integer.toHexString(x & 0XFF);
            if (temp.length() == 1) {
                hs.append("0");
            }
            hs.append(temp);
        }
        return hs.toString();
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }

}
