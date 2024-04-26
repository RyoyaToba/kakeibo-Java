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

// 価格をカンマ区切りにフォーマットする関数
function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


/* 表内の金額編集関数 */
$(document).ready(function() {
        $('.editable').on('click', function() {
            var cell = $(this);
            var value = cell.text();
            cell.html('<input type="number" value="' + value + '">');
            var input = cell.find('input');
            input.focus();

            input.on('blur', function() {
                var newValue = $(this).val();
                cell.text(newValue);
                var itemId = cell.closest('tr').find('input[name="itemId"]').val();

                // jQueryを使用してAjaxリクエストを送信してJavaに値を送信する
                $.ajax({
                    type: 'POST',
                    url: '/home/updateCellValue',
                    contentType: 'application/json',
                    data: JSON.stringify({ itemId: itemId, newValue: newValue }),
                    success: function(response) {
                        console.log('Value updated successfully');
                    },
                    error: function(xhr, status, error) {
                        console.error('Error updating value:', error);
                    }
                });
            });
        });
    });



