package fr.eni.lokacar;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.Agence;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.LocationDao;
import fr.eni.lokacar.dao.VehiculeDao;

public class AgenceActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DaoSession daoSession;
    LocationDao locationDao;
    VehiculeDao vehiculeDao;

    @BindView(R.id.et_agence_nom_agence) TextView tvNomAgence;
    @BindView(R.id.tv_agence_chiffre_affaire) TextView tvChiffreAffaire;
    @BindView(R.id.tv_agence_nb_locations_total) TextView tvLocTotal;
    @BindView(R.id.tv_agence_nb_locations_en_cours) TextView tvLocEnCours;
    @BindView(R.id.tv_agence_nb_locations_terminees) TextView tvLocTerminees;
    @BindView(R.id.tv_agence_nb_locations_depassees) TextView tvLocDepassees;
    @BindView(R.id.tv_agence_nb_vehicules_disponibles) TextView tvVehiculesDisponibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agence);

        ButterKnife.bind(this);

        daoSession = ((App) getApplication()).getDaoSession();
        locationDao = daoSession.getLocationDao();
        vehiculeDao = daoSession.getVehiculeDao();
        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), MODE_PRIVATE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_modifier, menu);
        menu.findItem(R.id.menu_supprimer).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enregistrer:
                enregistrer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tvNomAgence.setText(
                sharedPreferences.getString(Agence.PREFERENCE_NOM_AGENCE, null)
        );

        tvChiffreAffaire.setText(String.valueOf(
                locationDao.getChiffreAffaireTotal()
        ) + " €");

        tvLocTotal.setText(String.valueOf(
            locationDao.loadLocations(LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_TOUS).size()
        ));

        tvLocEnCours.setText(String.valueOf(
            locationDao.loadLocations(LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_EN_COURS).size()
        ));

        tvLocTerminees.setText(String.valueOf(
            locationDao.loadLocations(LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_PASSEES).size()
        ));

        tvLocDepassees.setText(String.valueOf(
            locationDao.loadLocations(LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_DEPASSEES).size()
        ));

        tvVehiculesDisponibles.setText(String.valueOf(
            vehiculeDao.loadVehicules(VehiculeListeActivity.MODE_VEHICULE.LISTE_VEH_DISPONIBLES).size()
        ) + " / " +
            vehiculeDao.loadVehicules(VehiculeListeActivity.MODE_VEHICULE.LISTE_VEH_TOUS).size()
        );
    }

    private void enregistrer(){
        String nomAgence = tvNomAgence.getText().toString().trim();
        if (nomAgence.isEmpty())
            Toast.makeText(this, "Vous devez choisir un nom d'agence.", Toast.LENGTH_SHORT).show();
        else {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString(Agence.PREFERENCE_NOM_AGENCE, nomAgence);
            e.commit();
            Toast.makeText(this, "Nom de l'agence enregistré", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
