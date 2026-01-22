const movieCards = document.querySelectorAll('.movie-card'); // This line is unused but I'll keep it as it was in the original file.

async function loadMovies(billboardFilter = 'ONSALE') {
    try {
        const url = `http://localhost:9011/movie/findbybillboard/${billboardFilter}`;
        console.log(`Fetching from URL: ${url}`);
        const response = await fetch(url);
        if (!response.ok) {
            console.error(`HTTP error! status: ${response.status} for URL: ${url}`);
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const movies = await response.json();
        console.log(`Response from ${url}:`, movies);
        return { movies: movies, count: movies.length }; // Return movies and their count
    } catch (error) {
        console.error('Error fetching movies:', error);
        return { movies: [], count: 0 };
    }
}

function renderMovies(movies, selector) {
    const container = document.querySelector(selector);
    if (!container) {
        console.error(`Container with selector ${selector} not found.`);
        return;
    }
    console.log(`Rendering movies to ${selector}:`, movies);
    container.innerHTML = '';

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

function updateFilterLinkState(activeLinkId) {
    const filterInBillboardLink = document.getElementById('filter-in-billboard');
    const filterComingSoonLink = document.getElementById('filter-coming-soon');

    if (filterInBillboardLink) filterInBillboardLink.classList.remove('active');
    if (filterComingSoonLink) filterComingSoonLink.classList.remove('active');

    if (activeLinkId === 'filter-in-billboard' && filterInBillboardLink) {
        filterInBillboardLink.classList.add('active');
    } else if (activeLinkId === 'filter-coming-soon' && filterComingSoonLink) {
        filterComingSoonLink.classList.add('active');
    }
}

async function updateAllBadgeCounts() {
    const estrenosData = await loadMovies('ONSALE');
    const proximamenteData = await loadMovies('SOON');

    const badgeInBillboard = document.getElementById('badge-in-billboard');
    const badgeComingSoon = document.getElementById('badge-coming-soon');

    if (badgeInBillboard) {
        badgeInBillboard.textContent = estrenosData.count;
    }
    if (badgeComingSoon) {
        badgeComingSoon.textContent = proximamenteData.count;
    }
}

document.addEventListener('DOMContentLoaded', async () => {
    const moviesEstrenosContainer = document.querySelector('#moviesEstrenos');
    const filterInBillboardLink = document.getElementById('filter-in-billboard');
    const filterComingSoonLink = document.getElementById('filter-coming-soon');

    // Initial load: default to "Estrenos" (ONSALE)
    if (moviesEstrenosContainer) {
        const { movies: currentMovies } = await loadMovies('ONSALE'); // Destructure to get only movies
        renderMovies(currentMovies, '#moviesEstrenos');
        updateFilterLinkState('filter-in-billboard'); // Set "Estrenos" as active initially
        await updateAllBadgeCounts(); // Update all badge counts
    }

    // Event listeners for the new filter links
    if (filterInBillboardLink) {
        filterInBillboardLink.addEventListener('click', async (event) => {
            event.preventDefault();
            updateFilterLinkState('filter-in-billboard');
            const { movies: moviesToRender } = await loadMovies('ONSALE');
            renderMovies(moviesToRender, '#moviesEstrenos');
            await updateAllBadgeCounts(); // Update all badge counts
        });
    }

    if (filterComingSoonLink) {
        filterComingSoonLink.addEventListener('click', async (event) => {
            event.preventDefault();
            updateFilterLinkState('filter-coming-soon');
            const { movies: moviesToRender } = await loadMovies('SOON');
            renderMovies(moviesToRender, '#moviesEstrenos');
            await updateAllBadgeCounts(); // Update all badge counts
        });
    }

    // Lógica para la navbar transparente que se solidifica al hacer scroll
    const navbar = document.querySelector('.nav');
    if (navbar) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                navbar.classList.add('nav-scrolled');
            } else {
                navbar.classList.remove('nav-scrolled');
            }
        });
    }
});
