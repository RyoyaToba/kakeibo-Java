package com.demo.common.service.impl;

import com.demo.common.service.CommonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public LocalDate convertStringToFirstLocalDate(String targetMonth) {

        targetMonth = targetMonth + "/01";

        // 入力フォーマットを指定（日付部分を含む）
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 出力フォーマットを指定
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 入力文字列が正しいフォーマットか確認
        if (isValidFormat(targetMonth, inputFormatter)) {
            // 入力文字列をLocalDateに変換
            LocalDate date = LocalDate.parse(targetMonth, inputFormatter);
            // 日付の日部分を常に"01"に設定
            LocalDate resultDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
            // 出力フォーマットに変換して返す
            return LocalDate.parse(resultDate.format(outputFormatter), outputFormatter);
        } else {
            // エラー処理などを行うか、例外をスローするなど適切な対応を行う
            throw new IllegalArgumentException("無効な日付形式");
        }

    }

    @Override
    public LocalDate convertStringToEndLocalDate(String targetMonth) {
        // yyyy/mm形式の文字列を分割して年と月を取得
        String[] parts = targetMonth.split("/");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);

        // YearMonthオブジェクトを作成して指定された年月の末日を取得
        YearMonth yearMonthObj = YearMonth.of(year, month);
        int lastDayOfMonth = yearMonthObj.lengthOfMonth();

        // LocalDateオブジェクトを作成して末日を返す
        LocalDate endOfMonth = LocalDate.of(year, month, lastDayOfMonth);
        return endOfMonth;
    }

    // 文字列が指定されたフォーマットに従っているかを確認するメソッド
    private boolean isValidFormat(String value, DateTimeFormatter formatter) {
        try {
            LocalDate.parse(value, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}