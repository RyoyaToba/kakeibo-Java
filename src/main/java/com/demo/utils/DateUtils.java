package com.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    /** LocalDate → Date */
    public static Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /** Date → LocalDate */
    public static LocalDate convertDateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /** 月初日を返す (LocalDate)*/
    public static LocalDate getStartOfMonth(LocalDate date){
        return date.withDayOfMonth(1);
    }

    /** 月末日を返す (LocalDate)*/
    public static LocalDate getEndOfMonth(LocalDate date) {
        return date.withDayOfMonth(LocalDate.now().lengthOfMonth());
    }

    /** 日付を文字列から日付型に変換 */
    public static Date convertStringToDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
            // パースに失敗した場合の例外処理
            e.printStackTrace();

        }

        return null;
    }


}
