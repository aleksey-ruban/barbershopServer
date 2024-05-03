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

    public static MasterQualification fromString(String text) {
        for (MasterQualification b : MasterQualification.values()) {
            if (b.qualification.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
