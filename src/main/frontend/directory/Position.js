let currentPagePosition = 0;
const pageSizePosition = 10;

function positionsLoad() {
    
    $.get(`http://localhost:8081/job-title?page=${currentPagePosition}&size=${pageSizePosition}`, function(data) {
        let table = `
                <h3>🏪 Должностьи</h3>
                <button class="btn btn-success mb-2" onclick="positionShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead><tr><th>ID</th><th>Название</th><th>Действия</th></tr></thead>
                    <tbody>${data.map(position => `
                        <tr>
                            <td>${position.id}</td>
                            <td>${position.name}</td>
                            <td>
                                <button class="btn btn-warning" onclick="positionEdit(${position.id})">✏️</button>
                            </td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                    <button class="btn btn-secondary" onclick="changePagePosition(-1)" ${currentPagePosition === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePagePosition(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}

function changePagePosition(direction) {
    currentPagePosition += direction;
    positionsLoad();
}


function positionShowForm() {
    const form = `
            <h3>Добавить Должность</h3>
            <form id="position-form">
                <div class="mb-3">
                    <label for="position-name" class="form-label">Название</label>
                    <input type="text" id="position-name" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="positionLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#position-form').on('submit', function (event) {
        event.preventDefault();
        const name = $('#position-name').val();
        $.post('http://localhost:8081/job-title', {name}, function () {
            positionsLoad();
        });
    });
}


function positionEdit(id) {
    $.get(`http://localhost:8081/job-title/${id}`, function(position) {
        const form = `
                <h3>Редактировать Должность</h3>
                <form id="position-form">
                    <input type="hidden" id="position-id" value="${id}">
                    <div class="mb-3">
                        <label for="position-name" class="form-label">Название</label>
                        <input type="text" id="position-name" class="form-control" value="${position.name}" required>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить</button>
                    <button type="button" class="btn btn-secondary" onclick="positionsLoad()">Отменить</button>
                </form>
            `;
        $('#content').html(form);

        $('#position-form').on('submit', function(event) {
            event.preventDefault();

            const positionId = $('#position-id').val();
            const name = $('#position-name').val();

            console.log(`ID: ${positionId}`);

            if (!positionId || isNaN(positionId) || positionId <= 0) {
                alert('Некорректный идентификатор Должностьа');
                return;
            }

            $.ajax({
                url: `http://localhost:8081/job-title/${positionId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ name }),
                success: function() {
                    positionsLoad();
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : 'Неизвестная ошибка';
                    alert(`Ошибка: ${errorMessage}`);
                }
            });

        });
    });
}

