let currentPageElectroType = 0;
const pageSizeElectroType = 10;

function electroTypesLoad() {
    
    $.get(`http://localhost:8081/electro-type?page=${currentPageElectroType}&size=${pageSizeElectroType}`, function(data) {
        let table = `
                <h3>🏪 Типы товара</h3>
                <button class="btn btn-success mb-2" onclick="electroTypeShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead><tr><th>ID</th><th>Название</th><th>Действия</th></tr></thead>
                    <tbody>${data.map(electroType => `
                        <tr>
                            <td>${electroType.id}</td>
                            <td>${electroType.name}</td>
                            <td>
                                <button class="btn btn-warning" onclick="electroTypeEdit(${electroType.id})">✏️</button>
                            </td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                    <button class="btn btn-secondary" onclick="changePageElectroType(-1)" ${currentPageElectroType === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageElectroType(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}

function changePageElectroType(direction) {
    currentPageElectroType += direction;
    electroTypesLoad();
}


function electroTypeShowForm() {
    const form = `
            <h3>Добавить тип товара</h3>
            <form id="electroType-form">
                <div class="mb-3">
                    <label for="electroType-name" class="form-label">Название</label>
                    <input type="text" id="electroType-name" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="electroTypeLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#electroType-form').on('submit', function (event) {
        event.preventDefault();
        const name = $('#electroType-name').val();
        $.post('http://localhost:8081/electro-type', {name}, function () {
            electroTypesLoad();
        });
    });
}


function electroTypeEdit(id) {
    $.get(`http://localhost:8081/electro-type/${id}`, function(electroType) {
        const form = `
                <h3>Редактировать тип товара</h3>
                <form id="electroType-form">
                    <input type="hidden" id="electroType-id" value="${id}">
                    <div class="mb-3">
                        <label for="electroType-name" class="form-label">Название</label>
                        <input type="text" id="electroType-name" class="form-control" value="${electroType.name}" required>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить</button>
                    <button type="button" class="btn btn-secondary" onclick="electroTypesLoad()">Отменить</button>
                </form>
            `;
        $('#content').html(form);

        $('#electroType-form').on('submit', function(event) {
            event.preventDefault();

            const electroTypeId = $('#electroType-id').val();
            const name = $('#electroType-name').val();

            console.log(`ID: ${electroTypeId}`);

            if (!electroTypeId || isNaN(electroTypeId) || electroTypeId <= 0) {
                alert('Некорректный идентификатор тип товараа');
                return;
            }

            $.ajax({
                url: `http://localhost:8081/electro-type/${electroTypeId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ name }),
                success: function() {
                    electroTypesLoad();
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : 'Неизвестная ошибка';
                    alert(`Ошибка: ${errorMessage}`);
                }
            });

        });
    });
}

