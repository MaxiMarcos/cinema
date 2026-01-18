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
            <div class="col-12 col-md-6 col-lg-4 mb-4">
                <div class="card movie-card bg-black text-white">
                    <div class="movie-image-wrapper">
                        <a href="movie.html?id=${movie.id}">
                            <img src="http://localhost:9011/img/${movie.photo}" class="img-fluid rounded-start" alt="${movie.name}">
                        </a>
                        <a href="movie.html?id=${movie.id}" class="btn btn-warning movie-card-cta">Ver horarios</a>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${movie.name}</h5>
                    </div>
                </div>
            </div>
        `;
        container.innerHTML += movieCard;
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    // Cargar y renderizar películas para "Estrenos" (Billboard.ONSALE) por defecto
    const moviesEstrenosContainer = document.querySelector('#moviesEstrenos');
    if (moviesEstrenosContainer) {
        let currentMovies = await fetchMovies('ONSALE');
        renderMovies(currentMovies, '#moviesEstrenos');
    }

    const filterButtons = document.querySelectorAll('.filter-buttons .btn');

    filterButtons.forEach(button => {
        button.addEventListener('click', async (event) => {
            filterButtons.forEach(btn => btn.classList.remove('active'));
            event.target.classList.add('active');

            const filterType = event.target.dataset.filter;
            let moviesToRender = [];

            if (filterType === 'ONSALE') {
                moviesToRender = await fetchMovies('ONSALE');
            } else if (filterType === 'SOON') {
                moviesToRender = await fetchMovies('SOON');
            }
            renderMovies(moviesToRender, '#moviesEstrenos');
        });
});

    // Lógica para la navbar transparente que se solidifica al hacer scroll
    const navbar = document.querySelector('.nav');
    if (navbar) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) { // Cambia a sólido después de 50px de scroll
                navbar.classList.add('nav-scrolled');
            } else {
                navbar.classList.remove('nav-scrolled');
            }
        });
    }
});
