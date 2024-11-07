`use strict`

window.addEventListener('load', function() {
    retrieveSumPrice();
})


// selectElementを取得して変数に代入
const selectElement = document.getElementById('item-name');

selectElement.addEventListener('change', function() {

    // 選択された option の値とテキストを取得
    const selectedText = selectElement.options[selectElement.selectedIndex].text;

    console.log("選択されたテキスト:", selectedText);

    if (selectedText == "合計") {
        retrieveSumPrice();
    } else {
        retrieveItemPrice(selectedText);
    }
})

/* グラフの作成 */
let lineChart = null;

let createChart = (data) => {
    let lineCtx = document.getElementById("lineChart");

    // 既存のチャートインスタンスが存在する場合は破棄
    if (lineChart) {
        lineChart.destroy();
    }

    // 線グラフの設定
    let lineConfig = {
        type: 'line',
        data: {
            labels: Object.keys(data),
            datasets: [{
                label: selectElement.options[selectElement.selectedIndex].text,
                data: Object.values(data),
                borderColor: '#f88',
            }],
        },
        options: {
            scales: {
                y: {
                    suggestedMin: 5000,
                    suggestedMax: 30000,
                    ticks: {
                        stepSize: 5000
                    }
                }
            },
        },
        responsive: true,
        maintainAspectRatio: false,
    };

    // グローバル変数 lineChart に新しいチャートインスタンスを代入
    lineChart = new Chart(lineCtx, lineConfig);
};

let retrieveSumPrice = () => {
    $.ajax({
        url: "/sumPrice",
        type: "POST",
        contentType: "application/json",
        dataType: "json"
    }).done(function(data) {
        console.log(data);
        createChart(data);
    }).fail(function() {
        console.log('fail');
    })
}

let retrieveItemPrice = (selectedText) => {
    $.ajax({
        url: "/itemPrices",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({ selectedText: selectedText }), // データをJSON形式で送信
    }).done(function(data) {
        console.log(data);
        createChart(data);
    }).fail(function() {
        console.log('fail');
    })
}
