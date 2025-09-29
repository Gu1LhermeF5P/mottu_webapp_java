document.addEventListener('DOMContentLoaded', () => {
    const yardContainer = document.getElementById('yard-container');
    if (!yardContainer) return; // Se não estiver na página do dashboard, não faz nada

    // Mapeia os IDs das zonas do HTML
    const yardId = yardContainer.dataset.yardId;
    const zoneAvailable = document.getElementById('zone-available');
    const zoneMaintenance = document.getElementById('zone-maintenance');
    const zoneBlocked = document.getElementById('zone-blocked');
    const statusText = document.getElementById('status-text');

    // Função principal que busca e desenha as motos
    async function updatePositions() {
        if (statusText) statusText.textContent = 'Atualizando...';
        
        try {
            // 1. Busca os dados mais recentes das motos na nossa API
            const response = await fetch(`/api/dashboard/${yardId}/positions`);
            const positions = await response.json();

            // 2. Limpa todos os ícones de moto antigos de todas as zonas
            document.querySelectorAll('.motorcycle-icon').forEach(icon => icon.remove());

            // 3. Itera sobre cada moto recebida
            positions.forEach(pos => {
                let targetZone;
                
                // 4. Decide em qual zona a moto deve ser colocada com base no seu status
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
                    // 5. Cria o elemento HTML (o ícone) para a moto
                    const icon = document.createElement('div');
                    icon.className = `tooltip motorcycle-icon status-${pos.status}`;
                    icon.dataset.tip = `Placa: ${pos.licensePlate}`;
                    
                    // 6. POSICIONA o ícone dentro da zona usando porcentagens
                    // Os valores de posX e posY (0-100) do backend são usados como left e top
                    icon.style.left = `calc(${pos.posX}% - 12.5px)`; // Subtrai metade do tamanho do ícone para centralizar
                    icon.style.top = `calc(${pos.posY}% - 12.5px)`;

                    // 7. Adiciona o ícone à zona correta
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

    // Roda a função pela primeira vez e depois a cada 10 segundos
    updatePositions();
    setInterval(updatePositions, 10000);
});