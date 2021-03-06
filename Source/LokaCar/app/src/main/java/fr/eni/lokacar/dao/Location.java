package fr.eni.lokacar.dao;

import org.greenrobot.greendao.annotation.*;

import fr.eni.lokacar.dao.DaoSession;
import org.greenrobot.greendao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "LOCATION".
 */
@Entity(active = true)
public class Location {

    @Id(autoincrement = true)
    private Long id;
    private Long ClientId;
    private Long VehiculeId;

    @NotNull
    private java.util.Date DateContrat;

    @NotNull
    private java.util.Date DateDebut;

    @NotNull
    private java.util.Date DatePrevue;
    private java.util.Date DateRendu;
    private String CommentaireAvant;
    private String CommentaireApres;
    private float PrixJour;
    private Float CoutSupplementaire;

    /** Used to resolve relations */
    @Generated
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated
    private transient LocationDao myDao;

    @ToOne(joinProperty = "ClientId")
    private Client client;

    @Generated
    private transient Long client__resolvedKey;

    @ToOne(joinProperty = "VehiculeId")
    private Vehicule vehicule;

    @Generated
    private transient Long vehicule__resolvedKey;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Location() {
    }

    public Location(Long id) {
        this.id = id;
    }

    @Generated
    public Location(Long id, Long ClientId, Long VehiculeId, java.util.Date DateContrat, java.util.Date DateDebut, java.util.Date DatePrevue, java.util.Date DateRendu, String CommentaireAvant, String CommentaireApres, float PrixJour, Float CoutSupplementaire) {
        this.id = id;
        this.ClientId = ClientId;
        this.VehiculeId = VehiculeId;
        this.DateContrat = DateContrat;
        this.DateDebut = DateDebut;
        this.DatePrevue = DatePrevue;
        this.DateRendu = DateRendu;
        this.CommentaireAvant = CommentaireAvant;
        this.CommentaireApres = CommentaireApres;
        this.PrixJour = PrixJour;
        this.CoutSupplementaire = CoutSupplementaire;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLocationDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return ClientId;
    }

    public void setClientId(Long ClientId) {
        this.ClientId = ClientId;
    }

    public Long getVehiculeId() {
        return VehiculeId;
    }

    public void setVehiculeId(Long VehiculeId) {
        this.VehiculeId = VehiculeId;
    }

    @NotNull
    public java.util.Date getDateContrat() {
        return DateContrat;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDateContrat(@NotNull java.util.Date DateContrat) {
        this.DateContrat = DateContrat;
    }

    @NotNull
    public java.util.Date getDateDebut() {
        return DateDebut;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDateDebut(@NotNull java.util.Date DateDebut) {
        this.DateDebut = DateDebut;
    }

    @NotNull
    public java.util.Date getDatePrevue() {
        return DatePrevue;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDatePrevue(@NotNull java.util.Date DatePrevue) {
        this.DatePrevue = DatePrevue;
    }

    public java.util.Date getDateRendu() {
        return DateRendu;
    }

    public void setDateRendu(java.util.Date DateRendu) {
        this.DateRendu = DateRendu;
    }

    public String getCommentaireAvant() {
        return CommentaireAvant;
    }

    public void setCommentaireAvant(String CommentaireAvant) {
        this.CommentaireAvant = CommentaireAvant;
    }

    public String getCommentaireApres() {
        return CommentaireApres;
    }

    public void setCommentaireApres(String CommentaireApres) {
        this.CommentaireApres = CommentaireApres;
    }

    public float getPrixJour() {
        return PrixJour;
    }

    public void setPrixJour(float PrixJour) {
        this.PrixJour = PrixJour;
    }

    public Float getCoutSupplementaire() {
        if (CoutSupplementaire == null)
            CoutSupplementaire = 0f;
        return CoutSupplementaire;
    }

    public void setCoutSupplementaire(Float CoutSupplementaire) {
        this.CoutSupplementaire = CoutSupplementaire;
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Client getClient() {
        Long __key = this.ClientId;
        if (client__resolvedKey == null || !client__resolvedKey.equals(__key)) {
            __throwIfDetached();
            ClientDao targetDao = daoSession.getClientDao();
            Client clientNew = targetDao.load(__key);
            synchronized (this) {
                client = clientNew;
            	client__resolvedKey = __key;
            }
        }
        return client;
    }

    @Generated
    public void setClient(Client client) {
        synchronized (this) {
            this.client = client;
            ClientId = client == null ? null : client.getId();
            client__resolvedKey = ClientId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated
    public Vehicule getVehicule() {
        Long __key = this.VehiculeId;
        if (vehicule__resolvedKey == null || !vehicule__resolvedKey.equals(__key)) {
            __throwIfDetached();
            VehiculeDao targetDao = daoSession.getVehiculeDao();
            Vehicule vehiculeNew = targetDao.load(__key);
            synchronized (this) {
                vehicule = vehiculeNew;
            	vehicule__resolvedKey = __key;
            }
        }
        return vehicule;
    }

    @Generated
    public void setVehicule(Vehicule vehicule) {
        synchronized (this) {
            this.vehicule = vehicule;
            VehiculeId = vehicule == null ? null : vehicule.getId();
            vehicule__resolvedKey = VehiculeId;
        }
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void delete() {
        __throwIfDetached();
        myDao.delete(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void update() {
        __throwIfDetached();
        myDao.update(this);
    }

    /**
    * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
    * Entity must attached to an entity context.
    */
    @Generated
    public void refresh() {
        __throwIfDetached();
        myDao.refresh(this);
    }

    @Generated
    private void __throwIfDetached() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
