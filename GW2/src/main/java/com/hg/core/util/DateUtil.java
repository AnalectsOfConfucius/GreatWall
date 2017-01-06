package com.hg.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Title: 用来转换时间的工具类
 * 
 * Description: 用来转换时间的工具类
 * 
 */
public class DateUtil {

	public static String formatDate(long mms, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date dt = new Date(mms);
		return sdf.format(dt);
	}

	/**
	 * 字符串转日期
	 * 
	 * @param strDate
	 *            字符串日期
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static Date strToDate(String strDate, String format) {
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(strDate);
		} catch (ParseException ex) {
		}
		return null;
	}

	/**
	 * 时间转换方法
	 *
	 * @param date
	 *            传入一个String的时间
	 * @return 获得年月日的时间(yyyy-MM-dd)
	 */
	public static String formatDate(Date date) {
		// 保存改后的时间

		String ymd = " ";
		if (date == null || "".equals(date) || "1900-01-01 00:00:00.0".equals(date)) {
			return ymd;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ymd = dateFormat.format(date);
		return ymd;
	}

	/**
	 * 时间转换方法
	 *
	 * @param date
	 *            传入一个String的时间
	 * @return 获得一个指定格式的时间(yyyy-MM-dd)
	 */
	public static String formatDate(Date date, String fmt) {
		// 保存改后的时间

		String ymd = " ";
		if (date == null || "".equals(date) || "1900-01-01 00:00:00.0".equals(date)) {
			return ymd;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		ymd = dateFormat.format(date);
		return ymd;
	}

	/**
	 * 获得给定时间的月分的会后一天
	 *
	 * @param calendar
	 * @return 天数
	 */
	public int monthLastDay(GregorianCalendar calendar) {

		int lastDay = 0;
		GregorianCalendar gregorian = calendar;
		int month = gregorian.get(Calendar.MONTH);
		do {
			lastDay = gregorian.get(Calendar.DAY_OF_MONTH);
			gregorian.add(Calendar.DAY_OF_MONTH, 1);
		} while (gregorian.get(Calendar.MONTH) == month);
		return lastDay;
	}

	/**
	 * 返回这个月的最大Date
	 *
	 * @return YYYY-MM-dd
	 */
	public String getMonthLastDaDate() {
		StringBuffer datetime = new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		datetime.append(calendar.get(Calendar.YEAR));
		datetime.append("-");
		if (calendar.get(Calendar.MONTH) < 9) {
			datetime.append("0");
		}
		datetime.append((calendar.get(Calendar.MONTH) + 1));
		datetime.append("-");
		datetime.append(monthLastDay(calendar));
		return datetime.toString();
	}

	/**
	 * 返回这个月的最小Date
	 *
	 * @return YYYY-MM-dd
	 */
	public String getMonthLeastDaDate() {
		StringBuffer datetime = new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		datetime.append(calendar.get(Calendar.YEAR));
		datetime.append("-");
		if (calendar.get(Calendar.MONTH) < 9) {
			datetime.append("0");
		}
		datetime.append((calendar.get(Calendar.MONTH) + 1));
		datetime.append("-");
		datetime.append("01");
		return datetime.toString();
	}

	/**
	 * 获得当前日期YYYY-MM-dd
	 *
	 * @return YYYY-MM-dd
	 */
	public String getFormatDate() {
		StringBuffer datetime = new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		datetime.append(calendar.get(Calendar.YEAR));
		datetime.append("-");
		if (calendar.get(Calendar.MONTH) < 9) {
			datetime.append("0");
		}
		datetime.append((calendar.get(Calendar.MONTH) + 1));
		datetime.append("-");
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			datetime.append("0");
		}
		datetime.append(calendar.get(Calendar.DAY_OF_MONTH));
		return datetime.toString();
	}

	/**
	 * 获得 XXXX年XX月XX日XX分XX秒 格式的的时间
	 *
	 * @return
	 */
	public String getFormatTime() {
		StringBuffer datetime = new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		datetime.append(calendar.get(Calendar.YEAR));
		datetime.append("年");
		datetime.append((calendar.get(Calendar.MONTH) + 1));
		datetime.append("月");
		datetime.append(calendar.get(Calendar.DAY_OF_MONTH));
		datetime.append("日");
		datetime.append(calendar.get(Calendar.MINUTE));
		datetime.append("分");
		datetime.append(calendar.get(Calendar.SECOND));
		datetime.append("秒");
		return datetime.toString();
	}

	/**
	 * 获得 当前日期YYYYMMdd
	 *
	 * @return
	 */
	public static String getDate() {
		StringBuffer datetime = new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		datetime.append(calendar.get(Calendar.YEAR));
		if (calendar.get(Calendar.MONTH) < 9) {
			datetime.append("0");
		}
		datetime.append((calendar.get(Calendar.MONTH) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			datetime.append("0");
		}
		datetime.append(calendar.get(Calendar.DAY_OF_MONTH));
		return datetime.toString();
	}

	/**
	 * 获得当前时间 HH:MM
	 *
	 * @return
	 */
	public String getTime() {
		StringBuffer datetime = new StringBuffer();
		GregorianCalendar calendar = new GregorianCalendar();
		datetime.append(calendar.get(Calendar.HOUR_OF_DAY));
		datetime.append(":");
		datetime.append(calendar.get(Calendar.MINUTE));
		return datetime.toString();
	}

	/**
	 * 获得当前时间 HHmmss
	 *
	 * @return
	 */
	public static String getFullTimeNotDate() {
		return getCurrentDateTime("HHmmss");
	}

	/**
	 * 获得当前时间 HH:mm:ss
	 *
	 * @return
	 */
	public static String getFullTimeByTwoPonit() {
		return getCurrentDateTime("HH:mm:ss");
	}

	/**
	 * 获得当前时间 yyyyMMdd
	 *
	 * @return
	 */
	public static String getFullDate() {
		return getCurrentDateTime("yyyyMMdd");
	}

	public static String getFullDate2() {
		return getCurrentDateTime("yyMMdd");
	}

	/**
	 * 获得当前时间 yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getFullDate_() {
		return getCurrentDateTime("yyyy-MM-dd");
	}

	/**
	 * 根据格式返回当前日期时间
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 返回星期
	 *
	 * @return
	 */
	public static String getWeekay() {
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String week_day = dayNames[day - 1];
		return week_day;
	}

	/**
	 * 返回明天星期
	 *
	 * @return
	 */
	public static String getTomorrowWeekay() {
		String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		String week_day = dayNames[day];
		return week_day;
	}

	public static String getTime3() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return df.format(cal.getTime());
	}

	public static String getDateDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		return df.format(new Date());
	}

