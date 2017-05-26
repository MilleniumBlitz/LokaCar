package fr.eni.lokacar.dao;

/**
 * Created by Administrateur on 24/05/2017.
 */

public class Agence {
    public static final String PREFERENCE_NOM_AGENCE = "nomAgence";

    private String nomAgence;

    public Agence() {}

    public String getNomAgence() {
        return nomAgence;
    }

    public void setNomAgence(String nomAgence) {
        this.nomAgence = nomAgence;
    }
}
