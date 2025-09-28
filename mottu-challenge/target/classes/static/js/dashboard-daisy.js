document.addEventListener('DOMContentLoaded', () => {
    const yardContainer = document.getElementById('yard-container');
    if (!yardContainer) return;

    const yardId = yardContainer.dataset.yardId;
    const zoneAvailable = document.getElementById('zone-available');
    const zoneMaintenance = document.getElementById('zone-maintenance');
    const zoneBlocked = document.getElementById('zone-blocked');

    async function updatePositions() {
        try {
            const response = await fetch(`/api/dashboard/${yardId}/positions`);
            const positions = await response.json();

            // Limpa todos os ícones de moto existentes de todas as zonas
            document.querySelectorAll('.motorcycle-icon').forEach(icon => icon.remove());

            positions.forEach(pos => {
                let targetZone;
                // Escolhe a zona correta baseada no status da moto
                switch(pos.status) {
                    case 'IN_MAINTENANCE':
                        targetZone = zoneMaintenance;
                        break;
                    case 'BLOCKED':
                        targetZone = zoneBlocked;
                        break;
                    default: // AVAILABLE, RENTED, etc
                        targetZone = zoneAvailable;
                }
                
                // Cria o ícone da moto
                const icon = document.createElement('div');
                icon.className = `tooltip motorcycle-icon status-${pos.status}`;
                icon.dataset.tip = `Placa: ${pos.licensePlate}`;
                
                // POSICIONA o ícone dentro da zona usando porcentagens
                // Os valores de posX e posY (0-100) do backend são usados como left e top
                icon.style.left = `${pos.posX}%`;
                icon.style.top = `${pos.posY}%`;

                targetZone.appendChild(icon);
            });

        } catch (error) {
            console.error('Erro ao atualizar posições:', error);
        }
    }

    updatePositions();
    setInterval(updatePositions, 10000); // Atualiza a cada 10 segundos
});