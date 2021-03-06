package com.meipiao.java8;

import com.alibaba.fastjson.JSONObject;

import java.time.*;

/**
 * @Author: Chenwx
 * @Date: 2020/5/11 10:50
 * @Des: Java 8 日期时间 API
 */
public class Java8DateTime {
    public static void main(String[] args) {
        Java8DateTime java8DateTime = new Java8DateTime();
        System.out.println("时间显示为白色字体,时区相关为红色字体");
        System.out.println("---------------------------------");
        java8DateTime.testLocalDateTime();
        System.out.println("---------------------------------");
        java8DateTime.testZonedDateTime();
        System.out.println("---------------------------------");
        System.out.println("---------------------------------");
        System.out.println("---------------------------------");

        String str = "{\n" +
                "\t\"AID\": 1,\n" +
                "\t\"SID\": 50,\n" +
                "\t\"Access_Token\": \"02856107c8594d21aaf5cdfb563e875\",\n" +
                "\t\"Expires_In\": 597,\n" +
                "\t\"Refresh_Token\": \"c24rngwlriasdfsga7357ee613e500ee\"\n" +
                "}";
        JSONObject result = JSONObject.parseObject(str);

        Integer errCode = result.getInteger("ErrCode");
        if(errCode == null ){
            System.out.println("aaaa");
        }

        if(true || false){
            System.out.println("1");
        }else{
            System.out.println("2");
        }
    }

    public void testLocalDateTime(){
        LocalDateTime currentTime  = LocalDateTime.now();
        System.out.println("当前时间: " + currentTime); //精确到毫秒

        LocalDate date1 = currentTime.toLocalDate();
        System.out.println("date1: " + date1); //精确到日

        Month month = currentTime.getMonth();
        int day = currentTime.getDayOfMonth();
        int seconds = currentTime.getSecond();

        System.out.println("月: " + month +", 日: " + day +", 秒: " + seconds);

        LocalDateTime date2 = currentTime.withDayOfMonth(10).withYear(2012);
        System.out.println("date2: " + date2);

        // 12 december 2014
        LocalDate date3 = LocalDate.of(2014, Month.DECEMBER, 12);
        System.out.println("date3: " + date3);

        // 22 小时 15 分钟
        LocalTime date4 = LocalTime.of(22, 15);
        System.out.println("date4: " + date4);

        // 解析字符串 String转化成LocalTime 时:分:秒
        LocalTime date5 = LocalTime.parse("20:15:30");
        System.out.println("date5: " + date5);

    }

    //如果我们需要考虑到时区，就可以使用时区的日期时间API：
    public void testZonedDateTime(){

        // 获取当前时间日期
        ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
        System.err.println("date1: " + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.err.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.err.println("当期时区: " + currentZone);
    }

}
