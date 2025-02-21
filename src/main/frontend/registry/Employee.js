let currentPageEmployee = 0;
const pageSizeEmployee = 10;
let currentPageEmployeeTop = 0;
const pageSizeEmployeeTop = 10;

function employeesLoadTop() {
    $.get(`http://localhost:8081/employee-top?page=${currentPageEmployeeTop}&size=${pageSizeEmployeeTop}`, function (data) {
        let table = `
                <h3>🏪 Лучшие сотрудники</h3>
                <button class="btn btn-info mb-2" onclick="employeesLoad()">Назад</button>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Фамилия</th>
                            <th>Имя</th>
                            <th>Отчество</th>
                            <th>Должность</th>
                            <th>Сумма</th>
                            <th>Магазин</th>
                            <th>Пол</th>
                        </tr>
                    </thead>
                    <tbody>${data.map(employee => `
                        <tr>
                            <td>${employee.id}</td>
                            <td>${employee.lastName}</td>
                            <td>${employee.firstName}</td>
                            <td>${employee.patronymic || ''}</td>
                            <td>${employee.position}</td>
                            <th>${employee.summary}</th>
                            <td>${employee.shop ? employee.shop.name : ''}</td>
                            <td>${employee.gender}</td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                    <button class="btn btn-secondary" onclick="changePageEmployeeTop(-1)" ${currentPageEmployeeTop === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageEmployeeTop(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}

function changePageEmployeeTop(direction) {
    currentPageEmployeeTop += direction;
    employeesLoadTop();
}

function employeesLoad() {
    $.get(`http://localhost:8081/employee?page=${currentPageEmployee}&size=${pageSizeEmployee}`, function (data) {
        let table = `
                <h3>🏪 Сотрудники</h3>
                <button class="btn btn-success mb-2" onclick="employeeShowForm()">➕ Добавить</button>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Фамилия</th>
                            <th>Имя</th>
                            <th>Отчество</th>
                            <th>Дата рождения</th>
                            <th>Должность</th>
                            <th>Магазин</th>
                            <th>Пол</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>${data.map(employee => `
                        <tr>
                            <td>${employee.id}</td>
                            <td>${employee.lastName}</td>
                            <td>${employee.firstName}</td>
                            <td>${employee.patronymic || ''}</td>
                            <td>${employee.birthDate}</td>
                            <td>${employee.position}</td>
                            <td>${employee.shop ? employee.shop.name : ''}</td>
                            <td>${employee.gender}</td>
                            <td>
                                <button class="btn btn-warning" onclick="employeeEdit(${employee.id})">✏️</button>
                            </td>
                        </tr>
                    `).join('')}</tbody>
                </table>
                <div>   
                                <button class="btn btn-info mb-2" onclick="employeesLoadTop()">Лучшие сотрудники</button>

                    <button class="btn btn-secondary" onclick="changePageEmployee(-1)" ${currentPageEmployee === 0 ? 'disabled' : ''}>Назад</button>
                    <button class="btn btn-secondary" onclick="changePageEmployee(1)">Вперед</button>
                </div>`;
        $('#content').html(table);
    });
}

function changePageEmployee(direction) {
    currentPageEmployee += direction;
    employeesLoad();
}


function employeeShowForm() {
    const form = `
            <h3>Добавить сотрудника</h3>
            <form id="employee-form">
                <div class="mb-3">
                    <label for="employee-lastName" class="form-label">Фамилия</label>
                    <input type="text" id="employee-lastName" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="employee-firstName" class="form-label">Имя</label>
                    <input type="text" id="employee-firstName" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="employee-patronymic" class="form-label">Отчество</label>
                    <input type="text" id="employee-patronymic" class="form-control">
                </div>
                <div class="mb-3">
                    <label for="employee-birthDate" class="form-label">Дата рождения</label>
                    <input type="date" id="employee-birthDate" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="employee-position" class="form-label">Должность</label>
                    <input type="text" id="employee-position" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shop-name" class="form-label">Название магазина</label>
                    <input type="text" id="shop-name" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="shop-address" class="form-label">Адрес магазинаа</label>
                    <input type="text" id="shop-address" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="employee-gender" class="form-label">Пол</label>
                    <input type="text" id="employee-gender" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Добавить</button>
                <button type="button" class="btn btn-secondary" onclick="employeesLoad()">Отменить</button>
            </form>
        `;
    $('#content').html(form);

    $('#employee-form').on('submit', function (event) {
        event.preventDefault();
        const lastName = $('#employee-lastName').val();
        const firstName = $('#employee-firstName').val();
        const patronymic = $('#employee-patronymic').val();
        const birthDate = $('#employee-birthDate').val();
        const position = $('#employee-position').val();
        const shopName = $('#shop-name').val();
        const shopAddress = $('#shop-address').val();
        const gender = $('#employee-gender').val();

        $.post('http://localhost:8081/employee', {
            lastName,
            firstName,
            patronymic,
            birthDate,
            position,
            shop: {
                name: shopName,
                address: shopAddress
            },
            gender
        }, function () {
            employeesLoad();
        });
    });
}

function employeeEdit(id) {
    $.get(`http://localhost:8081/employee/${id}`, function (employee) {
        const form = `
                <h3>Редактировать сотрудника</h3>
                <form id="employee-form">
                    <input type="hidden" id="employee-id" value="${id}">
                    <div class="mb-3">
                        <label for="employee-lastName" class="form-label">Фамилия</label>
                        <input type="text" id="employee-lastName" class="form-control" value="${employee.lastName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="employee-firstName" class="form-label">Имя</label>
                        <input type="text" id="employee-firstName" class="form-control" value="${employee.firstName}" required>
                    </div>
                    <div class="mb-3">
                        <label for="employee-patronymic" class="form-label">Отчество</label>
                        <input type="text" id="employee-patronymic" class="form-control" value="${employee.patronymic || ''}">
                    </div>
                    <div class="mb-3">
                        <label for="employee-birthDate" class="form-label">Дата рождения</label>
                        <input type="date" id="employee-birthDate" class="form-control" value="${employee.birthDate}" required>
                    </div>
                    <div class="mb-3">
                        <label for="employee-position" class="form-label">Должность</label>
                        <input type="text" id="employee-position" class="form-control" value="${employee.position}" required>
                    </div>
                    <div class="mb-3">
                        <label for="shop-name" class="form-label">Название магазина</label>
                        <input type="text" id="shop-name" class="form-control" value="${employee.shop ? employee.shop.name : ''}" required>
                    </div>
                    <div class="mb-3">
                        <label for="shop-address" class="form-label">Адрес магазина</label>
                        <input type="text" id="shop-address" class="form-control" value="${employee.shop ? employee.shop.address : ''}" required>
                    </div>
                    <div class="mb-3">
                        <label for="employee-gender" class="form-label">Пол</label>
                        <input type="text" id="employee-gender" class="form-control" value="${employee.gender}" required>
                    </div>
                    <button type="submit" class="btn btn-warning">Сохранить</button>
                    <button type="button" class="btn btn-secondary" onclick="employeesLoad()">Отменить</button>
                </form>
            `;
        $('#content').html(form);

        $('#employee-form').on('submit', function (event) {
            event.preventDefault();

            const employeeId = $('#employee-id').val();
            const lastName = $('#employee-lastName').val();
            const firstName = $('#employee-firstName').val();
            const patronymic = $('#employee-patronymic').val();
            const birthDate = $('#employee-birthDate').val();
            const position = $('#employee-position').val();
            const shopName = $('#shop-name').val();
            const shopAddress = $('#shop-address').val();
            const gender = $('#employee-gender').val();

            console.log(`ID: ${employeeId}`);

            // Проверка на корректность id
            if (!employeeId || isNaN(employeeId) || employeeId <= 0) {
                alert('Некорректный идентификатор сотрудника');
                return;
            }

            $.ajax({
                url: `http://localhost:8081/employee/${employeeId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({
                    lastName,
                    firstName,
                    patronymic,
                    birthDate,
                    position,
                    shop: {
                        name: shopName,
                        address: shopAddress
                    },
                    gender
                }),
                success: function () {
                    employeesLoad();
                },
                error: function (xhr) {
                    const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : 'Неизвестная ошибка';
                    alert(`Ошибка: ${errorMessage}`);
                }
            });
        });
    });
}


