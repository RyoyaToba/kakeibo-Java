package com.demo.utils;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    
    /** Home画面で年月選択のプルダウンびデータを格納 */
    public static List<String> retrieveMonths() {

        List<String> yearMonths = new ArrayList<>();

        // 現在の年月を取得
        YearMonth currentYearMonth = YearMonth.now();
        // 上限となる年月
        YearMonth limitYearMonth = YearMonth.of(2022, 1);
        // 現在の年月から上限の年月までループ
        while (currentYearMonth.isAfter(limitYearMonth) || currentYearMonth.equals(limitYearMonth)) {
            // 年月を文字列にフォーマットしてリストに追加
            String formattedDate = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy/MM"));
            yearMonths.add(formattedDate);

            // 1ヶ月前の年月に更新
            currentYearMonth = currentYearMonth.minusMonths(1);
        }

        return yearMonths;
    }
}
