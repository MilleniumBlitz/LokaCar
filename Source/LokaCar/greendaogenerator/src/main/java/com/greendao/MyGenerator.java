package com.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    static Entity client;
    static Entity vehicule;
    static Entity location;

    public static void main(String[] args) {
        Schema schema = new Schema(1, "fr.eni.lokacar.dao"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addClientEntities(schema);
        addVehiculeEntities(schema);
        addLocationEntities(schema);
    }

    //Ajout de la table Client
    private static Entity addClientEntities(final Schema schema)
    {
        client = schema.addEntity("Client");
        client.addIdProperty().primaryKey().autoincrement();
        client.addStringProperty("Nom").notNull();
        client.addStringProperty("Prenom").notNull();
        client.addStringProperty("Telephone").notNull();
        return client;
    }

    //Ajout  de la table vehicule
    private static Entity addVehiculeEntities(final Schema schema)
    {
        vehicule = schema.addEntity("Vehicule");
        vehicule.addIdProperty().primaryKey().autoincrement();
        vehicule.addStringProperty("CNIT").notNull();
        vehicule.addStringProperty("marque").notNull();
        vehicule.addStringProperty("modele").notNull();
        vehicule.addFloatProperty("coutParJour").notNull();
        vehicule.addBooleanProperty("disponible").notNull();
        return vehicule;
    }

    //Ajout de la table location
    private static Entity addLocationEntities(final Schema schema)
    {
        location = schema.addEntity("Location");
        location.addIdProperty().primaryKey().autoincrement();

        Property clientIdProperty = location.addLongProperty("ClientId").getProperty();
        location.addToOne(client, clientIdProperty);

        Property vehiculeIdProperty = location.addLongProperty("VehiculeId").getProperty();
        location.addToOne(vehicule, vehiculeIdProperty);

        location.addDateProperty("DateContrat").notNull();
        location.addDateProperty("DateDebut").notNull();
        location.addDateProperty("DatePrevue").notNull();
        location.addDateProperty("DateRendu");
        location.addStringProperty("CommentaireAvant");
        location.addStringProperty("CommentaireApres");
        location.addFloatProperty("PrixJour").notNull();
        location.addFloatProperty("CoutSupplementaire");

        return location;
    }
}
