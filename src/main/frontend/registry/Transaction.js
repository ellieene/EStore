let currentPageTransaction = 0;
const pageSizeTransaction = 10;

function transactionsLoad() {
    const currentSort = true;
    
    $.get(`http://localhost:8081/transaction?sort=${currentSort}&page=${currentPageTransaction}&size=${pageSizeTransaction}`, function(data) {
        let table = `
                <h3>🏪 Транзакции</h3>
                <button class="btn btn-info mb-2" onclick="toggleSort()">Сортировать ${currentSort ? 'по убыванию' : 'по возрастанию'}</button>
                <button class="btn btn-success mb-2" onclick="transactionShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Название товара</th>
                            <th>Сотрудник</th>
                            <th>Тип оплаты</th>
                            <th>Дата</th>
                            <th>Магазин</th>
                            <th>Адрес магазина</th>
                        </tr>
                    </thead>
                    <tbody>${data.map(transaction => `
                        <tr>
                            <td>${transaction.id}</td>
                            <td>${transaction.electroName}</td>
                            <td>${transaction.employee.firstName} ${transaction.employee.lastName} ${transaction.employee.patronymic}</td>
                            <td>${transaction.purchaseType}</td>
                            <td>${transaction.date}</td>
                            <td>${transaction.shop ? transaction.shop.name : 'Нет магазина'}</td>
                            <td>${transaction.shop ? transaction.shop.address : 'Нет адреса'}</td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                    <button class="btn btn-secondary" onclick="changePageTransaction(-1)" ${currentPageTransaction === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageTransaction(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}
function changePageTransaction(direction) {
    currentPageTransaction += direction;
    transactionsLoad();
}


function toggleSort() {
    currentSort = !currentSort;
    transactionsLoad();
}




function transactionShowForm() {
    const form = `
            <h3>Добавить транзакцию</h3>
            <form id="transaction-form">
                <div class="mb-3">
                    <label for="transaction-electroName" class="form-label">Название товара</label>
                    <input type="text" id="transaction-electroName" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="transaction-employeeId" class="form-label">ID сотрудника</label>
                    <input type="number" id="transaction-employeeId" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="transaction-purchaseType" class="form-label">Тип оплаты</label>
                    <input type="text" id="transaction-purchaseType" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="transaction-purchaseType" class="form-label">Дата</label>
                    <input type="text" id="transaction-data" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="transaction-shop" class="form-label">Магазин</label>
                    <input type="text" id="transaction-shop" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="transactionsLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#transaction-form').on('submit', function (event) {
        event.preventDefault();
        const electroName = $('#transaction-electroName').val();
        const employeeId = $('#transaction-employeeId').val();
        const purchaseType = $('#transaction-purchaseType').val();
        const shop = { name: $('#transaction-shop').val() };

        $.post('http://localhost:8081/transaction', { electroName, employeeId, purchaseType, shop }, function () {
            transactionsLoad();
        });
    });
}

