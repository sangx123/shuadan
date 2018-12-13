package com.sangxiang.util;


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import com.rd.p2p.common.util.code.MD5;*/

/**
 * 工具类-字符串处理
 * 
 * @author xx
 * @version 2.0
 * @since 2014年1月28日
 */
public class StringUtil extends StringUtils {
	
	//private static final Logger LOGGER = Logger.getLogger(StringUtil.class);
	
	/**
	 * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
	 * 
	 * @param str
	 * @return
	 */
	public static String isNull(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 将对象转为字符串
	 * 
	 * @param o
	 * @return
	 */
	public static String isNull(Object o) {
		if (o == null) {
			return "";
		}
		String str = "";
		if (o instanceof String) {
			str = (String) o;
		} else {
			str = o.toString();
		}
		return str.trim();
	}

	/**
	 * 检验是否为空或空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return "".equals(StringUtil.isNull(str));
	}

	public static boolean isBlank(Object o) {
		return "".equals(StringUtil.isNull(o));
	}

	/**
	 * 检验是否非空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return !"".equals(StringUtil.isNull(str));
	}

	private static final Pattern REGEX_PHONE = Pattern.compile("^((13[0-9])|(14[57])|(15[0-9])|(17[0135678])|(18[0-9]))\\d{8}$");
	/**
	 * 检验手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		phone = isNull(phone);
		Matcher matcher = REGEX_PHONE.matcher(phone);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	private static final Pattern REGEX_CHN = Pattern.compile("[\\u4e00-\\u9fa5]{2,10}");
	/**
	 * 校验是否全中文，返回true 表示是 反之为否
	 * 
	 * @param realname
	 * @return
	 */
	public static boolean isChinese(String realname) {
		realname = isNull(realname);
		Matcher matcher = REGEX_CHN.matcher(realname);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	private static final Pattern REGEX_EMAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	/**
	 * 校验邮箱格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		email = isNull(email);
		Matcher matcher = REGEX_EMAIL.matcher(email);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	private static final Pattern REGEX_ID_15 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
	private static final Pattern REGEX_ID_18 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
	/**
	 * 校验身份证号码
	 * 
	 * @param
	 * @return
	 */
	public static boolean isCard(String cardId) {
		cardId = isNull(cardId);
		// 身份证正则表达式(15位)
		// 身份证正则表达式(18位)
		Matcher matcher1 = REGEX_ID_15.matcher(cardId);
		Matcher matcher2 = REGEX_ID_18.matcher(cardId);
		boolean isMatched = matcher1.matches() || matcher2.matches();
		return isMatched;
	}
	
	/**
	 * 根据身份证Id获取性别
	 * @param cardId
	 * @return
	 */
	public static int getSex(String cardId) {
		int sexNum = 0;
		//15位的最后一位代表性别，18位的第17位代表性别，奇数为男，偶数为女
		if (cardId.length() == 15) {
			sexNum = cardId.charAt(cardId.length() - 1);
		} else {
			sexNum = cardId.charAt(cardId.length() - 2);
		}
		
		if (sexNum % 2 == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 根据身份证Id获取出生年月日
	 * @param cardId
	 * @return
	 */
	public static String getBirthday(String cardId) {
		String birthday = "";
		//15位7-12位出生年月日 如590101 代表 19590101; 18位7-14位出生年月日如 19590101
		if (cardId.length() == 15) {
			birthday = "19" + cardId.substring(6, 12);
		} else {
			birthday = cardId.substring(6, 14);
		}
		return birthday;
	}



	private static final Pattern REGEX_INTEGER = Pattern.compile("\\d*");
	/**
	 * 判断字符串是否为整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (isBlank(str)) {
			return false;
		}
		Matcher matcher = REGEX_INTEGER.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	private static final Pattern REGEX_DIGITAL = Pattern.compile("(-)?\\d*(.\\d*)?");
	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isBlank(str)) {
			return false;
		}
		Matcher matcher = REGEX_DIGITAL.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstCharUpperCase(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1, s.length()));
		return sb.toString();
	}

	/**
	 * 隐藏头几位
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideFirstChar(String str, int len) {
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		if (str.length() <= len) {
			for (int i = 0; i < chars.length; i++) {
				chars[i] = '*';
			}
		} else {
			for (int i = 0; i < 1; i++) {
				chars[i] = '*';
			}
		}
		str = new String(chars);
		return str;
	}

	/**
	 * 隐藏末几位
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public static String hideLastChar(String str, int len) {
		//noinspection AliControlFlowStatementWithoutBraces
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		if (str.length() <= len) {
			return hideLastChar(str, str.length() - 1);
		} else {
			for (int i = chars.length - 1; i > chars.length - len - 1; i--) {
				chars[i] = '*';
			}
		}
		str = new String(chars);
		return str;
	}

	/**
	 * 指定起始位置字符串隐藏
	 * 
	 * @param str
	 * @param index1
	 * @param index2
	 * @return
	 */
	public static String hideStr(String str, int index1, int index2) {
		if (str == null) {
			return null;
		}
		String str1 = str.substring(index1, index2);
		String str2 = str.substring(index2);
		String str3 = "";
		if (index1 > 0) {
			str1 = str.substring(0, index1);
			str2 = str.substring(index1, index2);
			str3 = str.substring(index2);
		}
		char[] chars = str2.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] = '*';
		}
		str2 = new String(chars);
		String str4 = str1 + str2 + str3;
		return str4;
	}

	/**
	 * Object数组拼接为字符串
	 * @param args
	 * @return
	 */
	public static String contact(Object[] args) {
		if(args == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Long数组拼接为字符串
	 * @param args
	 * @return
	 */
	public static String contact(long[] args) {
		if(args == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (i < args.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 数字数组拼接为字符串
	 * 
	 * @param arr
	 * @return
	 */
	public static String array2Str(int[] arr) {
		if(arr == null){
			return "";
		}
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			s.append(arr[i]);
			if (i < arr.length - 1) {
				s.append(",");
			}
		}
		return s.toString();
	}

	/**
	 * 字符串数组转换为数字数组
	 * 
	 * @param strarr
	 * @return
	 */
	public static int[] strarr2intarr(String[] strarr) {
		int[] result = new int[strarr.length];
		for (int i = 0; i < strarr.length; i++) {
			result[i] = Integer.parseInt(strarr[i]);
		}
		return result;
	}

	/*public static String fillTemplet(String template, Map<String, Object> sendData) {
		// 模板中的'是非法字符，会导致无法提交，所以页面上用`代替
		template = template.replace('`', '\'');
		try {
			return FreemarkerUtil.renderTemplate(template, sendData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/

	/**
	 * 大写字母转成“_”+小写 驼峰命名转换为下划线命名
	 * 
	 * @param str
	 * @return
	 */
	public static String toUnderline(String str) {
		char[] charArr = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
				sb.append('_').append(charArr[i]);
			} else {
				sb.append(charArr[i]);
			}
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 下划线改成驼峰样子
	 * 
	 * @param str
	 * @return
	 */
	public static String clearUnderline(String str) {
		char[] charArr = StringUtil.isNull(str).toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		sb.append(charArr[0]);
		boolean isClear = false;
		for (int i = 1; i < charArr.length; i++) {
			if (charArr[i] == '_') {
				isClear = true;
				continue;
			}
			if (isClear && (charArr[i] >= 'a' && charArr[i] <= 'z')) {
				char c = (char) (charArr[i] - 32);
				sb.append(c);
				isClear = false;
			} else {
				sb.append(charArr[i]);
			}

		}
		return sb.toString();
	}

	/**
	 * String to int
	 * 
	 * @param str
	 * @return
	 */
	public static int toInt(String str) {
		if (StringUtil.isBlank(str)) {
			return 0;
		}
		int ret = 0;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	public static byte toByte(String str) {
		if (StringUtil.isBlank(str)) {
			return 0;
		}
		byte ret = 0;
		try {
			ret = Byte.parseByte(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * String to Long
	 * 
	 * @param str
	 * @return
	 */
	public static long toLong(String str) {
		if (StringUtil.isBlank(str)) {
			return 0L;
		}
		long ret = 0;
		try {
			ret = Long.parseLong(str);
		} catch (NumberFormatException e) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * String[] to long[]
	 * 
	 * @param str
	 * @return
	 */
	public static long[] toLongs(String[] str) {
		if (str == null || str.length < 1) {
			return new long[] { 0L };
		}
		long[] ret = new long[str.length];
		ret = (long[]) ConvertUtils.convert(str, long.class);
		return ret;
	}
	
	/**
	 * String[] to double[]
	 * 
	 * @param str
	 * @return
	 */
/*	public static double[] toDoubles(String[] str) {

		if (str == null || str.length < 1)
			return new double[] { 0L };
		double[] ret = new double[str.length];
		for (int i = 0; i < str.length; i++) {
			ret[i] = toDouble(str[i]);
		}
		return ret;
	}*/

	/**
	 * String to Double
	 * 
	 * @param str
	 * @return
	 */
/*	public static double toDouble(String str) {
		if (StringUtil.isBlank(str))
			return 0;
		try {
			return BigDecimalUtil.round(str);
		} catch (Exception e) {
			return 0;
		}
	}*/
	
	/**
	 * MD5加密规则，用于注册时 明文加密
	 * @param str
	 * @return
	 */
/*	public static String getMd5Sign(String str) {
		String md5Str = MD5.getMD5ofStr("p2p_"+str+"_erongdu");
		return md5Str;
	}*/
	
	/**
	 * 生成指定长度的随机字符串，字母加数字组合
	 * @param length
	 * @return
	 */
    public static String getRandomString(int length) { 
        String val = "";  
        Random random = new Random();  
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) { 
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }
    
    /**
     * “参数=参数值”的模式用“&”字符拼接成字符串转换为map，例如："username=zhangsan&age=14"
     * @param str
     * @return
     */
    public static Map<String, Object> toMap(String str) {
    	Map<String, Object> data = new HashMap<String, Object>();
    	String[] str2 = str.split("&");
		for (String ss : str2) {
			String[] str3 = ss.split("=");
			data.put(str3[0], str3[1]);
		}
    	return data;
    }
    
    /**
     * 
     * mysql like 值进行处理，若包含通配符则转译
     * @param value
     * @return
     */
    public static String likeSQLTranslation(String value) {
    	if (isBlank(value)) {
    		return "";
    	}
    	value = value.replace("_", "\\_");
    	value = value.replace("%", "\\%");	
    	return "%" + value + "%";
    }


	private static final Pattern REGEX_HTML = Pattern.compile("<.+?>");
    /**
     * 
     * 去除字符串中包含的html标签内容
     * @param value
     * @return
     */
    public static String removeHtmlValue(String value) {
		Matcher matcher = REGEX_HTML.matcher(value);
		return matcher.replaceAll("");
    }
    
    /**
	 * 将字符串进行解码(默认解码为UTF-8)
	 * 
	 * @param s
	 *            被解码的字符串
	 * @return
	 */
	public static String urlDecode(String s) {
		return urlDecode(s, "UTF-8");
	}

	/**
	 * 将字符串进行解码
	 * 
	 * @param s
	 *            被解码的字符串
	 * @param enc
	 *            解码
	 * @return
	 */
	public static String urlDecode(String s, String enc) {
		if (StringUtil.isBlank(s)) {
			return s;
		}
		try {
			return URLDecoder.decode(s, enc);
		} catch (Exception e) {
			//LOGGER.error("urlDecode error, value:" + s, e);
			return s;
		}
	}

	/**
	 * 将字符串进行编码(默认编码为UTF-8)
	 * 
	 * @param s
	 *            被编码的字符串
	 * @return
	 */
	public static String urlEncode(String s) {
		return urlEncode(s, "UTF-8");
	}

	/**
	 * 将字符串进行编码
	 * 
	 * @param s
	 * @param enc
	 * @return
	 */
	public static String urlEncode(String s, String enc) {
		if (StringUtil.isBlank(s)) {
			return s;
		}
		try {
			return URLEncoder.encode(s, enc);
		} catch (Exception e) {
			//LOGGER.error("urlEncode error, value:" + s, e);
			return s;
		}
	}
    
    /**
	 * 
	* @Title: mapToUrl 
	* @Description: 将map转为url字符串
	* @param params
	* @param transFlag 参数驼峰,下划线转换标志
	* @return String    返回类型 
	* @throws
	 */
	public static String mapToUrl(Map<String, String> params, boolean transFlag) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String key : params.keySet()) {
            String value = params.get(key);
            String realKey = key;
            if(transFlag){
            	realKey = toUnderline(realKey);
            }
            if (isFirst) {
                sb.append(realKey + "=" + StringUtil.urlEncode(value));
                isFirst = false;
            } else {
                if (value != null) {
                    sb.append("&" + realKey + "=" + StringUtil.urlEncode(value));
                } else {
                    sb.append("&" + realKey + "=");
                }
            }
        }
        return sb.toString();
    }
	
	/**
     * 出去null和""
     * @param src
     * @return
     */
    public static String formatNull(String src) {
        return (src == null || "null".equals(src)) ? "" : src;
    }

    /**
     * 判断字符串是否为空的正则表达式，空白字符对应的unicode编码
     */
    private static final String EMPTY_REGEX = "[\\s\\u00a0\\u2007\\u202f\\u0009-\\u000d\\u001c-\\u001f]+";

    /**
     * 验证字符串是否为空
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        return input == null || "".equals(input) || input.matches(EMPTY_REGEX);
    }

    public static boolean isNotEmpty(String input){
        return !isEmpty(input);
    }

    public static String getUuid() {
        return UUID.randomUUID().toString();
    }
}
