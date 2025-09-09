package io.permasoft.archihexa.pretdebd.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@ToString
@Getter
public class Bd  {
    private final UUID id;
    private final Isbn isbn;
    private final Proprietaire proprietaire;
    private Emprunteur emprunteur;
    private State state;

    public enum State { DISPONIBLE, EMPRUNTE;}

    public Bd(@NonNull UUID id, @NonNull Isbn isbn, @NonNull Proprietaire proprietaire) {
        this(id, isbn, proprietaire, State.DISPONIBLE);
    }

    /**
     * Canonical constructor to create a Bd with a given state.
     * When loading from a DB.
     */
    //@org.springframework.data.annotation.PersistenceCreator <= makes spring data use this constructor
    public Bd(@NonNull UUID id, @NonNull Isbn isbn, @NonNull Proprietaire proprietaire, @NonNull State disponible) {
        this.id = id;
        this.isbn = isbn;
        this.proprietaire = proprietaire;
        this.state = disponible;
    }

    public void emprunter(Emprunteur emprunteur) throws Exception{
        if(this.state != State.DISPONIBLE) {
            throw new Exception("Bd is already borrowed.");
        }

        if(!emprunteur.peutEmprunter()) {
            throw new Exception("Bd can't be borrowed, borrower has already max out his borrowing capacity.");
        }

        this.emprunteur = emprunteur;
        this.state = State.EMPRUNTE;
    }

    public void retourner(Emprunteur emprunteur) throws Exception {
        if(this.state != State.EMPRUNTE) {
            throw new Exception("Bd is not borrowed.");
        }

        if(!emprunteur.equals(this.getEmprunteur())) {
            throw new Exception("Bd hasn't been borrowed by the borrower passed in params.");
        }

        this.emprunteur = null;
        this.state = State.DISPONIBLE;
    }
}