	public static String dateFormat(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 返回YYYY-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String getStrDate(String date) {
		String y = date.substring(0, 4);
		String m = date.substring(4, 6);
		String d = date.substring(6, 8);
		StringBuffer newDate = new StringBuffer();
		newDate.append(y).append("-").append(m).append("-").append(d);
		return newDate.toString();
	}

	/**
	 * 返回YYYY-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String getStrDateTime(String date) {
		String y = date.substring(0, 4);
		String m = date.substring(4, 6);
		String d = date.substring(6, 8);
		StringBuffer newDate = new StringBuffer();
		newDate.append(y).append("-").append(m).append("-").append(d).append(" ");
		String h = date.substring(8, 10);
		String mm = date.substring(10, 12);
		String s = date.substring(12, 14);
		newDate.append(h).append(":").append(mm).append(":").append(s);
		return newDate.toString();
	}

	/**
	 * 返回HH:mm:ss
	 *
	 * @param date
	 * @return
	 */
	public static String getStrtime(String date) {
		String y = date.substring(0, 2);
		String m = date.substring(2, 4);
		String d = date.substring(4, 6);
		StringBuffer newDate = new StringBuffer();
		newDate.append(y).append(":").append(m).append(":").append(d);
		return newDate.toString();
	}

	/**
	 * 判断是否为现在日期
	 *
	 * @param date
	 * @return
	 */
	public static boolean isCurrent(String date) {
		boolean flag = true;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date dt = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			Calendar currentDate = Calendar.getInstance();
			if (currentDate.get(Calendar.YEAR) != cal.get(Calendar.YEAR) || currentDate.get(Calendar.MONTH) != cal.get(Calendar.MONTH) || currentDate.get(Calendar.DATE) != cal.get(Calendar.DATE)) {
				flag = false;
			}
		} catch (ParseException e) {
		}
		return flag;
	}

