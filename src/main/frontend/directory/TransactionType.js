let currentPageTransactionType = 0;
const pageSizeTransactionType = 10;

function transactionTypesLoad() {
    
    $.get(`http://localhost:8081/pyrchase-type?page=${currentPageTransactionType}&size=${pageSizeTransactionType}`, function(data) {
        let table = `
                <h3>🏪 Типы транзакции</h3>
                <button class="btn btn-success mb-2" onclick="transactionTypeShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead><tr><th>ID</th><th>Название</th><th>Действия</th></tr></thead>
                    <tbody>${data.map(transactionType => `
                        <tr>
                            <td>${transactionType.id}</td>
                            <td>${transactionType.name}</td>
                            <td>
                                <button class="btn btn-warning" onclick="transactionTypeEdit(${transactionType.id})">✏️</button>
                            </td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                    <button class="btn btn-secondary" onclick="changePageTransactionType(-1)" ${currentPageTransactionType === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageTransactionType(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}
function changePageTransactionType(direction) {
    currentPageTransactionType += direction;
    transactionTypesLoad();
}


function transactionTypeShowForm() {
    const form = `
            <h3>Добавить тип транзакцию</h3>
            <form id="transactionType-form">
                <div class="mb-3">
                    <label for="transactionType-name" class="form-label">Название</label>
                    <input type="text" id="transactionType-name" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="transactionTypeLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#transactionType-form').on('submit', function (event) {
        event.preventDefault();
        const name = $('#transactionType-name').val();
        $.post('http://localhost:8081/transactionType', {name}, function () {
            transactionTypesLoad();
        });
    });
}


function transactionTypeEdit(id) {
    $.get(`http://localhost:8081/pyrchase-type/${id}`, function(transactionType) {
        const form = `
                <h3>Редактировать тип транзакцию</h3>
                <form id="transactionType-form">
                    <input type="hidden" id="transactionType-id" value="${id}">
                    <div class="mb-3">
                        <label for="transactionType-name" class="form-label">Название</label>
                        <input type="text" id="transactionType-name" class="form-control" value="${transactionType.name}" required>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить</button>
                    <button type="button" class="btn btn-secondary" onclick="transactionTypesLoad()">Отменить</button>
                </form>
            `;
        $('#content').html(form);

        $('#transactionType-form').on('submit', function(event) {
            event.preventDefault();

            const transactionTypeId = $('#transactionType-id').val();
            const name = $('#transactionType-name').val();

            console.log(`ID: ${transactionTypeId}`);

            if (!transactionTypeId || isNaN(transactionTypeId) || transactionTypeId <= 0) {
                alert('Некорректный идентификатор  тип транзакцияа');
                return;
            }

            $.ajax({
                url: `http://localhost:8081/pyrchase-type/${transactionTypeId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ name }),
                success: function() {
                    transactionTypesLoad();
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : 'Неизвестная ошибка';
                    alert(`Ошибка: ${errorMessage}`);
                }
            });

        });
    });
}

