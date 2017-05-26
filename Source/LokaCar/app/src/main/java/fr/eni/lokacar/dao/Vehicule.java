package fr.eni.lokacar.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "VEHICULE".
 */
@Entity
public class Vehicule {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String CNIT;

    @NotNull
    private String marque;

    @NotNull
    private String modele;
    private float coutParJour;
    private boolean disponible;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Vehicule() {
    }

    public Vehicule(Long id) {
        this.id = id;
    }

    @Generated
    public Vehicule(Long id, String CNIT, String marque, String modele, float coutParJour, boolean disponible) {
        this.id = id;
        this.CNIT = CNIT;
        this.marque = marque;
        this.modele = modele;
        this.coutParJour = coutParJour;
        this.disponible = disponible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getCNIT() {
        return CNIT;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCNIT(@NotNull String CNIT) {
        this.CNIT = CNIT;
    }

    @NotNull
    public String getMarque() {
        return marque;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMarque(@NotNull String marque) {
        this.marque = marque;
    }

    @NotNull
    public String getModele() {
        return modele;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setModele(@NotNull String modele) {
        this.modele = modele;
    }

    public float getCoutParJour() {
        return coutParJour;
    }

    public void setCoutParJour(float coutParJour) {
        this.coutParJour = coutParJour;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return getMarque() + " " + getModele();
    }

}
