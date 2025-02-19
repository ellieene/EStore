let pageElectroItem = 0;
const pageSizeElectroItem = 10;

function electroItemsLoad() {
    $.get(`http://localhost:8081/electro-item?page=${pageElectroItem}&size=${pageSizeElectroItem}`, function(data) {
        let table = `
                <h3>🏪 Товары</h3>
                <button class="btn btn-success mb-2" onclick="electroItemShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Название</th>
                            <th>Цена</th>
                            <th>Тип электроники</th>
                            <th>Архивный</th>
                            <th>Описание</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>${data.map(electroItem => `
                        <tr>
                            <td>${electroItem.id}</td>
                            <td>${electroItem.name}</td>
                            <td>${electroItem.price}</td>
                            <td>${electroItem.electronicType}</td>
                            <td>${electroItem.archive ? 'Да' : 'Нет'}</td>
                            <td>${electroItem.description || 'Нет описания'}</td>
                            <td>
                                <button class="btn btn-warning" onclick="electroItemEdit(${electroItem.id})">✏️</button>
                            </td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>
                    <button class="btn btn-secondary" onclick="changePageElectroItem(-1)" ${pageElectroItem === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageElectroItem(1)">Вперед</button>
                </div>
        `;
        $('#content').html(table);
    });
}

function changePageElectroItem(direction) {
    pageElectroItem += direction;
    electroItemsLoad();
}




function electroItemShowForm() {
    const form = `
            <h3>Добавить товар</h3>
            <form id="electroItem-form">
                <div class="mb-3">
                    <label for="electroItem-name" class="form-label">Название</label>
                    <input type="text" id="electroItem-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="electroItem-price" class="form-label">Цена</label>
                    <input type="number" id="electroItem-price" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="electroItem-type" class="form-label">Тип электроники</label>
                    <input type="text" id="electroItem-type" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="electroItem-archive" class="form-label">Архивный</label>
                    <input type="checkbox" id="electroItem-archive">
                </div>
                <div class="mb-3">
                    <label for="electroItem-description" class="form-label">Описание</label>
                    <textarea id="electroItem-description" class="form-control"></textarea>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="electroItemsLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#electroItem-form').on('submit', function (event) {
        event.preventDefault();
        const name = $('#electroItem-name').val();
        const price = $('#electroItem-price').val();
        const electronicType = $('#electroItem-type').val();
        const archive = $('#electroItem-archive').is(':checked');
        const description = $('#electroItem-description').val();

        $.post('http://localhost:8081/electro-item', { name, price, electronicType, archive, description }, function () {
            electroItemsLoad();
        });
    });
}



function electroItemEdit(id) {
    $.get(`http://localhost:8081/electro-item/${id}`, function(electroItem) {
        const form = `
                <h3>Редактировать товар</h3>
                <form id="electroItem-form">
                    <input type="hidden" id="electroItem-id" value="${id}">
                    <div class="mb-3">
                        <label for="electroItem-name" class="form-label">Название</label>
                        <input type="text" id="electroItem-name"                        class="form-control" value="${electroItem.name}" required>
                    </div>
                    <div class="mb-3">
                        <label for="electroItem-price" class="form-label">Цена</label>
                        <input type="number" id="electroItem-price" class="form-control" value="${electroItem.price}" required>
                    </div>
                    <div class="mb-3">
                        <label for="electroItem-type" class="form-label">Тип электроники</label>
                        <input type="text" id="electroItem-type" class="form-control" value="${electroItem.electronicType}" required>
                    </div>
                    <div class="mb-3">
                        <label for="electroItem-archive" class="form-label">Архивный</label>
                        <input type="checkbox" id="electroItem-archive" ${electroItem.archive ? 'checked' : ''}>
                    </div>
                    <div class="mb-3">
                        <label for="electroItem-description" class="form-label">Описание</label>
                        <textarea id="electroItem-description" class="form-control">${electroItem.description || ''}</textarea>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить</button>
                    <button type="button" class="btn btn-secondary" onclick="electroItemsLoad()">Отменить</button>
                </form>`;
        $('#content').html(form);

        $('#electroItem-form').on('submit', function(event) {
            event.preventDefault();

            const electroItemId = $('#electroItem-id').val();
            const name = $('#electroItem-name').val();
            const price = $('#electroItem-price').val();
            const electronicType = $('#electroItem-type').val();
            const archive = $('#electroItem-archive').is(':checked');
            const description = $('#electroItem-description').val();

            // Проверка на корректность id
            if (!electroItemId || isNaN(electroItemId) || electroItemId <= 0) {
                alert('Некорректный идентификатор товара');
                return;
            }

            $.ajax({
                url: `http://localhost:8081/electro-item/${electroItemId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ name, price, electronicType, archive, description }),
                success: function() {
                    electroItemsLoad();
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : 'Неизвестная ошибка';
                    alert(`Ошибка: ${errorMessage}`);
                }
            });
        });
    });
}



