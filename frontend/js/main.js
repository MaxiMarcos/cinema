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
            <div class="col-6 col-md-3">
                <div class="card movie-card">
                    <img src="http://localhost:9011/img/${movie.photo}" class="card-img-top" alt="${movie.name}">
                    <div class="card-body">
                        <h5 class="card-title">${movie.name}</h5>
                        <p class="card-text">${movie.category} · ${movie.language}</p>
                        <a href="movie.html?id=${movie.id}" class="btn btn-primary w-100">Comprar</a>
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
});
