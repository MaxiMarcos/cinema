const movieCards = document.querySelectorAll('.movie-card');



async function fetchMovies(type, isLanguage = false) {
    try {
        let url;
        if (isLanguage) {
            url = `http://localhost:9011/movie/findbylanguage/${type}`;
        } else {
            url = `http://localhost:9011/movie/findbybillboard/${type}`;
        }
        console.log(`Fetching from URL: ${url}`); // Log de la URL
        const response = await fetch(url);
        if (!response.ok) {
            console.error(`HTTP error! status: ${response.status} for URL: ${url}`); // Log de error HTTP
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const movies = await response.json();
        console.log(`Response from ${url}:`, movies); // Log de la respuesta
        return movies;
    } catch (error) {
        console.error('Error fetching movies:', error);
        return [];
    }
}

function renderMovies(movies, selector) {
    const container = document.querySelector(selector);
    if (!container) {
        console.error(`Container with selector ${selector} not found.`);
        return;
    }
    console.log(`Rendering movies to ${selector}:`, movies); // Log de las películas a renderizar
    container.innerHTML = ''; // Clear existing content

    movies.forEach(movie => {
        const movieCard = `
            <div class="col-12 col-md-6 col-lg-4">
                <div class="card movie-card h-100 bg-black text-white">
                    <div class="row g-0">
                        <div class="col-md-6">
                            <img src="http://localhost:9011/img/${movie.photo}" class="img-fluid rounded-start" alt="${movie.name}">
                        </div>
                        <div class="col-md-6">
                            <div class="card-body">
                                <h5 class="card-title">${movie.name}</h5>
                                <p class="card-text">${movie.category} · ${movie.language}</p>
                                <a href="movie.html?id=${movie.id}" class="btn btn-danger w-100">Comprar</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `;
        container.innerHTML += movieCard;
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    // Cargar y renderizar películas para "Estrenos" (Billboard.ONSALE) por defecto
    let currentMovies = await fetchMovies('ONSALE');
    renderMovies(currentMovies, '#moviesEstrenos');

    const filterButtons = document.querySelectorAll('.filter-buttons .btn');

    filterButtons.forEach(button => {
        button.addEventListener('click', async (event) => {
            filterButtons.forEach(btn => btn.classList.remove('active'));
            event.target.classList.add('active');

            const filterType = event.target.dataset.filter;
            let moviesToRender = [];

            if (filterType === 'subtitled') {
                moviesToRender = await fetchMovies('English', true);
            } else if (filterType === 'castellano') {
                moviesToRender = await fetchMovies('Spanish', true);
            } else if (filterType === 'premieres') {
                moviesToRender = await fetchMovies('ONSALE'); // Mostrar todas las películas de estreno
            }
            renderMovies(moviesToRender, '#moviesEstrenos');
        });
    });

    // Dark Mode Toggle Logic
    const darkModeToggle = document.getElementById('darkModeToggle');
    const body = document.body;
    const darkModeIcon = document.getElementById('darkModeIcon');

    // Check for saved theme preference
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'light-mode') {
        body.classList.add('light-mode');
        darkModeIcon.src = 'img/modooscuro.png';
        darkModeIcon.alt = 'Modo Oscuro';
    } else {
        darkModeIcon.src = 'img/modoclaro.png';
        darkModeIcon.alt = 'Modo Claro';
    }

    darkModeToggle.addEventListener('click', () => {
        body.classList.toggle('light-mode');
        if (body.classList.contains('light-mode')) {
            localStorage.setItem('theme', 'light-mode');
            darkModeIcon.src = 'img/modooscuro.png';
            darkModeIcon.alt = 'Modo Oscuro';
        } else {
            localStorage.setItem('theme', 'dark-mode');
            darkModeIcon.src = 'img/modoclaro.png';
            darkModeIcon.alt = 'Modo Claro';
        }
    });
});
