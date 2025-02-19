let currentPageShop = 0;
const pageSizeShop = 10;

function shopsLoad() {
    
    $.get(`http://localhost:8081/shop?page=${currentPageShop}&size=${pageSizeShop}`, function(data) {
        let table = `
                <h3>🏪 Магазины</h3>
                <button class="btn btn-success mb-2" onclick="shopShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead><tr><th>ID</th><th>Название</th><th>Адрес</th><th>Действия</th></tr></thead>
                    <tbody>${data.map(shop => `
                        <tr>
                            <td>${shop.id}</td>
                            <td>${shop.name}</td>
                            <td>${shop.address}</td>
                            <td>
                                <button class="btn btn-warning" onclick="shopEdit(${shop.id})">✏️</button>
                            </td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                    <button class="btn btn-secondary" onclick="changePageShop(-1)" ${currentPageShop === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageShop(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}

function changePageShop(direction) {
    currentPageShop += direction;
    shopsLoad();
}


function shopShowForm() {
    const form = `
            <h3>Добавить магазин</h3>
            <form id="shop-form">
                <div class="mb-3">
                    <label for="shop-name" class="form-label">Название</label>
                    <input type="text" id="shop-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shop-address" class="form-label">Адрес</label>
                    <input type="text" id="shop-address" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="shopLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#shop-form').on('submit', function (event) {
        event.preventDefault();
        const name = $('#shop-name').val();
        const address = $('#shop-address').val();
        $.post('http://localhost:8081/shop', {name, address}, function () {
            shopsLoad();
        });
    });
}


function shopEdit(id) {
    $.get(`http://localhost:8081/shop/${id}`, function(shop) {
        const form = `
                <h3>Редактировать магазин</h3>
                <form id="shop-form">
                    <input type="hidden" id="shop-id" value="${id}">
                    <div class="mb-3">
                        <label for="shop-name" class="form-label">Название</label>
                        <input type="text" id="shop-name" class="form-control" value="${shop.name}" required>
                    </div>
                    <div class="mb-3">
                        <label for="shop-address" class="form-label">Адрес</label>
                        <input type="text" id="shop-address" class="form-control" value="${shop.address}" required>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить</button>
                    <button type="button" class="btn btn-secondary" onclick="shopsLoad()">Отменить</button>
                </form>
            `;
        $('#content').html(form);

        $('#shop-form').on('submit', function(event) {
            event.preventDefault();

            const shopId = $('#shop-id').val();
            const name = $('#shop-name').val();
            const address = $('#shop-address').val();

            console.log(`ID: ${shopId}`);

            if (!shopId || isNaN(shopId) || shopId <= 0) {
                alert('Некорректный идентификатор магазина');
                return;
            }

            $.ajax({
                url: `http://localhost:8081/shop/${shopId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ name, address }),
                success: function() {
                    shopsLoad();
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : 'Неизвестная ошибка';
                    alert(`Ошибка: ${errorMessage}`);
                }
            });

        });
    });
}

