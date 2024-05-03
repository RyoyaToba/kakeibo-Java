let lineCtx = document.getElementById("lineChart");
    // 線グラフの設定
    let lineConfig = {
      type: 'line',
      data: {
        // ※labelとデータの関係は得にありません
        labels: ['2024/01', '2024/02', '2024/03', '2024/04', '2024/05', '2024/06', '2024/07'],
        datasets: [{
          label: 'Red',
          data: [20, 35, 40, 30, 45, 35, 40],
          borderColor: '#f88',
        }, {
          label: 'Green',
          data: [20, 15, 30, 25, 30, 40, 35],
          borderColor: '#484',
        }, {
          label: 'Blue',
          data: [30, 25, 10, 5, 25, 30, 20],
          borderColor: '#48f',
        }],
      },
      options: {
        scales: {
          // Y軸の最大値・最小値、目盛りの範囲などを設定する
          y: {
            suggestedMin: 0,
            suggestedMax: 40000,
            ticks: {
              stepSize: 50000,
            }
          }
        },
      },
    };
    let lineChart = new Chart(lineCtx, lineConfig);

