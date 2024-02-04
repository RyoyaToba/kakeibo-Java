`use strict`

window.addEventListener('load', function() {

    var priceElements = document.querySelectorAll('.price');

    var totalPrice = 0;

    priceElements.forEach(function(element) {
        var priceText = element.textContent;
        var priceValue = parseFloat(priceText.replace('$', '')); // もし価格が'$'で始まっている場合は除去
        totalPrice += priceValue;
    });

    var sumElement = document.getElementById('sum');
    sumElement.textContent = totalPrice.toFixed(0) + " 円"; // 小数点2桁まで表示

})




