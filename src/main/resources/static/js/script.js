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

/** 画面ロード時に呼び出す */
window.addEventListener('load', function() {
    calcTotalPrice();
    //onTargetMonthChange();
})
