document.addEventListener('DOMContentLoaded', () => {
    const yardContainer = document.getElementById('yard-container');
    if (!yardContainer) return;

    const yardId = yardContainer.dataset.yardId;
    const zoneAvailable = document.getElementById('zone-available');
    const zoneMaintenance = document.getElementById('zone-maintenance');
    const zoneBlocked = document.getElementById('zone-blocked');
    const statusText = document.getElementById('status-text');

    async function updatePositions() {
        if (statusText) statusText.textContent = 'Atualizando...';
        
        try {
            const response = await fetch(`/api/dashboard/${yardId}/positions`);
            const positions = await response.json();

            // Limpa todos os ícones de moto antigos de todas as zonas
            document.querySelectorAll('.motorcycle-icon').forEach(icon => icon.remove());

            positions.forEach(pos => {
                let targetZone;
                
                // Decide em qual zona a moto deve ser colocada com base no seu status
                switch(pos.status) {
                    case 'IN_MAINTENANCE':
                        targetZone = zoneMaintenance;
                        break;
                    case 'BLOCKED':
                        targetZone = zoneBlocked;
                        break;
                    default: // AVAILABLE, RENTED, etc.
                        targetZone = zoneAvailable;
                }
                
                if (targetZone) {
                    // Cria o elemento HTML (o ícone) para a moto
                    const icon = document.createElement('div');
                    icon.className = `tooltip motorcycle-icon status-${pos.status}`;
                    icon.dataset.tip = `Placa: ${pos.licensePlate}`;
                    
                    // Posiciona o ícone dentro da zona usando porcentagens
                    icon.style.left = `calc(${pos.posX}% - 12.5px)`;
                    icon.style.top = `calc(${pos.posY}% - 12.5px)`;

                    targetZone.appendChild(icon);
                }
            });
            
            if (statusText) {
                const now = new Date();
                statusText.textContent = `Atualizado às ${now.getHours()}:${String(now.getMinutes()).padStart(2, '0')}`;
            }

        } catch (error) {
            console.error('Erro ao atualizar posições:', error);
            if (statusText) statusText.textContent = 'Erro de conexão.';
        }
    }

    updatePositions();
    setInterval(updatePositions, 10000);
});