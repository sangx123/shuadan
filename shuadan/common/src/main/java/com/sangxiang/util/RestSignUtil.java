package com.sangxiang.util;

import java.util.*;
import java.util.Map.Entry;

public class RestSignUtil {
    /**
     * 签名
     * 
     * @param appkey
     *            应用标识
     * @param url
     *            请求地址
     * @param requestBody
     *            请求体数据
     * @param privateKey
     *            私钥
     * @return
     */
    public static String sign(String appkey, String url, Map<String, String> queryMap, byte[] requestBody,
            String privateKey) {
        byte[] signData = genSignData(appkey, url, queryMap, requestBody);
        try {
            String sign = RSAUtils.sign(signData, privateKey);
            return sign;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] genSignData(String appkey, String url, Map<String, String> queryMap, byte[] requestBody) {
        try {
            byte[] appkeyBytes = appkey.getBytes("UTF-8");
            byte[] urlBytes = url.getBytes("UTF-8");

            String queryString = queryMapToString(queryMap);
            byte[] queryStringBytes = queryString.getBytes("UTF-8");

            byte[] bytes = new byte[appkeyBytes.length + urlBytes.length + requestBody.length + queryStringBytes.length];

            System.arraycopy(appkeyBytes, 0, bytes, 0, appkeyBytes.length);
            System.arraycopy(urlBytes, 0, bytes, appkeyBytes.length, urlBytes.length);
            System.arraycopy(requestBody, 0, bytes, appkeyBytes.length + urlBytes.length, requestBody.length);
            System.arraycopy(queryStringBytes, 0, bytes, appkeyBytes.length + urlBytes.length + requestBody.length,
                    queryStringBytes.length);

            return bytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证签名
     * 
     * @param appkey
     *            应用标识
     * @param url
     *            请求地址
     * @param requestBody
     *            请求体数据
     * @param publicKey
     *            公钥
     * @param sign
     *            签名值
     * @return
     */
    public static boolean verify(String appkey, String url, Map<String, String> queryMap, byte[] requestBody,
            String publicKey, String sign) {
        byte[] signData = genSignData(appkey, url, queryMap, requestBody);
        try {
            return RSAUtils.verify(signData, publicKey, sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证签名
     * 
     * @param md5String
     *            经过MD5进行哈希计算并使用base64编码过的字符串
     * @param publicKey
     *            公钥
     * @param sign
     *            签名值
     * @return
     */
    public static boolean verify(String md5String, String publicKey, String sign) {
        try {
            byte[] verifyData = md5String.getBytes("UTF-8");
            return RSAUtils.verify(verifyData, publicKey, sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String queryMapToString(Map<String, String> queryMap) {
        if (queryMap.size() == 0) {
            return "";
        }

        List<Entry<String, String>> mappingList = new ArrayList<Entry<String, String>>(queryMap.entrySet());

        Collections.sort(mappingList, new Comparator<Entry<String, String>>() {

            @Override
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuffer sf = new StringBuffer();
        for (Entry<String, String> entry : mappingList) {
            sf.append(entry.getKey()).append(entry.getValue() == null ? "" : entry.getValue());
        }

        return sf.toString();
    }
}