	public static Calendar stringToCalendar(String date) {
		Calendar cal = Calendar.getInstance();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date dt = df.parse(date);
			cal.setTime(dt);
		} catch (ParseException e) {
		}
		return cal;
	}

	public static String calendarToString(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(cal.getTime());
	}

	public static String calendarToString_(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(cal.getTime());
	}

	public static String calendarToFullString_(Calendar cal) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取完整的时间日期
	 *
	 * @return
	 */
	public static String getFullTime() {
		return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
	}

	public static String getFullTime2() {
		return getCurrentDateTime("yyMMddHHmmss");
	}

	public static String getFullTime3() {
		return getCurrentDateTime("yyyy-MM-dd HH:mm");
	}

	/**
	 * 获取完整的时间日期
	 *
	 * @return
	 */
	public static String getYM() {
		return getCurrentDateTime("yyyy-MM");
	}

	/**
	 * 获取完整的时间日期
	 *
	 * @return
	 */
	public static String getYearM() {
		return getCurrentDateTime("yyyyMM");
	}

	public static String getMonth() {
		return getCurrentDateTime("MM");
	}

	public static String getYear() {
		return getCurrentDateTime("yyyy");
	}

	/**
	 * 获取指定日期字符串n天之前或者之后的日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param day
	 *            相对天数，为正数表示之后，为负数表示之前
	 * @return 指定日期字符串n天之前或者之后的日期，格式为"yyyy-MM-dd"
	 */
	public static String getBeforeAfterDate(String strDate, int day) {
		Date dt = strToDate(strDate, "yyyy-MM-dd");
		if (dt != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + day);
			return calendarToString_(cal);
		}

		return null;
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @param strDate
	 *            待校验字符串日期
	 * @param format
	 *            日期格式
	 * @param isRequired
	 *            是否可以为空。true：不可以为空， false：可以为空
	 * @return
	 */
	public static boolean isDate(String strDate, String format, boolean isRequired) {
		if (strDate == null || strDate.length() == 0) {
			if (isRequired) {
				return false;
			} else {
				return true;
			}
		}
		Date dt = strToDate(strDate, format);
		if (dt == null) {
			return false;
		}
		return true;
	}

	/**
	 * 日期运算
	 * 
	 * @param objDate
	 *            目标日期
	 * @param type
	 *            运算类型
	 * @param amount
	 *            计算量
	 * @return
	 */
	public static Date operationDate(Date objDate, int type, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(objDate);
		cal.add(type, amount);
		return cal.getTime();
	}

	/**
	 * 获取指定日期是星期几 参数为null时表示获取当前日期是星期几
	 * @param type [1:返回“星期几”][2:返回“周几”]
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(int type,Date date) {
		String[] weekOfDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		String[] weekOfZhou = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		if (type == 1) {
			return weekOfDays[w];
		}
		return weekOfZhou[w];
	}
	/**
	 * 前/后N天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static String beforeAfterDate(String datestr, int hour) {
		Date date = strToDate(datestr,"yyyy-MM-dd HH:mm:ss");
		if(null==date){
			return datestr;
		}
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		c.add(java.util.Calendar.HOUR_OF_DAY, hour);
		return DateUtil.formatDate(c.getTime(),"yyyy-MM-dd HH:mm:ss");
	}
}
