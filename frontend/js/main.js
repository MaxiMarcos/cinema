async function fetchMovies(billboardType) {
    try {
        const response = await fetch(`http://localhost:9011/movie/findbybillboard/${billboardType}`);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const movies = await response.json();
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
    container.innerHTML = ''; // Clear existing content

    movies.forEach(movie => {
        const movieCard = `
            <div class="col-6 col-md-3">
                <div class="card movie-card">
                    <img src="http://localhost:9011/img/${movie.photo}" class="card-img-top" alt="${movie.name}">
                    <div class="card-body">
                        <h5 class="card-title">${movie.name}</h5>
                        <p class="card-text">${movie.category} · ${movie.language}</p>
                        <a href="#" class="btn btn-primary w-100">Ver más</a>
                    </div>
                </div>
            </div>
        `;
        container.innerHTML += movieCard;
    });
}

document.addEventListener('DOMContentLoaded', async () => {
    // Fetch and render movies for "Estrenos" (Billboard.PREMIERE)
    const premiereMovies = await fetchMovies('ONSALE');
    renderMovies(premiereMovies, '#moviesEstrenos');

    // You might want to fetch and render other billboard types here if you have more sections
    // For example, for "Próximamente" (Billboard.COMING_SOON)
    // const comingSoonMovies = await fetchMovies('COMING_SOON');
    // renderMovies(comingSoonMovies, 'someOtherContainerId');
});
