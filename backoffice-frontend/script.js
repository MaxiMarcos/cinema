document.addEventListener('DOMContentLoaded', () => {
    const loginSection = document.getElementById('login-section');
    const moviesSection = document.getElementById('movies-section');
    const schedulesSection = document.getElementById('schedules-section');
    const theatersSection = document.getElementById('theaters-section');
    const seatsSection = document.getElementById('seats-section');
    const dashboardSection = document.getElementById('dashboard-section'); // Nueva sección del dashboard
    const appWrapper = document.getElementById('app-wrapper'); // Nuevo contenedor principal

    const navDashboard = document.getElementById('nav-dashboard');
    const navMovies = document.getElementById('nav-movies');
    const navSchedules = document.getElementById('nav-schedules');
    const navTheaters = document.getElementById('nav-theaters');
    const navSeats = document.getElementById('nav-seats');
    const navLogout = document.getElementById('nav-logout');

    const loginForm = document.getElementById('login-form');
    const loginError = document.getElementById('login-error');

    let jwtToken = localStorage.getItem('jwtToken');

    // Lógica inicial para mostrar la sección correcta al cargar la página
    if (jwtToken) {
        appWrapper.classList.remove('d-none'); // Mostrar el contenedor principal
        showSection(dashboardSection); // Mostrar el dashboard por defecto
    } else {
        showSection(loginSection);
    }

    const AUTH_SERVICE_URL = 'http://localhost:9000';
    const CINEMA_SERVICE_URL = 'http://localhost:9011';
    const THEATER_SERVICE_URL = 'http://localhost:9006';
    const CARRITO_SERVICE_URL = 'http://localhost:9002'; // Aunque no se usa directamente en el CRUD, la defino por consistencia.

    // Función para mostrar una sección y ocultar las demás
    function showSection(sectionToShow) {
        const sections = [loginSection, moviesSection, schedulesSection, theatersSection, seatsSection, dashboardSection];
        sections.forEach(section => {
            if (section === sectionToShow) {
                section.classList.remove('d-none');
                if (section === loginSection) {
                    section.classList.add('d-flex'); // loginSection usa d-flex para centrado
                    appWrapper.classList.add('d-none'); // Ocultar appWrapper cuando se muestra el login
                } else {
                    section.classList.add('d-block'); // Otras secciones pueden usar d-block
                    appWrapper.classList.remove('d-none'); // Asegurarse de que appWrapper esté visible
                }
            } else {
                section.classList.add('d-none');
                section.classList.remove('d-flex');
                section.classList.remove('d-block');
            }
        });
    }

    // Función para mostrar toasts de Bootstrap
    function showToast(message, type = 'info') {
        const toastContainer = document.querySelector('.toast-container');
        if (!toastContainer) {
            console.error('Toast container not found!');
            return;
        }

        const toastId = `toast-${Date.now()}`;
        const toastHtml = `
            <div id="${toastId}" class="toast align-items-center text-white bg-${type} border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        ${message}
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        `;

        toastContainer.insertAdjacentHTML('beforeend', toastHtml);
        const toastElement = document.getElementById(toastId);
        const bootstrapToast = new bootstrap.Toast(toastElement);

        toastElement.addEventListener('hidden.bs.toast', () => {
            toastElement.remove();
        });

        bootstrapToast.show();
    }

    // Navegación
    navDashboard.addEventListener('click', (e) => {
        e.preventDefault();
        if (jwtToken) {
            showSection(dashboardSection);
        } else {
            showToast('Debe iniciar sesión para acceder a esta sección.', 'warning');
            showSection(loginSection);
        }
    });

    navMovies.addEventListener('click', (e) => {
        e.preventDefault();
        if (jwtToken) {
            showSection(moviesSection);
            loadMovies();
        } else {
            showToast('Debe iniciar sesión para acceder a esta sección.', 'warning');
            showSection(loginSection);
        }
    });

    document.getElementById('add-seat-btn').addEventListener('click', () => {
        document.getElementById('seat-form-container').classList.remove('d-none');
        document.getElementById('seat-form').reset();
        document.getElementById('seat-id').value = '';
    });

    document.getElementById('cancel-seat-form').addEventListener('click', () => {
        document.getElementById('seat-form-container').classList.add('d-none');
    });

    document.getElementById('add-multiple-seats-btn').addEventListener('click', () => {
        document.getElementById('multiple-seats-form-container').classList.remove('d-none');
        document.getElementById('multiple-seats-form').reset();
    });

    document.getElementById('cancel-multiple-seats-form').addEventListener('click', () => {
        document.getElementById('multiple-seats-form-container').classList.add('d-none');
    });

    document.getElementById('seat-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const seatId = document.getElementById('seat-id').value;
        const seat = {
            seatNumber: document.getElementById('seat-number').value,
            price: document.getElementById('seat-price').value,
            isAvailable: document.getElementById('seat-is-available').checked,
            theaterId: document.getElementById('seat-theater-id').value
        };

        const method = seatId ? 'PUT' : 'POST';
        const url = seatId ? `${THEATER_SERVICE_URL}/seats/update/${seatId}` : `${THEATER_SERVICE_URL}/seats/create`;

        try {
            const response = await fetchWithAuth(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(seat)
            });

            if (response && response.ok) {
                showToast(`Asiento ${seatId ? 'actualizado' : 'creado'} exitosamente.`, 'success');
                document.getElementById('seat-form-container').classList.add('d-none');
                loadSeats();
            } else if (response) {
                showToast(`Error al ${seatId ? 'actualizar' : 'crear'} asiento: ${response.statusText}`, 'danger');
            }
        } catch (error) {
            showToast(`Error de red al ${seatId ? 'actualizar' : 'crear'} asiento: ${error.message}`, 'danger');
        }
    });

    navSchedules.addEventListener('click', (e) => {
        e.preventDefault();
        if (jwtToken) {
            showSection(schedulesSection);
            loadSchedules();
        } else {
            showToast('Debe iniciar sesión para acceder a esta sección.', 'warning');
            showSection(loginSection);
        }
    });

    navTheaters.addEventListener('click', (e) => {
        e.preventDefault();
        if (jwtToken) {
            showSection(theatersSection);
            loadTheaters();
        } else {
            showToast('Debe iniciar sesión para acceder a esta sección.', 'warning');
            showSection(loginSection);
        }
    });

    navSeats.addEventListener('click', (e) => {
        e.preventDefault();
        if (jwtToken) {
            showSection(seatsSection);
            loadSeats();
        } else {
            showToast('Debe iniciar sesión para acceder a esta sección.', 'warning');
            showSection(loginSection);
        }
    });

    navLogout.addEventListener('click', (e) => {
        e.preventDefault();
        jwtToken = null;
        localStorage.removeItem('jwtToken');
        showToast('Sesión cerrada.', 'info');
        showSection(loginSection);
    });

    // Autenticación
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault(); // Prevenir el envío por defecto para manejar la validación
        
        if (!validateLoginForm()) {
            e.stopPropagation(); // Detener la propagación del evento si la validación falla
            loginForm.classList.add('was-validated'); // Añadir clase para mostrar feedback de validación
            return;
        }

        loginForm.classList.remove('was-validated'); // Remover la clase si la validación es exitosa
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch(`${AUTH_SERVICE_URL}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                jwtToken = data.token;
                localStorage.setItem('jwtToken', jwtToken);
                loginError.style.display = 'none';
                showToast('Inicio de sesión exitoso.', 'success');
                appWrapper.classList.remove('d-none'); // Mostrar el contenedor principal
                showSection(dashboardSection); // Redirigir al dashboard después del login
            } else {
                loginError.style.display = 'block';
                showToast('Credenciales incorrectas. Inténtelo de nuevo.', 'danger');
                console.error('Error de autenticación:', response.statusText);
            }
        } catch (error) {
            loginError.style.display = 'block';
            showToast('Error de red al intentar iniciar sesión.', 'danger');
            console.error('Error de red:', error);
        }
    });

    // Funciones de validación
    function validateField(field) {
        if (field.value.trim() === '') {
            field.classList.add('is-invalid');
            field.classList.remove('is-valid');
            return false;
        } else {
            field.classList.remove('is-invalid');
            field.classList.add('is-valid');
            return true;
        }
    }

    function validateLoginForm() {
        const usernameField = document.getElementById('username');
        const passwordField = document.getElementById('password');

        const isUsernameValid = validateField(usernameField);
        const isPasswordFieldValid = validateField(passwordField);

        return isUsernameValid && isPasswordFieldValid;
    }

    // Añadir event listeners para validación en tiempo real
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    usernameInput.addEventListener('input', () => validateField(usernameInput));
    usernameInput.addEventListener('blur', () => validateField(usernameInput));

    passwordInput.addEventListener('input', () => validateField(passwordInput));
    passwordInput.addEventListener('blur', () => validateField(passwordInput));

    // Función auxiliar para realizar fetch con autenticación
    async function fetchWithAuth(url, options = {}) {
        if (!jwtToken) {
            showToast('Su sesión ha expirado o no ha iniciado sesión. Por favor, inicie sesión de nuevo.', 'warning');
            showSection(loginSection);
            return null;
        }

        const headers = {
            ...options.headers,
            'Authorization': `Bearer ${jwtToken}`
        };

        const response = await fetch(url, { ...options, headers });

        if (response.status === 401) {
            showToast('Su sesión ha expirado o no está autorizado. Por favor, inicie sesión de nuevo.', 'warning');
            jwtToken = null;
            localStorage.removeItem('jwtToken');
            showSection(loginSection);
            return null;
        } else if (response.status === 403) {
            showToast('No tiene permisos para realizar esta acción.', 'danger');
            return null;
        }

        return response;
    }

    // Event Listeners para mostrar/ocultar formularios
    document.getElementById('add-movie-btn').addEventListener('click', () => {
        document.getElementById('movie-form-container').classList.remove('d-none');
        document.getElementById('movie-form').reset(); // Limpiar el formulario
        document.getElementById('movie-id').value = ''; // Asegurarse de que no haya ID para "añadir"
    });

    document.getElementById('movie-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const movieId = document.getElementById('movie-id').value;
        const movie = {
            title: document.getElementById('movie-name').value,
            description: document.getElementById('movie-description').value,
            language: document.getElementById('movie-language').value,
            category: document.getElementById('movie-category').value,
            billboard: document.getElementById('movie-billboard').value
        };

        const method = movieId ? 'PUT' : 'POST';
        const url = movieId ? `${CINEMA_SERVICE_URL}/movie/update/${movieId}` : `${CINEMA_SERVICE_URL}/movie/create`;

        try {
            const response = await fetchWithAuth(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(movie)
            });

            if (response && response.ok) {
                showToast(`Película ${movieId ? 'actualizada' : 'creada'} exitosamente.`, 'success');
                document.getElementById('movie-form-container').classList.add('d-none');
                loadMovies();
            } else if (response) {
                showToast(`Error al ${movieId ? 'actualizar' : 'crear'} película: ${response.statusText}`, 'danger');
            }
        } catch (error) {
            showToast(`Error de red al ${movieId ? 'actualizar' : 'crear'} película: ${error.message}`, 'danger');
        }
    });
    document.getElementById('cancel-movie-form').addEventListener('click', () => {
        document.getElementById('movie-form-container').classList.add('d-none');
    });

    document.getElementById('add-schedule-btn').addEventListener('click', () => {
        document.getElementById('schedule-form-container').classList.remove('d-none');
        document.getElementById('schedule-form').reset();
        document.getElementById('schedule-id').value = '';
    });

    document.getElementById('cancel-schedule-form').addEventListener('click', () => {
        document.getElementById('schedule-form-container').classList.add('d-none');
    });

    document.getElementById('add-theater-btn').addEventListener('click', () => {
        document.getElementById('theater-form-container').classList.remove('d-none');
        document.getElementById('theater-form').reset();
        document.getElementById('theater-id').value = '';
    });

    document.getElementById('cancel-theater-form').addEventListener('click', () => {
        document.getElementById('theater-form-container').classList.add('d-none');
    });

    document.getElementById('theater-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const theaterId = document.getElementById('theater-id').value;
        const theater = {
            name: document.getElementById('theater-name').value,
            capacity: document.getElementById('theater-capacity').value,
            screenType: document.getElementById('theater-screen-type').value
        };

        const method = theaterId ? 'PUT' : 'POST';
        const url = theaterId ? `${THEATER_SERVICE_URL}/theater/update/${theaterId}` : `${THEATER_SERVICE_URL}/theater/create`;

        try {
            const response = await fetchWithAuth(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(theater)
            });

            if (response && response.ok) {
                showToast(`Sala ${theaterId ? 'actualizada' : 'creada'} exitosamente.`, 'success');
                document.getElementById('theater-form-container').classList.add('d-none');
                loadTheaters();
            } else if (response) {
                showToast(`Error al ${theaterId ? 'actualizar' : 'crear'} sala: ${response.statusText}`, 'danger');
            }
        } catch (error) {
            showToast(`Error de red al ${theaterId ? 'actualizar' : 'crear'} sala: ${error.message}`, 'danger');
        }
    });

    document.getElementById('schedule-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        const scheduleId = document.getElementById('schedule-id').value;
        const schedule = {
            movieId: document.getElementById('schedule-movie-id').value,
            startTime: document.getElementById('schedule-start-time').value
        };

        const method = scheduleId ? 'PUT' : 'POST';
        const url = scheduleId ? `${CINEMA_SERVICE_URL}/schedule/update/${scheduleId}` : `${CINEMA_SERVICE_URL}/schedule/create`;

        try {
            const response = await fetchWithAuth(url, {
                method: method,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(schedule)
            });

            if (response && response.ok) {
                showToast(`Horario ${scheduleId ? 'actualizado' : 'creado'} exitosamente.`, 'success');
                document.getElementById('schedule-form-container').classList.add('d-none');
                loadSchedules();
            } else if (response) {
                showToast(`Error al ${scheduleId ? 'actualizar' : 'crear'} horario: ${response.statusText}`, 'danger');
            }
        } catch (error) {
            showToast(`Error de red al ${scheduleId ? 'actualizar' : 'crear'} horario: ${error.message}`, 'danger');
        }
    });
    async function loadMovies() {
        console.log('Cargando películas...');
        const moviesList = document.getElementById('movies-list');
        moviesList.innerHTML = 'Cargando...';
        try {
            const response = await fetchWithAuth(`${CINEMA_SERVICE_URL}/movie/find-all`);
            if (response && response.ok) {
                const movies = await response.json();
                moviesList.innerHTML = '';
                if (movies.length === 0) {
                    moviesList.innerHTML = '<p>No hay películas disponibles.</p>';
                } else {
                    movies.forEach(movie => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <span>${movie.title} (${movie.genre}) - ${movie.duration} min</span>
                            <div>
                                <button class="edit-movie-btn" data-id="${movie.id}">Editar</button>
                                <button class="delete-movie-btn" data-id="${movie.id}">Eliminar</button>
                            </div>
                        `;
                        moviesList.appendChild(li);
                    });
                    // Añadir event listeners a los botones de editar y eliminar
                    document.querySelectorAll('.edit-movie-btn').forEach(button => {
                        button.addEventListener('click', (e) => editMovie(e.target.dataset.id));
                    });
                    document.querySelectorAll('.delete-movie-btn').forEach(button => {
                        button.addEventListener('click', (e) => deleteMovie(e.target.dataset.id));
                    });
                }
            } else if (response) {
                console.error('Error al cargar películas:', response.statusText);
                moviesList.innerHTML = `<p>Error al cargar películas: ${response.statusText}</p>`;
            }
        } catch (error) {
            console.error('Error de red al cargar películas:', error);
            moviesList.innerHTML = `<p>Error de red al cargar películas: ${error.message}</p>`;
        }
    }

    async function editMovie(id) {
        console.log('Editando película con ID:', id);
        try {
            const response = await fetchWithAuth(`${CINEMA_SERVICE_URL}/movie/find/${id}`);
            if (response && response.ok) {
                const movie = await response.json();
                document.getElementById('movie-id').value = movie.id;
                document.getElementById('movie-name').value = movie.title;
                document.getElementById('movie-description').value = movie.description;
                document.getElementById('movie-language').value = movie.language;
                document.getElementById('movie-category').value = movie.category;
                document.getElementById('movie-billboard').value = movie.billboard;
                document.getElementById('movie-form-container').classList.remove('hidden');
            } else if (response) {
                showToast('Error al cargar datos de la película para editar: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al cargar datos de la película para editar: ' + error.message, 'danger');
        }
    }

    async function deleteMovie(id) {
        console.log('Eliminando película con ID:', id);
        if (!confirm('¿Está seguro de que desea eliminar esta película?')) {
            return;
        }
        try {
            const response = await fetchWithAuth(`${CINEMA_SERVICE_URL}/movie/delete/${id}`, {
                method: 'DELETE'
            });
            if (response && response.ok) {
                showToast('Película eliminada exitosamente.', 'success');
                loadMovies();
            } else if (response) {
                showToast('Error al eliminar película: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al eliminar película: ' + error.message, 'danger');
        }
    }

    async function editSchedule(id) {
        console.log('Editando horario con ID:', id);
        try {
            const response = await fetchWithAuth(`${CINEMA_SERVICE_URL}/schedule/find/${id}`);
            if (response && response.ok) {
                const schedule = await response.json();
                document.getElementById('schedule-id').value = schedule.id;
                document.getElementById('schedule-movie-id').value = schedule.movieId;
                // Formatear la fecha y hora para el input datetime-local
                const startTime = new Date(schedule.startTime);
                const year = startTime.getFullYear();
                const month = (startTime.getMonth() + 1).toString().padStart(2, '0');
                const day = startTime.getDate().toString().padStart(2, '0');
                const hours = startTime.getHours().toString().padStart(2, '0');
                const minutes = startTime.getMinutes().toString().padStart(2, '0');
                document.getElementById('schedule-start-time').value = `${year}-${month}-${day}T${hours}:${minutes}`;
                document.getElementById('schedule-form-container').classList.remove('hidden');
            } else if (response) {
                showToast('Error al cargar datos del horario para editar: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al cargar datos del horario para editar: ' + error.message, 'danger');
        }
    }

    async function deleteSchedule(id) {
        console.log('Eliminando horario con ID:', id);
        if (!confirm('¿Está seguro de que desea eliminar este horario?')) {
            return;
        }
        try {
            const response = await fetchWithAuth(`${CINEMA_SERVICE_URL}/schedule/delete/${id}`, {
                method: 'DELETE'
            });
            if (response && response.ok) {
                showToast('Horario eliminado exitosamente.', 'success');
                loadSchedules();
            } else if (response) {
                showToast('Error al eliminar horario: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al eliminar horario: ' + error.message, 'danger');
        }
    }

    async function editTheater(id) {
        console.log('Editando sala con ID:', id);
        try {
            const response = await fetchWithAuth(`${THEATER_SERVICE_URL}/theater/find/${id}`);
            if (response && response.ok) {
                const theater = await response.json();
                document.getElementById('theater-id').value = theater.id;
                document.getElementById('theater-name').value = theater.name;
                document.getElementById('theater-capacity').value = theater.capacity;
                document.getElementById('theater-screen-type').value = theater.screenType;
                document.getElementById('theater-form-container').classList.remove('hidden');
            } else if (response) {
                showToast('Error al cargar datos de la sala para editar: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al cargar datos de la sala para editar: ' + error.message, 'danger');
        }
    }

    async function deleteTheater(id) {
        console.log('Eliminando sala con ID:', id);
        if (!confirm('¿Está seguro de que desea eliminar esta sala?')) {
            return;
        }
        try {
            const response = await fetchWithAuth(`${THEATER_SERVICE_URL}/theater/delete/${id}`, {
                method: 'DELETE'
            });
            if (response && response.ok) {
                showToast('Sala eliminada exitosamente.', 'success');
                loadTheaters();
            } else if (response) {
                showToast('Error al eliminar sala: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al eliminar sala: ' + error.message, 'danger');
        }
    }

    async function editSeat(id) {
        console.log('Editando asiento con ID:', id);
        try {
            const response = await fetchWithAuth(`${THEATER_SERVICE_URL}/seats/find/${id}`);
            if (response && response.ok) {
                const seat = await response.json();
                document.getElementById('seat-id').value = seat.id;
                document.getElementById('seat-number').value = seat.seatNumber;
                document.getElementById('seat-price').value = seat.price;
                document.getElementById('seat-is-available').checked = seat.isAvailable;
                document.getElementById('seat-theater-id').value = seat.theaterId;
                document.getElementById('seat-form-container').classList.remove('hidden');
            } else if (response) {
                showToast('Error al cargar datos del asiento para editar: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al cargar datos del asiento para editar: ' + error.message, 'danger');
        }
    }

    async function deleteSeat(id) {
        console.log('Eliminando asiento con ID:', id);
        if (!confirm('¿Está seguro de que desea eliminar este asiento?')) {
            return;
        }
        try {
            const response = await fetchWithAuth(`${THEATER_SERVICE_URL}/seats/delete/${id}`, {
                method: 'DELETE'
            });
            if (response && response.ok) {
                showToast('Asiento eliminado exitosamente.', 'success');
                loadSeats();
            } else if (response) {
                showToast('Error al eliminar asiento: ' + response.statusText, 'danger');
            }
        } catch (error) {
            showToast('Error de red al eliminar asiento: ' + error.message, 'danger');
        }
    }

    async function loadSeats() {
        console.log('Cargando horarios...');
        const schedulesList = document.getElementById('schedules-list');
        schedulesList.innerHTML = 'Cargando...';
        try {
            const response = await fetchWithAuth(`${CINEMA_SERVICE_URL}/schedule/find-all`);
            if (response && response.ok) {
                const schedules = await response.json();
                schedulesList.innerHTML = '';
                if (schedules.length === 0) {
                    schedulesList.innerHTML = '<p>No hay horarios disponibles.</p>';
                } else {
                    schedules.forEach(schedule => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <span>ID Horario: ${schedule.id}, ID Película: ${schedule.movieId}, Hora de Inicio: ${new Date(schedule.startTime).toLocaleString()}</span>
                            <div>
                                <button class="edit-schedule-btn" data-id="${schedule.id}">Editar</button>
                                <button class="delete-schedule-btn" data-id="${schedule.id}">Eliminar</button>
                            </div>
                        `;
                        schedulesList.appendChild(li);
                    });
                    document.querySelectorAll('.edit-schedule-btn').forEach(button => {
                        button.addEventListener('click', (e) => editSchedule(e.target.dataset.id));
                    });
                    document.querySelectorAll('.delete-schedule-btn').forEach(button => {
                        button.addEventListener('click', (e) => deleteSchedule(e.target.dataset.id));
                    });
                }
            } else if (response) {
                console.error('Error al cargar horarios:', response.statusText);
                schedulesList.innerHTML = `<p>Error al cargar horarios: ${response.statusText}</p>`;
            }
        } catch (error) {
            console.error('Error de red al cargar horarios:', error);
            schedulesList.innerHTML = `<p>Error de red al cargar horarios: ${error.message}</p>`;
        }
    }

    async function loadTheaters() {
        console.log('Cargando salas...');
        const theatersList = document.getElementById('theaters-list');
        theatersList.innerHTML = 'Cargando...';
        try {
            const response = await fetchWithAuth(`${THEATER_SERVICE_URL}/theater/find-all`);
            if (response && response.ok) {
                const theaters = await response.json();
                theatersList.innerHTML = '';
                if (theaters.length === 0) {
                    theatersList.innerHTML = '<p>No hay salas disponibles.</p>';
                } else {
                    theaters.forEach(theater => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <span>Nombre: ${theater.name}, Capacidad: ${theater.capacity}, Tipo de Pantalla: ${theater.screenType}</span>
                            <div>
                                <button class="edit-theater-btn" data-id="${theater.id}">Editar</button>
                                <button class="delete-theater-btn" data-id="${theater.id}">Eliminar</button>
                            </div>
                        `;
                        theatersList.appendChild(li);
                    });
                    document.querySelectorAll('.edit-theater-btn').forEach(button => {
                        button.addEventListener('click', (e) => editTheater(e.target.dataset.id));
                    });
                    document.querySelectorAll('.delete-theater-btn').forEach(button => {
                        button.addEventListener('click', (e) => deleteTheater(e.target.dataset.id));
                    });
                }
            } else if (response) {
                console.error('Error al cargar salas:', response.statusText);
                theatersList.innerHTML = `<p>Error al cargar salas: ${response.statusText}</p>`;
            }
        } catch (error) {
            console.error('Error de red al cargar salas:', error);
            theatersList.innerHTML = `<p>Error de red al cargar salas: ${error.message}</p>`;
        }
    }

    async function loadSeats() {
        console.log('Cargando asientos...');
        const seatsList = document.getElementById('seats-list');
        seatsList.innerHTML = 'Cargando...';
        try {
            const response = await fetchWithAuth(`${THEATER_SERVICE_URL}/seats/find-all`);
            if (response && response.ok) {
                const seats = await response.json();
                seatsList.innerHTML = '';
                if (seats.length === 0) {
                    seatsList.innerHTML = '<p>No hay asientos disponibles.</p>';
                } else {
                    seats.forEach(seat => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <span>ID Asiento: ${seat.id}, Número: ${seat.seatNumber}, Precio: ${seat.price}, Disponible: ${seat.isAvailable ? 'Sí' : 'No'}, ID Sala: ${seat.theaterId}</span>
                            <div>
                                <button class="edit-seat-btn" data-id="${seat.id}">Editar</button>
                                <button class="delete-seat-btn" data-id="${seat.id}">Eliminar</button>
                            </div>
                        `;
                        seatsList.appendChild(li);
                    });
                    document.querySelectorAll('.edit-seat-btn').forEach(button => {
                        button.addEventListener('click', (e) => editSeat(e.target.dataset.id));
                    });
                    document.querySelectorAll('.delete-seat-btn').forEach(button => {
                        button.addEventListener('click', (e) => deleteSeat(e.target.dataset.id));
                    });
                }
            } else if (response) {
                console.error('Error al cargar asientos:', response.statusText);
                seatsList.innerHTML = `<p>Error al cargar asientos: ${response.statusText}</p>`;
            }
        } catch (error) {
            console.error('Error de red al cargar asientos:', error);
            seatsList.innerHTML = `<p>Error de red al cargar asientos: ${error.message}</p>`;
        }
    }

    // Inicialización al cargar la página
    if (jwtToken) {
        showSection(moviesSection);
        loadMovies();
    } else {
        showSection(loginSection);
    }
});