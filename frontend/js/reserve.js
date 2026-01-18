document.addEventListener('DOMContentLoaded', () => {
    const seatGrid = document.getElementById('seat-grid');
    const confirmSelectionBtn = document.getElementById('confirm-selection');

    // Función para obtener parámetros de la URL
    const getUrlParameter = (name) => {
        name = name.replace(/[[\]]/g, '\\$&');
        const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        const results = regex.exec(window.location.href);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    };

    const scheduleId = getUrlParameter('scheduleId');
    const theaterId = getUrlParameter('theaterId');

    if (scheduleId && theaterId) {
        console.log(`Schedule ID: ${scheduleId}, Theater ID: ${theaterId}`);
        fetchSeats(theaterId, scheduleId);
    } else {
        console.error('Schedule ID o Theater ID no encontrados en la URL.');
        seatGrid.innerHTML = '<p>No se pudo cargar la información de la función.</p>';
    }

    // Lógica de selección de asientos
    seatGrid.addEventListener('click', (e) => {
        if (!e.target.classList.contains('seat')) return;
        if (e.target.classList.contains('occupied')) return;

        e.target.classList.toggle('selected');
    });

    confirmSelectionBtn.addEventListener('click', async () => {
        const selectedSeats = Array.from(document.querySelectorAll('.seat.selected')).map(seat => seat.dataset.seatId);
        if (selectedSeats.length > 0) {
            try {
                const createdPurchaseIds = [];
                for (const seatId of selectedSeats) {
                    const response = await fetch(`http://localhost:9002/purchase/create-pending?scheduleId=${scheduleId}&seatId=${seatId}`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    });
                    if (!response.ok) {
                        throw new Error(`Error al crear PurchaseItem para el asiento ${seatId}`);
                    }
                    const purchaseItem = await response.json();
                    createdPurchaseIds.push(purchaseItem.id);
                    console.log(`PurchaseItem creado para el asiento ${seatId} con ID: ${purchaseItem.id}`);
                }
                const redirectUrl = `confirm_purchase.html?purchaseIds=${createdPurchaseIds.join(',')}&status=success`;
                window.location.href = redirectUrl;            } catch (error) {
                console.error('Error al procesar la reserva:', error);
                alert('Hubo un error al procesar tu reserva. Inténtalo de nuevo.');
            }
        } else {
            alert('Por favor, selecciona al menos un asiento.');
        }
    });

    async function fetchSeats(theaterId, scheduleId) {
        try {
            const [seatsResponse, occupiedSeatsResponse] = await Promise.all([
                fetch(`http://localhost:9006/seat/byTheater/${theaterId}`),
                fetch(`http://localhost:9002/purchase/occupied-seats/${scheduleId}`)
            ]);

            const seatsData = await seatsResponse.json();
            const occupiedSeatIds = await occupiedSeatsResponse.json();

            renderSeats(seatsData, occupiedSeatIds);
        } catch (error) {
            console.error('Error al obtener los asientos:', error);
            seatGrid.innerHTML = '<p>Error al cargar los asientos.</p>';
        }
    }

    function renderSeats(seatsData, occupiedSeatIds) {
        seatGrid.innerHTML = ''; // Limpiar la cuadrícula existente
        if (seatsData && seatsData.length > 0) {
            seatsData.forEach(seat => {
                const seatElement = document.createElement('button');
                seatElement.classList.add('seat');
                seatElement.dataset.seatId = seat.id; // Asume que el asiento tiene un 'id'
                seatElement.textContent = seat.number; // Asume que el asiento tiene un 'number' (ej: A1, B2)

                if (occupiedSeatIds.includes(seat.id)) {
                    seatElement.classList.add('occupied');
                } else {
                    seatElement.classList.add('free');
                }
                seatGrid.appendChild(seatElement);
            });
        } else {
            seatGrid.innerHTML = '<p>No hay asientos disponibles para esta función.</p>';
        }
    }
});
