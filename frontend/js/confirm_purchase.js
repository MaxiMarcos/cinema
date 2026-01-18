document.addEventListener('DOMContentLoaded', () => {
    const detailsContainer = document.getElementById('details-container');
    const confirmPurchaseBtn = document.getElementById('confirm-purchase-btn');
    const cancelPurchaseBtn = document.getElementById('cancel-purchase-btn');
    const reservationMessageDiv = document.getElementById('reservation-message');

    const getUrlParameter = (name) => {
        name = name.replace(/[[\]]/g, '\\$&');
        const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        const results = regex.exec(window.location.href);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    };

    const purchaseIdsParam = getUrlParameter('purchaseIds');
    const statusParam = getUrlParameter('status');
    let purchaseIds = [];

    if (statusParam === 'success') {
        if (reservationMessageDiv) {
            reservationMessageDiv.textContent = '¡Tus asientos han sido seleccionados y tu reserva está pendiente de confirmación!';
            reservationMessageDiv.style.display = 'block';
        }
    }

    if (purchaseIdsParam) {
        purchaseIds = purchaseIdsParam.split(',').map(id => parseInt(id, 10));
        fetchPurchaseDetails(purchaseIds);
    } else {
        detailsContainer.innerHTML = '<p>No se encontraron reservas pendientes.</p>';
        confirmPurchaseBtn.disabled = true;
        cancelPurchaseBtn.disabled = true;
    }

    async function fetchPurchaseDetails(ids) {
        detailsContainer.innerHTML = '<p>Cargando detalles de la reserva...</p>';
        let allDetails = [];
        let totalAmount = 0;

        for (const id of ids) {
            try {
                const response = await fetch(`http://localhost:9002/purchase/get/${id}`);
                if (!response.ok) {
                    throw new Error(`Error al obtener detalles de la compra ${id}`);
                }
                const purchaseItem = await response.json();
                allDetails.push(purchaseItem);
                totalAmount += purchaseItem.totalPrice || 0;
            } catch (error) {
                console.error(`Error fetching purchase item ${id}:`, error);
                detailsContainer.innerHTML = `<p>Error al cargar detalles para la reserva ${id}.</p>`;
                return;
            }
        }

        // Asumiendo que todos los purchaseItems pertenecen al mismo schedule
        const firstPurchaseItem = allDetails[0];
        const scheduleId = firstPurchaseItem.scheduleId;

        try {
            const { schedule, movie, theater } = await fetchScheduleAndMovieDetails(scheduleId, firstPurchaseItem);
            renderPurchaseDetails(allDetails, totalAmount, movie, schedule, theater);
        } catch (error) {
            console.error('Error al obtener detalles adicionales de la reserva:', error);
            detailsContainer.innerHTML = '<p>Error al cargar detalles adicionales de la reserva.</p>';
        }
    }

    async function fetchScheduleAndMovieDetails(scheduleId, firstPurchaseItem) {
        const scheduleResponse = await fetch(`http://localhost:9011/schedule/find/${scheduleId}`);
        if (!scheduleResponse.ok) {
            throw new Error(`Error al obtener detalles del horario ${scheduleId}`);
        }
        const schedule = await scheduleResponse.json();

        const movieResponse = await fetch(`http://localhost:9011/movie/find/${schedule.movie_id}`);
        if (!movieResponse.ok) {
            throw new Error(`Error al obtener detalles de la película ${schedule.movieId}`);
        }
        const movie = await movieResponse.json();

        const theaterResponse = await fetch(`http://localhost:9006/theater/find/${schedule.theaterId}`);
        if (!theaterResponse.ok) {
            throw new Error(`Error al obtener detalles de la sala ${schedule.theaterId}`);
        }
        const theater = await theaterResponse.json();

        return { schedule, movie, theater };
    }

    function renderPurchaseDetails(details, totalAmount, movie, schedule, theater) {
        if (details.length === 0) {
            detailsContainer.innerHTML = '<p>No hay detalles de reserva para mostrar.</p>';
            return;
        }

        const seatIds = details.map(item => item.seatId);
        const formattedDate = new Date(schedule.startTime).toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' });
        const formattedTime = new Date(schedule.startTime).toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });

        detailsContainer.innerHTML = `
            <p><strong>Película:</strong> ${movie.name}</p>
            <p><strong>Fecha:</strong> ${formattedDate}</p>
            <p><strong>Hora:</strong> ${formattedTime}</p>
            <p><strong>Sala:</strong> ${theater.name}</p>
            <p><strong>Asientos:</strong> ${seatIds.join(', ')}</p>
            <p><strong>Total:</strong> $${totalAmount.toFixed(2)}</p>
        `;
    }

    confirmPurchaseBtn.addEventListener('click', async () => {
        if (purchaseIds.length === 0) return;

        try {
            for (const id of purchaseIds) {
                const response = await fetch(`http://localhost:9002/purchase/editStatus/status?id=${id}&status=COMPLETED`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                if (!response.ok) {
                    throw new Error(`Error al confirmar la compra ${id}`);
                }
            }
            const allPurchaseDetails = [];
            for (const id of purchaseIds) {
                const response = await fetch(`http://localhost:9002/purchase/get/${id}`);
                if (!response.ok) {
                    throw new Error(`Error al obtener detalles de la compra ${id}`);
                }
                const purchaseItem = await response.json();
                allPurchaseDetails.push(purchaseItem);
            }

            // Asumiendo que todos los purchaseItems pertenecen al mismo schedule y movie
            const firstPurchaseItem = allPurchaseDetails[0];
            const scheduleId = firstPurchaseItem.scheduleId;
            const seatIds = allPurchaseDetails.map(item => item.seatId);
            const totalAmount = allPurchaseDetails.reduce((sum, item) => sum + (item.totalPrice || 0), 0);

            // Obtener detalles del horario
            const scheduleResponse = await fetch(`http://localhost:9011/schedule/find/${scheduleId}`);
            if (!scheduleResponse.ok) {
                throw new Error(`Error al obtener detalles del horario ${scheduleId}`);
            }
            const schedule = await scheduleResponse.json();

            // Obtener detalles de la película
            const movieResponse = await fetch(`http://localhost:9011/movie/find/${schedule.movie_id}`);
            if (!movieResponse.ok) {
                throw new Error(`Error al obtener detalles de la película ${schedule.movieId}`);
            }
            const movie = await movieResponse.json();

            // Obtener detalles de la sala
            const theaterResponse = await fetch(`http://localhost:9006/theater/find/${schedule.theaterId}`);
            if (!theaterResponse.ok) {
                throw new Error(`Error al obtener detalles de la sala ${schedule.theaterId}`);
            }
            const theater = await theaterResponse.json();

            const formattedDate = new Date(schedule.startTime).toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' });
            const formattedTime = new Date(schedule.startTime).toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });
            const reservationCode = firstPurchaseItem.reservationCode; // Usar el código de reserva del backend

            const queryParams = new URLSearchParams();
            queryParams.append('movieName', movie.name);
            queryParams.append('date', formattedDate);
            queryParams.append('time', formattedTime);
            queryParams.append('theaterName', theater.name);
            queryParams.append('seats', seatIds.join(', '));
            queryParams.append('total', totalAmount.toFixed(2));
            queryParams.append('reservationCode', reservationCode);

            window.location.href = `purchase_confirmation.html?${queryParams.toString()}`;
        } catch (error) {
            console.error('Error al confirmar la compra:', error);
            alert('Hubo un error al confirmar tu compra. Inténtalo de nuevo.');
        }
    });

    cancelPurchaseBtn.addEventListener('click', async () => {
        if (purchaseIds.length === 0) return;

        try {
            for (const id of purchaseIds) {
                const response = await fetch(`http://localhost:9002/purchase/editStatus/status?id=${id}&status=CANCELED`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                if (!response.ok) {
                    throw new Error(`Error al cancelar la reserva ${id}`);
                }
            }
            alert('Reserva cancelada.');
            window.location.href = 'index.html'; // Redirigir a la página principal
        } catch (error) {
            console.error('Error al cancelar la reserva:', error);
            alert('Hubo un error al cancelar tu reserva. Inténtalo de nuevo.');
        }
    });
});
