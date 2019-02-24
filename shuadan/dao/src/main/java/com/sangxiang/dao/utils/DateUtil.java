package com.sangxiang.dao.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author fujg
 */
public class DateUtil {

    public final static String YYYY_MM_DD_HH_MM_SS = new String("yyyy-MM-dd HH:mm:ss");

    public final static String YYYYMMDD_MM_HH_SS = new String("yyyy/MM/dd HH:mm:ss");

    public final static String YYYY_MM_DD = new String("yyyy-MM-dd");

    public final static String YYYY_MM_DD_HH_MM= new String("yyyy-MM-dd HH:mm");

    public final static String YYYYMMDDHHMMSS = new String("yyyyMMddHHmmss");

    public final static String YYYYMMDDHHMM = new String("yyyyMMddHHmm");

    public static final String YYYYMMDD = new String("yyyyMMdd");

    public static final String MMDD = new String("MMdd");

    public static final String YYYYMM = new String("yyyyMM");

    public static final String YYYY_MM = new String("yyyy-MM");

    /**
     * 时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return dateFormat.format(date);
    }

    public static String dateToString1(Date date) {
        if (null == date) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        return dateFormat.format(date);
    }

    public static String dateToString2(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD_MM_HH_SS);
        return dateFormat.format(date);
    }

    public static String simple(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD);
        return dateFormat.format(date);
    }

    public static String mmdd(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(MMDD);
        return dateFormat.format(date);
    }

    public static String simple2(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return dateFormat.format(date);
    }

    /**
     * format: YYYYMM
     *
     * @param date
     * @return
     */
    public static String simple3(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYYMM);
        return dateFormat.format(date);
    }

    /**
     * format: YYYY-MM
     *
     * @param date
     * @return
     */
    public static String simple4(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM);
        return dateFormat.format(date);
    }

    public static Date strToDate(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strToYYMMDDDate(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strToYYMMDate(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYYMM);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strToSimpleYYMMDDDate(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYYMMDD);
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toDateYYYYMMDDHHMM(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strDateYYYYMMDDHHMM(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMM);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date yyyyMMddHHmmssStrToDate(String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMMSS);
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //获取系统时间并返回时间格式
    public static Date currentDate() {
//        Date date = null;
//        try {
//            String currentDates = YYYY_MM_DD_HH_MM_SS.format(new Date());
//            date = YYYY_MM_DD_HH_MM_SS.parse(currentDates);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
        return new Date(System.currentTimeMillis());
    }

    public static Date timeBackward(Date dt, int hr, int mi, int se) {
		long ctm = dt.getTime() - hr * 1000 * 60 * 60 - mi * 1000 * 60 - se * 1000;
		return new Date(ctm);
    }

    public static Date timeForward(Date dt, int hr, int mi, int se) {
        long ctm = dt.getTime() + hr * 1000 * 60 * 60 + mi * 1000 * 60 + se * 1000;
        return new Date(ctm);
    }

    /**
     * 得到当前时间距2013-11-01 00:00:00的小时数
     *
     * @return
     * @throws ParseException
     */
    public long getHours() {
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simple.parse("2013-11-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millisecond = System.currentTimeMillis() - date.getTime();
        long temp = 1000 * 60 * 60;
        return millisecond / temp;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 计算某个日期与当前时间的天数
     *
     * @param paramDate   较小的对比时间
     * @param currentData 当前时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetweenCurrent(String paramDate, Date currentData) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date paramdate = null;
        try {
            paramdate = format.parse(paramDate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(paramdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(currentData);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算某两个日期的天数
     */
    public static int daysBetweenDate(String startDate, String endData) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate = null;
        Date enddate = null;
        try {
            startdate = format.parse(startDate);
            enddate = format.parse(endData);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(startdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(enddate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * 计算两个时间之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        //一天的毫秒数1000 * 60 * 60 * 24=86400000
        days = (end - start) / 86400000;
        return days;
    }

    /**
     * 计算两个时间之间相差的天数(向上取整)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDaysCeil(Date startDate, Date endDate) {
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        //一天的毫秒数1000 * 60 * 60 * 24=86400000
        days = (long) Math.ceil((end - start) / 86400000.0);
        return days;
    }

    /**
     * 两个日期相差天数是否大于给定时间
     *
     * @param startDate 开始日期
     * @param days      给定的天数
     * @return
     */
    public static boolean compareCurDays(Date startDate, int days) {

        long betweenDays = diffDaysCeil(startDate, new Date());

        return betweenDays > days ? true : false;
    }

    /**
     * 计算两个时间之间相差的秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffSeconds(Date startDate, Date endDate) {
        long seconds = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        seconds = Math.abs((end - start) / 1000);
        return seconds;
    }

    /**
     * 计算两个时间之间相差的秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffSecondsWithoutAbs(Date startDate, Date endDate) {
        long seconds = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        seconds = (end - start) / 1000;
        return seconds;
    }

    /**
     * 日期加上月数的时间
     *
     * @param date
     * @param month
     * @return
     */
    public static Date dateAddMonth(Date date, int month) {
        return add(date, Calendar.MONTH, month);
    }

    /**
     * 日期加上天数的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    /**
     * 日期加上年数的时间
     *
     * @param date
     * @param year
     * @return
     */
    public static Date dateAddYear(Date date, int year) {
        return add(date, Calendar.YEAR, year);
    }

    /**
     * 计算剩余时间 (多少天多少时多少分)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static String remainDateToString(Date startDate, Date endDate) {
        StringBuilder result = new StringBuilder();
        if (endDate == null) {
            return "过期";
        }
        long times = endDate.getTime() - startDate.getTime();
        if (times < -1) {
            result.append("过期");
        } else {
            long temp = 1000 * 60 * 60 * 24;
            //天数
            long d = times / temp;

            //小时数
            times %= temp;
            temp /= 24;
            long m = times / temp;
            //分钟数
            times %= temp;
            temp /= 60;
            long s = times / temp;

            result.append(d);
            result.append("天");
            result.append(m);
            result.append("小时");
            result.append(s);
            result.append("分");
        }
        return result.toString();
    }

    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }


    /**
     * @MethodName: getLinkUrl
     * @Param: DateUtil
     * flag ： true 转换  false 不转换
     * @Author: gang.lv
     * @Date: 2013-5-8 下午02:52:44
     * @Return:
     * @Descb:
     * @Throws:
     */
    public static String getLinkUrl(boolean flag, String content, String id) {
        if (flag) {
            content = "<a href='finance.do?id=" + id + "'>" + content + "</a>";
        }
        return content;
    }

    /**
     * 时间转换为时间戳
     *
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static long getTimeCur(String format, String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * 时间转换为时间戳
     *
     * @param format
     * @param date
     * @return
     * @throws ParseException
     */
    public static long getTimeCur(String format, Date date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(sf.format(date)).getTime();
    }

    /**
     * 将时间戳转为字符串
     *
     * @param cc_time
     * @return
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    public static int getAge(Date birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthday);
        Calendar nowDate = Calendar.getInstance();

        return nowDate.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
    }

    /**
     * 当前时间减去分钟数得到的时间
     *
     * @param minutes
     * @return
     * @throws ParseException
     */
    public static String getDateMinusMinutes(int minutes) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime = new Date();
        String datetest = formatter.format(nowTime);
        Date date = formatter.parse(datetest);
        long Time1 = (date.getTime() / 1000) - 60 * minutes;//减去30分
        date.setTime(Time1 * 1000);
        return formatter.format(date);
    }

    /**
     * 指定时间减去分钟数得到的时间
     * @param dt
     * @param minutes
     * @return
     * @throws ParseException
     */
    public static Date getDateMinusMinutes(Date dt ,int minutes){
        Date date=null;
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String datetest = formatter.format(dt);
            date = formatter.parse(datetest);
            long Time1 = (date.getTime() / 1000) - 60 * minutes;
            date.setTime(Time1 * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 和当前时间比较是否在给定的时长内
     *
     * @param validTime 给定的时间
     * @param time      给定的时长（s）
     * @return true 有效 false 无效
     */
    public static boolean inValidTime(Date validTime, int time) {
        long currTime = System.currentTimeMillis();
        long valid = validTime.getTime();

        return ((currTime - valid) < time * 1000);
    }

    /**
     * 获取年
     *
     * @param time
     * @return
     */
    public static int getYear(Date time) {
        if (time == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @param time
     * @return
     */
    public static int getMonth(Date time) {
        if (time == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @param time
     * @return
     */
    public static int getDay(Date time) {
        if (time == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取本月第一天
     *
     * @return
     */
    public static Date getFirstDayOfThisMonth() {
        return getFirstDayOfMonth(new Date());
    }

    /**
     * 获取上个月第一天
     *
     * @return
     */
    public static Date getFirstDayOfLastMonth(Date curDate) {
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTime(curDate);
        ca.set(ca.get(GregorianCalendar.YEAR), ca.get(GregorianCalendar.MONTH), 1, 0, 0, 0);
        Date date = ca.getTime();
        Date firstDayOfLastMonth = dateAddMonth(date, -1);
        return firstDayOfLastMonth;
    }

    /**
     * 获取指定日期所在月第一天
     *
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        String firstDayOfMonthString = dateToString1(ca.getTime());
        return strToYYMMDDDate(firstDayOfMonthString);
    }

    /**
     * 获取指定年月第一天
     * e.g. 2015, 6 => 2015-06-01 00:00:00
     *
     * @return
     */
    public static Date getFirstDayByYearMonth(int year, int month) {
        GregorianCalendar ca = new GregorianCalendar();
        ca.set(year, month - 1, 1, 0, 0, 0);
        Date date = ca.getTime();
        return date;
    }

    /**
     * 获取下个月的第一天
     * e.g. 2015, 12 => 2016-01-01 00:00:00
     *
     * @return
     */
    public static Date getFirstDayOfNextMonth(int year, int month) {
        GregorianCalendar ca = new GregorianCalendar();
        ca.set(year, month - 1, 1, 0, 0, 0);
        Date date = ca.getTime();
        Date firstDayOfNextMonth = dateAddMonth(date, 1);
        return firstDayOfNextMonth;
    }
	
	/*public static void main(String[] args) {
//		Date firstDayOfThisMonth = getFirstDayOfThisMonth();
//		System.out.println(dateToString(firstDayOfThisMonth));
//		
//		Date date1 = getFirstDayByYearMonth(2015, 12);
//		System.out.println(dateToString(date1));
//		
//		Date date2 = getFirstDayOfNextMonth(2015, 12);
//		System.out.println(dateToString(date2));
		
		int day2= daysBetweenDate("2016-04-20", "2016-04-22");
		
		getCurrentDate();
		
		Date now = new Date();
		System.out.println(dateToString(now));
		Date d = strToDate("2016-04-14 16:23:00");
		long t = diffSeconds(d, now);
		System.out.println(t);
		Date beginningOfDay = getBeginningOfDay(now);
		System.out.println(dateToString(beginningOfDay));
		Date endOfDay = getEndOfDay(now);
		System.out.println(dateToString(endOfDay));
		
		System.out.println(dateToString(deadlineTime(3,new Date())));
		
		System.out.println(dateToString(getEndOfDay(dateAddDay(new Date(),7))));
	}*/

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(getFirstDayOfLastMonth(now));
    }

    /**
     * T+n返回时间
     */
    public static Date deadlineTime(int n, Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        Calendar toDate = addDateByWorkDay(calendar, n);
        Date endTime = toDate.getTime();
        //String  endTime=DateUtil.dateToString(toDate.getTime());
        //System.out.print(endTime);
        return endTime;

    }


    private static List<Calendar> holidayList;

    private static boolean holidayFlag;

    /**
     * 计算工作日
     * <p>
     * 具体节日包含哪些,可以在HolidayMap中修改
     *
     * @param src     日期(源)
     * @param adddays 要加的天数
     * @throws throws [违例类型] [违例说明]
     */

    public static Calendar addDateByWorkDay(Calendar src, int adddays)

    {


//	        Calendar result = null;

        holidayFlag = false;

        for (int i = 0; i < adddays; i++)

        {

            //把源日期加一天

            src.add(Calendar.DAY_OF_MONTH, 1);

            holidayFlag = checkHoliday(src);
            //如果是节假
            if (holidayFlag)

            {

                i--;

            }

//	            System.out.println(src.getTime());

        }

//	        System.out.println("Final Result:"+src.getTime());

        return src;

    }

    /**
     * 校验指定的日期是否在节日列表中
     * <p>
     * 具体节日包含哪些,可以在HolidayMap中修改
     *
     * @param src 要校验的日期(源)
     */

    public static boolean checkHoliday(Calendar src)

    {

        boolean result = false;

        if (holidayList == null)

        {

            initHolidayList();

        }

        //先检查是否是周六周日(有些国家是周五周六)

        if (src.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY

                || src.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)

        {

            return true;

        }

        for (Calendar c : holidayList)

        {

            if (src.get(Calendar.MONTH) == c.get(Calendar.MONTH)

                    && src.get(Calendar.DAY_OF_MONTH) == c.get(Calendar.DAY_OF_MONTH))

            {

                result = true;

            }

        }

        return result;

    }


    /**
     * 初始化节日List,如果需要加入新的节日,请在这里添加
     * <p>
     * 加的时候请尽量使用Calendar自带的常量而不是魔鬼数字
     * <p>
     * 注:年份可以随便写,因为比的时候只比月份和天
     */

    private static void initHolidayList()

    {

        holidayList = new ArrayList();

        //国庆节

        Calendar may1 = Calendar.getInstance();

        may1.set(Calendar.MONTH, Calendar.OCTOBER);

        may1.set(Calendar.DAY_OF_MONTH, 1);

        holidayList.add(may1);


        Calendar may2 = Calendar.getInstance();

        may2.set(Calendar.MONTH, Calendar.OCTOBER);

        may2.set(Calendar.DAY_OF_MONTH, 2);

        holidayList.add(may2);


        Calendar may3 = Calendar.getInstance();

        may3.set(Calendar.MONTH, Calendar.OCTOBER);

        may3.set(Calendar.DAY_OF_MONTH, 3);

        holidayList.add(may3);

    }

    /**
     * 获取指定日期的开始
     * e.g. 2015/12/15 10:14:11 => 2015/12/15 00:00:00
     */
    public static Date getBeginningOfDay(Date now) {
        String beginningStr = simple(now);
        Date beginningOfDay = strToSimpleYYMMDDDate(beginningStr);
        return beginningOfDay;
    }

    /**
     * 获取指定日期的结束
     * e.g. 2015/12/15 10:14:11 => 2015/12/15 23:59:59
     */
    public static Date getEndOfDay(Date now) {
        String beginningStr = simple(now);
        beginningStr += "235959";
        Date endOfDay = yyyyMMddHHmmssStrToDate(beginningStr);
        return endOfDay;
    }

    /**
     * 获取昨天
     */
    public static Date getYesterday() {
        Date now = new Date();
        Date yesterday = dateAddDay(getBeginningOfDay(now), -1);
        return yesterday;
    }

    /***
     * @author scofield
     * @created 2016年4月25日 下午3:17:15
     * @decription 获得当前日期
     */
    public static String getCurrentDate() {
        String currDate = "";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        currDate = format.format(date);
        return currDate;
    }

    /***
     * @author scofield
     * @created 2016年4月25日 下午3:17:15
     * @decription 获得当前日期的前30天
     */
    public static String getBeforeDays() {
        String beforeDays = "";
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.add(Calendar.DATE, -30);
        beforeDays = c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE);
        return beforeDays;
    }

    public static int compare(Date ldate, Date rdate) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(ldate);
        c2.setTime(rdate);
        return c1.compareTo(c2);
    }

    public static int getDayOfWeek(Date day) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(day);
        int d = c1.get(Calendar.DAY_OF_WEEK) - 1;
        return d == 0 ? 7 : d;
    }

    public static Date addDateHours(Date date, int i) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        c1.add(Calendar.HOUR_OF_DAY, 12);
        return c1.getTime();
    }

    public static Date toShortDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            System.out.println(sdf.format(date));
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
