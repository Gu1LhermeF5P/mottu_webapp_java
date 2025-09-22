document.addEventListener('DOMContentLoaded', () => {
    const gridContainer = document.getElementById('yard-grid-container');
    const loadingSpinner = document.getElementById('loading-spinner');
    if (!gridContainer) return;

    const yardId = gridContainer.dataset.yardId;
    const gridWidth = parseInt(gridContainer.dataset.gridWidth, 10);
    const gridHeight = parseInt(gridContainer.dataset.gridHeight, 10);

    function createGrid() {
        gridContainer.innerHTML = '';
        gridContainer.style.gridTemplateColumns = `repeat(${gridWidth}, 1fr)`;
        gridContainer.style.gridTemplateRows = `repeat(${gridHeight}, 1fr)`;

        for (let y = 0; y < gridHeight; y++) {
            for (let x = 0; x < gridWidth; x++) {
                const cell = document.createElement('div');
                cell.classList.add('grid-cell');
                cell.id = `cell-${x}-${y}`;
                gridContainer.appendChild(cell);
            }
        }
    }

    async function updatePositions() {
        loadingSpinner.classList.remove('hidden');
        try {
            const response = await fetch(`/api/dashboard/${yardId}/positions`);
            if (!response.ok) throw new Error('Falha ao buscar dados');
            const positions = await response.json();

            document.querySelectorAll('.motorcycle-icon-wrapper').forEach(wrapper => wrapper.remove());

            positions.forEach(pos => {
                const cell = document.getElementById(`cell-${pos.posX}-${pos.posY}`);
                if (cell) {
                    const wrapper = document.createElement('div');
                    wrapper.className = 'tooltip motorcycle-icon-wrapper';
                    wrapper.dataset.tip = `Placa: ${pos.licensePlate} | Status: ${pos.status}`;

                    const icon = document.createElement('div');
                    icon.className = `motorcycle-icon status-${pos.status}`;
                    // Adicionando um ícone simples de moto (SVG)
                    icon.innerHTML = `<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor"><path d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z" /></svg>`;

                    wrapper.appendChild(icon);
                    cell.appendChild(wrapper);
                }
            });
        } catch (error) {
            console.error('Erro ao atualizar posições:', error);
        } finally {
            loadingSpinner.classList.add('hidden');
        }
    }

    createGrid();
    updatePositions();
    setInterval(updatePositions, 10000); // Atualiza a cada 10 segundos
});