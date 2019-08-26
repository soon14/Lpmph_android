package com.ailk.pmph.utils;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	public static final String HHMMSS = "HHmmss";

	/*************************************** 公共方法 **************************/
	/**
	 * @Title: getCurrentTimeMillis
	 * @Description:获取当前时间戳
	 * @author:
	 * @Create: 2014年12月18日 上午11:24:26
	 * @Modify: 2014年12月18日 上午11:24:26
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static long getCurrentTimeMillis() throws Exception {
		Timestamp time = getTimeStampNow();
		return time.getTime();
	}

	/**
	 * @Title: getTimeStampNumberFormat
	 * @Description:格式化时间 Locale是设置语言敏感操作
	 * @author:
	 * @Create: 2014年12月23日 下午5:15:01
	 * @Modify: 2014年12月23日 下午5:15:01
	 * @param:
	 * @return:
	 */
	public static String getTimeStampNumberFormat(Timestamp time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
		return format.format(time);
	}

	public static String getTimeStamp(Timestamp time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", new Locale("zh", "cn"));
		return format.format(time);
	}

	public static String getTimeStampString(java.sql.Date time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(time);
	}

	/**
	 * @Title: isValidTime
	 * @Description:判断时间是否符合格式要求
	 * @author:
	 * @Create: 2014年12月19日 下午6:49:42
	 * @Modify: 2014年12月19日 下午6:49:42
	 * @param:
	 * @return:
	 */
	public static boolean isValidTime(String str, String pattern) {
		boolean flag = true;
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			format.setLenient(false);
			format.parse(str);
			// System.out.println(format.parse(str));
			flag = true;
		} catch (ParseException e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * @Title: getDaysOfMonth
	 * @Description:获取当月总天数
	 * @author:
	 * @Create: 2014年12月23日 下午4:30:19
	 * @Modify: 2014年12月23日 下午4:30:19
	 * @param:
	 * @return:
	 */
	public static int getDaysOfMonth() throws Exception {
		Timestamp currTimestamp = DateUtils.getTimeStampNow();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currTimestamp);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @Title: isDateType
	 * @Description:验证日期格式 yyyyMM、yyyyMMdd、yyyyMMddHHmmss
	 * @author:
	 * @Create: 2014年12月23日 下午4:33:31
	 * @Modify: 2014年12月23日 下午4:33:31
	 * @param:
	 * @return:
	 */
	public static boolean isDateType(String time) {
		String isYYYYMMDDHHSS = "^\\d{4}([1-9]|(1[0-2])|(0[1-9]))([1-9]|([12]\\d)|(3[01])|(0[1-9]))(([0-1][0-9])|([2][0-3]))([0-5][0-9])([0-5][0-9])$";
		String isYYYYMMDD = "^\\d{4}([1-9]|(1[0-2])|(0[1-9]))([1-9]|([12]\\d)|(3[01])|(0[1-9]))$";
		String isYYYYMM = "^\\d{4}((1[0-2])|(0[1-9]))$";
		if (StringUtils.isEmpty(time))
			return false;
		if (time.length() == 6)
			return time.matches(isYYYYMM);

		if (time.length() == 8)
			return time.matches(isYYYYMMDD);

		if (time.length() == 14)
			return time.matches(isYYYYMMDDHHSS);

		return false;
	}

	/**
	 * @Title: getTimeDifferenceMonth
	 * @Description:计算两个日期的时间差(月)
	 * @author:
	 * @Create: 2014年12月23日 下午5:00:54
	 * @Modify: 2014年12月23日 下午5:00:54
	 * @param:
	 * @return:
	 */
	public static int getBetweenMonths(Timestamp time1, Timestamp time2) throws Exception {
		Date d1 = DateUtils.getDate(time1);
		Date d2 = DateUtils.getDate(time2);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);// 年差
		int m = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);// / 月差
		int d = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);// 天差

		if (d > 0) {
			// 如果天数差大于零
			return (y * 12 + m + 1);
		} else {
			return (y * 12 + m);
		}
	}

	/**
	 * @Title: getTimeDifference
	 * @Description:计算两个日期的时间差(天)
	 * @author:
	 * @Create: 2014年12月23日 下午5:04:49
	 * @Modify: 2014年12月23日 下午5:04:49
	 * @param:
	 * @return:
	 */
	public static long getBetweenDays(Timestamp time1, Timestamp time2) {
		SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
		long t1 = 0L;
		long t2 = 0L;
		try {
			t1 = timeformat.parse(getTimeStampNumberFormat(time1)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			t2 = timeformat.parse(getTimeStampNumberFormat(time2)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 毫秒ms
		long diff = t1 - t2;
		long diffDays = 0;
		if (diff > 0) {
			diffDays = diff / (24 * 60 * 60 * 1000);
		} else {
			diffDays = diff / (24 * 60 * 60 * 1000) - 1;
		}
		return diffDays;
	}

	/**
	 * @Title: isBetweenTime
	 * @Description:验证的时间（checkTime）是否在 startTime 和 endTime 之间
	 * @author:
	 * @Create: 2014年12月23日 下午5:03:13
	 * @Modify: 2014年12月23日 下午5:03:13
	 * @param:
	 * @return:
	 */
	public static boolean isBetweenTime(Timestamp startTime, Timestamp endTime, Timestamp checkTime) {

		// 如果checkTime 在当前 startTime 和 endTime 之间，那么返回 true;
		if (checkTime.compareTo(startTime) >= 0 && checkTime.compareTo(endTime) <= 0) {
			return true;
		}

		return false;
	}

	/*************************************** 以下为java.sql.Timestamp型的输入或者输出参数 ****************/

	/**
	 * @Title: getTimeStampNow
	 * @Description:
	 * @author:
	 * @Create: 2014年12月18日 上午11:10:24
	 * @Modify: 2014年12月18日 上午11:10:24
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static Timestamp getTimeStampNow() throws Exception {
		try {
			return new Timestamp(Calendar.getInstance().getTimeInMillis());
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * @Title: getFormatTimeNow
	 * @Description:格式化当前日期
	 * @author:
	 * @Create: 2014年12月18日 上午11:11:37
	 * @Modify: 2014年12月18日 上午11:11:37
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static String getFormatTimeNow(String pattern) throws Exception {
		Timestamp time = getTimeStampNow();
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = time;
		return dateFormat.format(date);
	}

	/**
	 * @Title: getFormatTime
	 * @Description:对输入Timestamp型的时间作格式化
	 * @author:
	 * @Create: 2014年12月18日 上午11:15:16
	 * @Modify: 2014年12月18日 上午11:15:16
	 * @param:
	 * @return:
	 */
	public static String getFormatTime(Timestamp time, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Date date = time;
		return date != null ? dateFormat.format(date) : "";
	}

	/**
	 * @Title: getTimestamp
	 * @Description:将指定格式的日期字符串转成Timestamp
	 * @author:
	 * @Create: 2014年12月22日 下午5:45:57
	 * @Modify: 2014年12月22日 下午5:45:57
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static Timestamp getTimestamp(String time, String pattern) throws Exception {
		if (!isValidTime(time, pattern)) {
			throw new Exception("传入的时间" + time + "不符合格式" + pattern);
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);
		Timestamp ts = null;
		try {
			ts = new Timestamp(format.parse(time).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * @Title: getMonth
	 * @Description:从字符串中获取月份
	 * @author:
	 * @Create: 2014年12月23日 下午4:42:02
	 * @Modify: 2014年12月23日 下午4:42:02
	 * @param:
	 * @return:
	 */
	public static int getMonth(String time) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getTimestamp(time, DATE_FORMAT));
		return calendar.get(Calendar.MONTH) + 1;

	}

	/**
	 * @Title: getAddTimestamp
	 * @Description:获取指定时间的偏移天数后的时间
	 * @author:
	 * @Create: 2014年12月23日 下午3:33:07
	 * @Modify: 2014年12月23日 下午3:33:07
	 * @param:
	 * @return:
	 */
	public static Timestamp getAddTimeByDay(Timestamp time, int addDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DAY_OF_MONTH, addDays);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getAddTimeByMonth
	 * @Description:获取指定时间的偏移月份后的时间
	 * @author:
	 * @Create: 2014年12月23日 下午4:12:24
	 * @Modify: 2014年12月23日 下午4:12:24
	 * @param:
	 * @return:
	 */
	public static Timestamp getAddTimeByMonth(Timestamp time, int addMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, addMonths);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getAddTimeByYear
	 * @Description:获取指定时间的偏移年份后的时间
	 * @author:
	 * @Create: 2014年12月23日 下午4:16:04
	 * @Modify: 2014年12月23日 下午4:16:04
	 * @param:
	 * @return:
	 */
	public static Timestamp getAddTimeByYear(Timestamp time, int addYears) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.YEAR, addYears);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 
	 * @Title: getFirstTimeStamp
	 * @Description:获取某一日零点Timestamp时间
	 * @author:
	 * @Create: 2014年12月22日 下午5:32:06
	 * @Modify: 2014年12月22日 下午5:32:06
	 * @param:
	 * @return:
	 */
	public static Timestamp getFirstTimeStamp(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getTheDayLastSecond
	 * @Description:获取一天的最后一秒
	 * @author:
	 * @Create: 2014年12月23日 下午3:55:31
	 * @Modify: 2014年12月23日 下午3:55:31
	 * @param:
	 * @return:
	 */
	public static Timestamp getLastTimeStamp(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, -1);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getMonthLastSecond
	 * @Description:获取本月最后一秒
	 * @author:
	 * @Create: 2014年12月23日 下午4:25:44
	 * @Modify: 2014年12月23日 下午4:25:44
	 * @param:
	 * @return:
	 */
	public static Timestamp getMonthLastSecond(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, -1);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getMonthFirstSecond
	 * @Description:获取本月第一秒
	 * @author:
	 * @Create: 2014年12月23日 下午4:24:12
	 * @Modify: 2014年12月23日 下午4:24:12
	 * @param:
	 * @return:
	 */
	public static Timestamp getMonthFirstSecond(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getNextMonthFirstSecond
	 * @Description:获取下月第一秒
	 * @author:
	 * @Create: 2014年12月23日 下午4:26:19
	 * @Modify: 2014年12月23日 下午4:26:19
	 * @param:
	 * @return:
	 */
	public static Timestamp getNextMonthFirstSecond(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getTimeThisYearFirstSec
	 * @Description:获取本年第一秒
	 * @author:
	 * @Create: 2014年12月23日 下午4:27:12
	 * @Modify: 2014年12月23日 下午4:27:12
	 * @param:
	 * @return:
	 */
	public static Timestamp getYearFirstSeccond(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getWeekFirstSecond
	 * @Description:获取本周第一秒
	 * @author:
	 * @Create: 2014年12月23日 下午4:28:05
	 * @Modify: 2014年12月23日 下午4:28:05
	 * @param:
	 * @return:
	 */
	public static Timestamp getWeekFirstSecond(Timestamp time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.WEEK_OF_MONTH, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * @Title: getBeforeSecond
	 * @Description:当前时间前一秒
	 * @author:
	 * @Create: 2014年12月19日 下午6:51:58
	 * @Modify: 2014年12月19日 下午6:51:58
	 * @param:
	 * @return:
	 */
	public static Timestamp getBeforeSecond(Timestamp time) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(time);
		calender.add(Calendar.SECOND, -1);
		return new Timestamp(calender.getTimeInMillis());
	}

	/**
	 * @Title: getAfterSecond
	 * @Description: 当前时间后一秒
	 * @author:
	 * @Create: 2014年12月23日 下午4:37:12
	 * @Modify: 2014年12月23日 下午4:37:12
	 * @param:
	 * @return:
	 */
	public static Timestamp getAfterSecond(Timestamp time) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(time);
		calender.add(Calendar.SECOND, 1);
		return new Timestamp(calender.getTimeInMillis());
	}

	/**
	 * @Title: getMondayDate
	 * @Description:获得参数时间的周一时间.如果参数为空null,那么返回当前系统时间的周一日期
	 * @author: luocan
	 * @Create: 2014年12月23日 下午5:13:39
	 * @Modify: 2014年12月23日 下午5:13:39
	 * @param:
	 * @return:
	 */
	public static Timestamp getMondayDate(Timestamp time) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (null != time) {
			cal.clear();
			Date d = DateUtils.getDate(time);
			cal.setTime(d);
		}
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		// System.out.println("********得到本周一的日期*******"+df.format(cal.getTime()));
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * @Title: getSundayDate
	 * @Description:获得参数时间的周日时间.如果参数为空null,那么返回当前系统时间的周日日期
	 * @author:
	 * @Create: 2014年12月23日 下午5:13:03
	 * @Modify: 2014年12月23日 下午5:13:03
	 * @param:
	 * @return:
	 */
	public static Timestamp getSundayDate(Timestamp time) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (null != time) {
			cal.clear();
			Date d = DateUtils.getDate(time);
			cal.setTime(d);
		}
		// 这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return new Timestamp(cal.getTimeInMillis());
	}

	/*********************************************** 以下为java.sql.Date型的输入或输出参数 *****************************/

	/**
	 * @Title: getFormatTime
	 * @Description:根据Date获取固定格式的日期
	 * @author:
	 * @Create: 2014年12月18日 上午11:22:58
	 * @Modify: 2014年12月18日 上午11:22:58
	 * @param:
	 * @return:
	 */
	public static String getFormatTime(Date date, String pattern) {
		SimpleDateFormat sdfmt = new SimpleDateFormat(pattern);
		return date != null ? sdfmt.format(date) : "";
	}

	/**
	 * @Title: getDate
	 * @Description:按指定格式将字符串转换为日期对象
	 * @author:
	 * @Create: 2014年12月19日 下午6:55:13
	 * @Modify: 2014年12月19日 下午6:55:13
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static Date getDate(String time, String pattern) throws Exception {
		if (!isValidTime(time, pattern)) {
			throw new Exception("传入的时间" + time + "不符合格式" + pattern);
		}
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			Date date = df.parse(time);
			Date d = new Date(date.getTime());
			return d;
		} catch (Exception e) {
			throw new Exception("系统转换日期字符串时出错！", e);
		}
	}

	/**
	 * @Title: getDate
	 * @Description:获取Date型当前日期
	 * @author:
	 * @Create: 2014年12月19日 下午6:55:53
	 * @Modify: 2014年12月19日 下午6:55:53
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static Date getDate() throws Exception {
		Date date = getTimeStampNow();
		Date d = new Date(date.getTime());
		return d;
	}

	/**
	 * @Title: getDate
	 * @Description:Timestamp型的日期转化为yyyy-MM-dd HH:mm:ss格式的date日期
	 * @author:
	 * @Create: 2014年12月19日 下午6:57:39
	 * @Modify: 2014年12月19日 下午6:57:39
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static Date getDate(Timestamp time) throws Exception {
		Date date = time;
		Date d = new Date(date.getTime());
		return d;
	}

	/**
	 * @Title: getAddDateByDay
	 * @Description:获取指定时间点偏移天数后的日期
	 * @author:
	 * @Create: 2014年12月22日 下午5:29:32
	 * @Modify: 2014年12月22日 下午5:29:32
	 * @param:
	 * @return:
	 * @throws Exception
	 */
	public static Date getAddDateByDay(Timestamp time, int addDays) throws Exception {
		Timestamp t = getAddTimeByDay(time, addDays);
		Date d = getDate(t);
		return d;
	}

	/**
	 * @Title: getAddDateByDay
	 * @Description:根据Date型日期取得偏移量后的日期
	 * @author:
	 * @Create: 2014年12月22日 下午5:31:21
	 * @Modify: 2014年12月22日 下午5:31:21
	 * @param:
	 * @return:
	 */
	public static Date getAddDateByDay(Date time, int addDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DAY_OF_MONTH, addDays);
		return new Date(calendar.getTimeInMillis());
	}
	
	

	public static void main(String[] args) throws Exception {

		System.out.println("将Timestamp型时间转化为Date型时间：" + getDate(new Timestamp(System.currentTimeMillis())));

		System.out.println("获取当前Timestamp的" + getTimeStampNow());

		System.out.println("将字符串按照固定的格式得到Timestamp型时间：" + getTimestamp("2014-12-22 17:54:58", DATE_FORMAT));

		System.out.println("获取当前Date型时间：  " + getDate());

		System.out.println("获取11天后的Timestamp时间：  " + getAddTimeByDay(getTimeStampNow(), 11));
		System.out.println("获取11月后的Timestamp时间：  " + getAddTimeByMonth(getTimeStampNow(), 11));
		System.out.println("获取11年后的Timestamp时间：  " + getAddTimeByYear(getTimeStampNow(), 11));

		System.out.println("获取11天后的Date时间：  " + getAddDateByDay(getDate(), 11));

		System.out.println("获取今天的零点Timestamp时间： " + getFirstTimeStamp(getTimeStampNow()));

		System.out.println("获取今天最后一秒Timestamp时间： " + getLastTimeStamp(getTimeStampNow()));

		System.out.println("当月天数：" + getDaysOfMonth());

		System.out.println("从字符串中获取月份： " + getMonth("2014-12-23 16:31:26.209"));

		System.out.println("获取两个时间的月份之差：" + getBetweenMonths(getTimeStampNow(), getAddTimeByMonth(getTimeStampNow(), 11)));

	}
}
