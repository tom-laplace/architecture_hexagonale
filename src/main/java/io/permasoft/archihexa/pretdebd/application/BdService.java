package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Emprunteur;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;

import java.util.Optional;
import java.util.UUID;

public class BdService {
    private final Repository repo;

    public BdService(Repository repo) {
        this.repo = repo;
    }

    public void enregistrer(UUID id, Isbn isbn, Proprietaire proprietaire) {
        repo.save(new Bd(id, isbn, proprietaire));
    }

    public Optional<Bd> byId(UUID id) {
        return repo.load(id);
    }

    public void emprunter(Emprunteur emprunteur, UUID id) throws Exception {
        try {
            Bd bd = this.byId(id).orElseThrow(() -> new AssertionError("bd not found"));
            bd.emprunter(emprunteur);
            emprunteur.ajouterEmprunt(id);
            repo.save(bd);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void retourner(Emprunteur emprunteur, UUID id) throws Exception {
        try {
            Bd bd = this.byId(id).orElseThrow(() -> new AssertionError("bd not found"));
            bd.retourner(emprunteur);
            emprunteur.enleverEmprunt(id);
            repo.save(bd);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
