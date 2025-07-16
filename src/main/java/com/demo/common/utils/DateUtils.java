package com.demo.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
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

    /** LocalDateで渡された日付をYYYY/MM形式のStringで返す */
    public static String localDateToStringTitleMonth(LocalDate date) {
        // LocalDate → String
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return date.format(dateTimeFormatter).substring(0,7); // MMまでを取得
    }

    /** Dateで渡された日付をYYYY/MM形式のStringで返す */
    public static String dateToString(Date date) {
        // 日付のフォーマットを定義
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        // 日付を指定された形式でフォーマットして文字列に変換
        String formattedDate = sdf.format(date);
        return formattedDate;
    }



}
