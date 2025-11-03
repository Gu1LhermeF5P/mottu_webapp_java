package br.com.mottu.mottu_challenge.model;

public enum MotorcycleStatus {
    AVAILABLE("Disponível"),
    IN_MAINTENANCE("Em Manutenção"),
    RENTED("Alugada"),
    BLOCKED("Com B.O.");

    private final String displayName;

    MotorcycleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}