package com.shuto.mam.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @PackageClassname com.shuto.mam.utils.CompareDate
 * @Author: luxq
 * @Date: 2020/7/7 9:06:51
 * @Version: v1.0
 * @Description: Java比较两个时间相差多少天，多少个月，多少年 工具类
 **/
public class CompareDate {
    /**
     *  
     *        * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12
     *        * @param date2 被比较的时间  为空(null)则为当前时间 
     *        * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年 
     *        * @return 
     *        * 举例：
     *        * compareDate("2009-09-12", null, 0);//比较天
     *        * compareDate("2009-09-12", null, 1);//比较月
     *        * compareDate("2009-09-12", null, 2);//比较年
     *       
     */
    public static int compareDate(String startDay, String endDay, int stype) {
        int n = 0;
        String[] u = {"天", "月", "年"};
        String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

        endDay = endDay == null ? getCurrentDate("yyyy-MM-dd") : endDay;

        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(startDay));
            c2.setTime(df.parse(endDay));
        } catch (Exception e3) {
            System.out.println("wrong occured");
        }
        // 循环对比，直到相等，n 就是所要的结果 
        while (!c1.after(c2)) {
            n++;
            if (stype == 1) {
                // 比较月份，月份+1 
                c1.add(Calendar.MONTH, 1);
            } else {
                // 比较天数，日期+1 
                c1.add(Calendar.DATE, 1);
            }
        }
        n = n - 1;
        if (stype == 2) {
            n = (int) n / 365;
        }
//        System.out.println(startDay + " -- " + endDay + " 相差多少" + u[stype] + ":" + n);
        return n;
    }

    public static String getCurrentDate(String format) {
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, 0);
        //"yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(day.getTime());
        return date;
    }


}
