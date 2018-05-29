package hpcomputeing.tools;/**
 * Created by:Ricardo
 * Description:
 * Date: 2018/5/28
 * Time: 13:22
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *@ClassName: DateAndString
 *@Description: TODO
 *@Author: Ricardo
 *@Date: 2018/5/28 13:22
 **/
public class DateAndString {

    public static void main(String[] args) {
        Date date = new Date();
        Date2String(date);
        String startDateString = "2016-10-24 21:59:06";
        String endDateString = "2016-10-24 21:59:06";

        try {
            String2Date(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        startDateString = "2017-2-25 21:59:06";
        endDateString = "2017-3-3 21:59:06";
        System.out.println("diff = " +calculateDates(startDateString,endDateString));


        startDateString = "2016-10-24 21:59:06";
        endDateString = "2016-10-25 21:59:06";
        System.out.println("diff = " +calculateDates(startDateString,endDateString));

        startDateString = "2016-10-24 21:59:06";
        endDateString = "2016-11-30 21:59:06";
        System.out.println("diff = " +calculateDates(startDateString,endDateString));

        startDateString = "2017-2-25 21:59:06";
        String dates =  "10";
        System.out.println("diff = " +calculateEndDates(startDateString,dates));
    }

    /*返回 "yyyy-MM-dd" 模式的日期字符串*/
    public static String Date2String(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
//        System.out.println(dateStr);
//
//        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(sdf.format(date));
//
//        sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        System.out.println(sdf.format(date));
    }

    /*根据"yyyy-MM-dd"格式的字符串构造日期*/
    public static Date String2Date(String string) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(string);
        // System.out.println(sdf.parse(string));
    }

    /*根据给定的"yyyy-MM-dd"格式起始日期字符串，以及结束日期字符串，返回中间间隔的天数，*/
    public static int calculateDates(String startDateString,String endDateString) {

        int date= 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(startDateString));

            for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDateString).getTime(); d = get_D_Plaus_1(cal)) {
               // System.out.println(sdf.format(d));
                date++;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }
    public static long get_D_Plaus_1(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }


    /*根据给定的"yyyy-MM-dd"格式起始日期字符串，以及中间间隔的天数，返回结束日期字符串，*/
    public static String calculateEndDates(String startDateString,String dates) {

        String endDate= null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(startDateString));

            cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) +Integer.parseInt(dates));

            long d = cal.getTimeInMillis();
            endDate = sdf.format(d);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;

    }
}
