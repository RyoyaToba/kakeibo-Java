`use strict`

/** fetch通信 */
let fetchFunction = (url, selectedMonth) => {

fetch(url, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({ selectedMonth: selectedMonth }),
}).then(response => {
    // レスポンスが成功した場合
    if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
           console.log(data);
        })
        .catch(error => {
            // エラー時の処理
            console.error('Fetch request failed:', error);
        });
}



