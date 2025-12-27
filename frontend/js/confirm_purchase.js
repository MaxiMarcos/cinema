document.addEventListener('DOMContentLoaded', () => {
    const detailsContainer = document.getElementById('details-container');
    const confirmPurchaseBtn = document.getElementById('confirm-purchase-btn');
    const cancelPurchaseBtn = document.getElementById('cancel-purchase-btn');

    const getUrlParameter = (name) => {
        name = name.replace(/[[\]]/g, '\\$&');
        const regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
        const results = regex.exec(window.location.href);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    };

    const purchaseIdsParam = getUrlParameter('purchaseIds');
    let purchaseIds = [];

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
                // Aquí asumimos que purchaseItem tiene un campo 'totalPrice' o similar
                // Si no, necesitarás ajustar cómo se calcula el total
                totalAmount += purchaseItem.totalPrice || 0; // Asumiendo que totalPrice existe
            } catch (error) {
                console.error(`Error fetching purchase item ${id}:`, error);
                detailsContainer.innerHTML = `<p>Error al cargar detalles para la reserva ${id}.</p>`;
                return;
            }
        }

        renderPurchaseDetails(allDetails, totalAmount);
    }

    function renderPurchaseDetails(details, totalAmount) {
        if (details.length === 0) {
            detailsContainer.innerHTML = '<p>No hay detalles de reserva para mostrar.</p>';
            return;
        }

        let html = '<ul>';
        details.forEach(item => {
            html += `<li>Reserva ID: ${item.id} - Asiento: ${item.seatId} - Horario: ${item.scheduleId} - Precio: $${item.totalPrice ? item.totalPrice.toFixed(2) : 'N/A'}</li>`;
        });
        html += `</ul><p><strong>Total a pagar: $${totalAmount.toFixed(2)}</strong></p>`;
        detailsContainer.innerHTML = html;
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
            alert('¡Compra confirmada exitosamente!');
            window.location.href = 'index.html'; // Redirigir a la página principal o de éxito
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
