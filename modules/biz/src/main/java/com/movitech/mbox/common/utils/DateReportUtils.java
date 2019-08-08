package com.movitech.mbox.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期报表工具
 * 
 * @author felix.jin 2017年9月6日
 */
public class DateReportUtils {

	public static Date getNearMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.MILLISECOND,0);
		// 日期的周第一天，星期天
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		//日期+1天，变周一
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.MILLISECOND,0);
		// 日期的月份第一天
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getMonthlastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MINUTE,59);
		// 日期的月份第一天
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}


	public static Date getNearSunDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MINUTE,59);
		// 日期的周第一天，星期天
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		//日期+7天，变周一
		calendar.add(Calendar.DATE, 7);
		return calendar.getTime();
	}

	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}
	public static Date getDayStart(){
        Calendar todayStart = Calendar.getInstance();
         todayStart.set(Calendar.HOUR_OF_DAY, 0);
         todayStart.set(Calendar.MINUTE, 0);
         todayStart.set(Calendar.SECOND, 0);
         todayStart.set(Calendar.MILLISECOND, 0);
         return todayStart.getTime();
}
}
