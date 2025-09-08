package io.permasoft.archihexa.pretdebd.application;

import io.permasoft.archihexa.pretdebd.domain.Bd;
import io.permasoft.archihexa.pretdebd.domain.Bds;
import io.permasoft.archihexa.pretdebd.domain.Emprunteur;
import io.permasoft.archihexa.pretdebd.domain.Isbn;
import io.permasoft.archihexa.pretdebd.domain.Proprietaire;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BdServiceTest {
    // each test is run after creating a new instance of the test class, so instance
    // variables are new for each tests.
    Repository repo = new Repository() {
        Bds bds = new Bds(List.of());

        @Override
        public void save(Bd bd) {
            bds = bds.add(bd);
        }

        @Override
        public Optional<Bd> load(UUID id) {
            return bds.byId(id);
        }
    };

    @Test
    void enregitre_une_bd_et_la_consulte() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Quentin");
        Isbn isbn = new Isbn("978-2012101333");
        // WHEN
        BdService bdService = new BdService(repo);
        bdService.enregistrer(id, isbn, proprietaire);
        // THEN
        Bd bd = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));

        assertThat(new Bd(id, isbn, proprietaire, Bd.State.DISPONIBLE))
                .usingRecursiveComparison()
                .isEqualTo(bd);
    }

    @Test
    void emprunte_une_bd() {
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Dwight");
        Isbn isbn = new Isbn("978-2012101333");
        BdService bdService = new BdService(repo);
        bdService.enregistrer(id, isbn, proprietaire);
        Bd bd = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));

        Emprunteur emprunteur = new Emprunteur("Jim", new ArrayList<>());

        try {
            bdService.emprunter(emprunteur, bd.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(bd.getEmprunteur())
                .usingRecursiveComparison()
                .isEqualTo(emprunteur);

        assertThat(bd.getState())
                .isEqualTo(Bd.State.EMPRUNTE);

        assertThat(emprunteur.bdsEmpruntees().get(0))
                .isEqualTo(id);
    }

    @Test
    void doit_refuser_emprunt_si_bd_deja_emprunter() {
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Dwight");
        Isbn isbn = new Isbn("978-2012101333");
        BdService bdService = new BdService(repo);
        bdService.enregistrer(id, isbn, proprietaire);
        Bd bd = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));

        Emprunteur emprunteur = new Emprunteur("Jim", new ArrayList<>());
        Emprunteur emprunteur2 = new Emprunteur("Pam", new ArrayList<>());

        try {
            bdService.emprunter(emprunteur, bd.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(Exception.class, () -> bdService.emprunter(emprunteur2, bd.getId())).getMessage()
                .equals("Bd is already borrowed.");
    }

    @Test
    void doit_refuser_emprunt_si_emprunteur_est_deja_au_max_de_ses_emprunts() {
        UUID id = UUID.randomUUID();
        Proprietaire proprietaire = new Proprietaire("Dwight");
        Isbn isbn = new Isbn("978-2012101333");
        BdService bdService = new BdService(repo);
        bdService.enregistrer(id, isbn, proprietaire);
        Bd bd = bdService.byId(id)
                .orElseThrow(() -> new AssertionError("bd not found"));

        Emprunteur emprunteur = new Emprunteur("Jim", new ArrayList<>());

        for (Integer i = 0; i < Emprunteur.MAX_BORROWING_CAPACITY; i++) {
            emprunteur.ajouterEmprunt(UUID.randomUUID());
        }

        assertThrows(Exception.class, () -> bdService.emprunter(emprunteur, bd.getId())).getMessage()
                .equals("Bd can't be borrowed, borrower has already max out his borrowing capacity.");
    }
}