package com.alekseyruban.barbershopServer.enums;

public enum MasterQualification {
    BARBER ("Барбер сети Brillant"),
    TOPBARBER ("Топ-барбер сети Brillant"),
    BRENDBARBER ("Бренд-барбер сети Brillant");

    private final String qualification;

    private MasterQualification(String qualification) {
        this.qualification = qualification;
    }

    public String toString() {
        return this.qualification;
    }
}
