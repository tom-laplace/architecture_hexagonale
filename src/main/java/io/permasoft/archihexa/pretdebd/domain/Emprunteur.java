package io.permasoft.archihexa.pretdebd.domain;

import java.util.List;
import java.util.UUID;

import lombok.NonNull;

public record Emprunteur(@NonNull String name, List<UUID> bdsEmpruntees) {
    public static final Integer MAX_BORROWING_CAPACITY = 3;

    public Emprunteur {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Nom invalide : " + name);
        }
    }

    public boolean peutEmprunter(){
        return bdsEmpruntees.size() < MAX_BORROWING_CAPACITY;
    }

    public void ajouterEmprunt(UUID id) {
        this.bdsEmpruntees.add(id);
    }

    public void enleverEmprunt(UUID id) {
        this.bdsEmpruntees.removeIf(uuid -> uuid.equals(id));
    }
}
