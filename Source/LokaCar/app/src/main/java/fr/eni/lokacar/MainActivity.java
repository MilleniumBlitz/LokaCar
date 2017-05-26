package fr.eni.lokacar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.Agence;
import fr.eni.lokacar.dao.Client;
import fr.eni.lokacar.dao.ClientDao;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.Location;
import fr.eni.lokacar.dao.LocationDao;
import fr.eni.lokacar.dao.Vehicule;
import fr.eni.lokacar.dao.VehiculeDao;

public class MainActivity extends AppCompatActivity {

    private DaoSession daoSession;
    private ClientDao clientDao;
    private VehiculeDao vehiculeDao;
    private LocationDao locationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Commit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        daoSession = ((App) getApplication()).getDaoSession();
        locationDao = daoSession.getLocationDao();
        clientDao = daoSession.getClientDao();
        vehiculeDao = daoSession.getVehiculeDao();
        //vehiculeDao.deleteAll();
        //jeu_donnees_1(true);
    }

    public void jeu_donnees_1(boolean initisaliser) {

        if (initisaliser) {
            clientDao.deleteAll();
            vehiculeDao.deleteAll();
            locationDao.deleteAll();
        }

        List<Client> clients = new ArrayList<Client>();
        List<Vehicule> vehicules = new ArrayList<Vehicule>();
        List<Location> locations = new ArrayList<Location>();

        Client client;
        Vehicule vehicule;
        Location location;

        // CLIENTS
        client = new Client();
        client.setNom("Lafficher");
        client.setPrenom("Xavier");
        client.setTelephone("0643921688");
        clients.add(client);

        client = new Client();
        client.setNom("Poisson");
        client.setPrenom("Quentin");
        client.setTelephone("0658965314");
        clients.add(client);

        client = new Client();
        client.setNom("Malestroit");
        client.setPrenom("Quentin");
        client.setTelephone("0689759632");
        clients.add(client);

        client = new Client();
        client.setNom("Sébastien");
        client.setPrenom("Patrick");
        client.setTelephone("0648579563");
        clients.add(client);

        for (Client c : clients)
            clientDao.insert(c);


        // Véhicules
        vehicule = new Vehicule();
        vehicule.setCNIT("sdfsdf5d4fds65f4");
        vehicule.setCoutParJour(51);
        vehicule.setDisponible(true);
        vehicule.setMarque("peugeot");
        vehicule.setModele("5008");
        vehicules.add(vehicule);

        vehicule = new Vehicule();
        vehicule.setCNIT("qsdcqsdcqsdvq");
        vehicule.setCoutParJour(128);
        vehicule.setDisponible(true);
        vehicule.setMarque("Citroën");
        vehicule.setModele("DS3");
        vehicules.add(vehicule);

        vehicule = new Vehicule();
        vehicule.setCNIT("qsdsqdqsdzazqdfqzfd");
        vehicule.setCoutParJour(520);
        vehicule.setDisponible(false);
        vehicule.setMarque("Ferrari");
        vehicule.setModele("F30");
        vehicules.add(vehicule);

        vehicule = new Vehicule();
        vehicule.setCNIT("65465qs4d65qsd");
        vehicule.setCoutParJour(12);
        vehicule.setDisponible(true);
        vehicule.setMarque("Fiat");
        vehicule.setModele("Panda");
        vehicules.add(vehicule);

        vehicule = new Vehicule();
        vehicule.setCNIT("a35a4ze65a4ezezae");
        vehicule.setCoutParJour(82);
        vehicule.setDisponible(false);
        vehicule.setMarque("Renault");
        vehicule.setModele("19");
        vehicules.add(vehicule);

        for (Vehicule v : vehicules)
            vehiculeDao.insert(v);

        // Locations
        location = new Location();
        location.setClient(clients.get(1));
        location.setVehicule(vehicules.get(2));
        location.setDateContrat(new Date(2017, 05, 01));
        location.setDatePrevue(new Date(2017, 05, 06));
        location.setDateDebut(new Date(2017, 05, 01));
        location.setPrixJour(location.getVehicule().getCoutParJour());
        locations.add(location);

        location = new Location();
        location.setClient(clients.get(3));
        location.setVehicule(vehicules.get(3));
        location.setDateContrat(new Date(2017, 02, 03));
        location.setDatePrevue(new Date(2017, 03, 24));
        location.setDateDebut(new Date(2017, 02, 12));
        location.setDateRendu(new Date(2017, 03, 24));
        location.setPrixJour(location.getVehicule().getCoutParJour());
        locations.add(location);

        location = new Location();
        location.setClient(clients.get(3));
        location.setVehicule(vehicules.get(3));
        location.setDateContrat(new Date(2017, 05, 29));
        location.setDatePrevue(new Date(2017, 06, 15));
        location.setDateDebut(new Date(2017, 05, 30));
        location.setPrixJour(location.getVehicule().getCoutParJour());
        locations.add(location);

        for (Location l : locations)
            locationDao.insert(l);

        location = new Location();
        location.setClient(clients.get(3));
        location.setVehicule(vehicules.get(3));
        location.setDateContrat(new Date(2017, 05, 29));
        location.setDatePrevue(new Date(2017, 06, 15));
        location.setDateDebut(new Date(2017, 05, 30));
        location.setPrixJour(location.getVehicule().getCoutParJour() * 16);
        locations.add(location);
    }

    public void clickGestionParking(View view) {
        try
        {
            Intent intentionGestionParking = new Intent(this,VehiculeListeActivity.class);
            startActivity(intentionGestionParking);
        }
        catch (Exception e)
        {
            Log.e("OnClickGestionParking",e.getMessage());
        }

    }

    public void clickClients(View view) {
        startActivity(new Intent(this, ClientListeActivity.class));
    }

    public void clickAgence(View view) {
        startActivity(new Intent(this, AgenceActivity.class));
    }

    public void clickLocEnCours(View view) {
        Intent intent = new Intent(this, LocationListeActivity.class);
        intent.putExtra(LocationListeActivity.EXTRA_MODE_LOCATIONS, LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_EN_COURS);
        startActivity(intent);
    }

    public void clickLocTerminees(View view) {
        Intent intent = new Intent(this, LocationListeActivity.class);
        intent.putExtra(LocationListeActivity.EXTRA_MODE_LOCATIONS, LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_PASSEES);
        startActivity(intent);
    }

    public void clickLocCreation(View view) {
        Intent intent = new Intent(this, LocationModifierActivity.class);
        startActivity(intent);
    }
}
