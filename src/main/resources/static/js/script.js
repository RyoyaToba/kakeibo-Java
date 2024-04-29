`use strict`

/** 合計金額を算出する */
let calcTotalPrice = () => {
    // class = priceとなっている要素を取得
    let priceElements = document.querySelectorAll('.price');

        let totalPrice = 0;
        // 合計金額を算出
        priceElements.forEach(function(element) {
            let priceText = element.textContent;
            let priceValue = parseFloat(priceText.replace(/[^0-9.-]+/g,"")); // カンマを削除して数値に変換
            totalPrice += priceValue;
        });
        // 算出した金額を画面表示する
        let sumElement = document.getElementById('sum');
        sumElement.textContent = formatPrice(totalPrice) + " 円"; // 小数点0桁まで表示
}

/** 前月の合計金額を算出 */
let calcTotalPricePrevMonth = () => {

    let targetMonth = $("#targetMonth").val();

    let previousMonth = getPreviousMonth(targetMonth);

    $.ajax({
        url: "/home/calcTotalPricePrevMonth",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({previousMonth: previousMonth})
        })
        .done(function(data) {
            // idが "sum" の要素を取得
            let sumElement = document.getElementById("sum");

            // 要素のテキストを取得して数値に変換
            let sumText = sumElement.textContent.replace(/,/g, '');
            let sumNumber = parseFloat(sumText);

            let distance = sumNumber - data.previousMonthSum;
            console.log("今月の合計" + sumNumber);
            console.log("先月の合計" + data.previousMonthSum);

            let hugou = "";

            if (distance > 0) {
                hugou = "＋";
            }

        	$("#prevSum").text("（" + "前月比：" + hugou + distance + "円" + "）");
        	console.log(data);
        	console.log(distance);
        })
        .fail(function() {
        	console.log('fail')
        })
}

/** 前月を文字列で取得 */
function getPreviousMonth(currentMonth) {
    // yyyy/mm形式の文字列を分割
    var parts = currentMonth.split('/');
    var year = parseInt(parts[0]);
    var month = parseInt(parts[1]);

    // 前月の計算
    if (month === 1) {
        year -= 1;
        month = 12;
    } else {
        month -= 1;
    }

    // 前月の年月を文字列にして返す
    return year + '/' + (month < 10 ? '0' + month : month);
}


/** 画面ロード時に呼び出す */
window.addEventListener('load', function() {
    calcTotalPrice();
    calcTotalPricePrevMonth();
})
