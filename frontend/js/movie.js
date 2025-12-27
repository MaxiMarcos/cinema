document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const movieId = urlParams.get('id');

    if (movieId) {
        console.log('Movie ID from URL:', movieId);
        fetchMovieDetail(movieId);
        fetchSchedules(movieId);
    } else {
        console.error('No movie ID found in URL.');
        // Redirigir o mostrar un mensaje de error si no hay ID
    }
});

async function fetchMovieDetail(movieId) {
    try {
        const url = `http://localhost:9011/movie/find/${movieId}`;
        console.log(`Fetching movie detail from URL: ${url}`);
        const response = await fetch(url);
        if (!response.ok) {
            console.error(`HTTP error! status: ${response.status} for URL: ${url}`);
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const movie = await response.json();
        console.log(`Response for movie ID ${movieId}:`, movie);
        renderMovieDetail(movie);
    } catch (error) {
        console.error('Error fetching movie details:', error);
    }
}

async function fetchSchedules(movieId) {
    try {
        const url = `http://localhost:9011/schedule/findschedulebymovie/${movieId}`;
        console.log(`Fetching schedules for movie ID ${movieId} from URL: ${url}`);
        const response = await fetch(url);
        if (!response.ok) {
            console.error(`HTTP error! status: ${response.status} for URL: ${url}`);
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const schedules = await response.json();
        console.log(`Response for schedules for movie ID ${movieId}:`, schedules);
        renderSchedules(schedules);
    } catch (error) {
        console.error('Error fetching schedules:', error);
    }
}

function renderMovieDetail(movie) {
    const movieDetailContainer = document.getElementById('movie-detail');
    if (!movieDetailContainer) {
        console.error('Movie detail container not found.');
        return;
    }

    movieDetailContainer.innerHTML = `
        <div class="col-md-4">
            <img src="http://localhost:9011/img/${movie.photo}" class="img-fluid rounded-start" alt="${movie.name}">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <h1 class="card-title">${movie.name}</h1>
                <p class="card-text"><strong>Categoría:</strong> ${movie.category}</p>
                <p class="card-text"><strong>Idioma:</strong> ${movie.language}</p>
                <p class="card-text"><strong>Subtítulos:</strong> ${movie.subtitle ? movie.subtitle : 'N/A'}</p>
                <p class="card-text">${movie.description}</p>
            </div>
        </div>
    `;
}

function renderSchedules(schedules) {
    const schedulesContainer = document.getElementById('schedules-container');
    if (!schedulesContainer) {
        console.error('Schedules container not found.');
        return;
    }

    if (schedules.length === 0) {
        schedulesContainer.innerHTML = '<p>No hay horarios disponibles para esta película.</p>';
        return;
    }

    // Ordenar los horarios del más próximo al más lejano
    schedules.sort((a, b) => new Date(a.startTime) - new Date(b.startTime));

    schedulesContainer.innerHTML = schedules.map(schedule => {
        const startTime = new Date(schedule.startTime);
        const formattedDate = startTime.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: '2-digit' });
        const formattedTime = startTime.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });
        return `
            <div class="col-md-3 mb-3">
                <div class="card text-center bg-black">
                    <div class="card-body text-white">
                        <h5 class="card-title">${formattedDate} ${formattedTime}</h5>
                        <a href="reserve.html?scheduleId=${schedule.id}&theaterId=${schedule.theaterId}" class="btn btn-danger">Seleccionar</a>
                    </div>
                </div>
            </div>
        `;
    }).join('');
}