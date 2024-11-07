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
                cell.text(newBalance);
                var accountId = cell.closest('tr').find('input[name="accountId"]').val();

                // jQueryを使用してAjaxリクエストを送信してJavaに値を送信する
                $.ajax({
                    type: 'POST',
                    url: '/home/updateCellValue',
                    contentType: 'application/json',
                    data: JSON.stringify({ accountId: accountId, newValue: newValue }),
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