package fr.eni.lokacar;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.Location;
import fr.eni.lokacar.dao.LocationDao;

public class LocationDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ID_LOCATION = "idLocation";

    @BindView(R.id.tv_location_detail_client) TextView tvLocationDetailClient;
    @BindView(R.id.tv_location_detail_vehicule) TextView tvLocationDetailVehicule;
    @BindView(R.id.tv_location_detail_date_signature) TextView tvLocationDetailDateSignature;
    @BindView(R.id.tv_location_detail_date_debut) TextView tvLocationDetailDateDebut;
    @BindView(R.id.tv_location_detail_date_fin_prevue) TextView tvLocationDetailDateFinPrevue;
    @BindView(R.id.tv_location_detail_commentaire_avant) TextView tvLocationDetailCommentaireAvant;
    @BindView(R.id.tv_location_detail_commentaire_apres) TextView tvLocationDetailCommentaireApres;
    @BindView(R.id.layout_location_commentaire_apres) LinearLayout layoutLocationCommentaireApres;

    Location location;
    LocationDao locationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTitle(getString(R.string.detail_location));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        long locationID = getIntent().getLongExtra(EXTRA_ID_LOCATION, -1);

        if (locationID != -1)
        {
            Log.i("lol", "Détail de la location ID " + locationID);
            DaoSession daoSession = ((App) getApplication()).getDaoSession();
            locationDao = daoSession.getLocationDao();
            location =  locationDao.load(locationID);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.FRENCH);

            tvLocationDetailClient.setText(location.getClient().toString());
            tvLocationDetailVehicule.setText(location.getVehicule().toString());
            tvLocationDetailDateSignature.setText(dateFormat.format(location.getDateContrat()));
            tvLocationDetailDateDebut.setText(dateTimeFormat.format(location.getDateDebut()));
            tvLocationDetailDateFinPrevue.setText(dateTimeFormat.format(location.getDatePrevue()));
            tvLocationDetailCommentaireAvant.setText(location.getCommentaireAvant());
            layoutLocationCommentaireApres.setVisibility(View.GONE);

            if (location.getCommentaireAvant() == null || location.getCommentaireAvant().trim().isEmpty())
            {
                tvLocationDetailCommentaireAvant.setText(R.string.aucun_commentaire);
            }

            if (location.getDateRendu() != null)
            {
                layoutLocationCommentaireApres.setVisibility(View.VISIBLE);
                tvLocationDetailCommentaireApres.setText(location.getCommentaireApres());
            }
        }
    }

    @Override
    public boolean
    onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        if (new Date().after(location.getDateDebut()))
        {
            MenuItem menuItemModifier = menu.findItem(R.id.menu_modifier);
            menuItemModifier.setVisible(false);
            MenuItem menuItemSupprimer = menu.findItem(R.id.menu_supprimer);
            menuItemSupprimer.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_modifier:

                //Modification
                Intent intent = new Intent(getApplicationContext(), LocationModifierActivity.class);
                intent.putExtra(EXTRA_ID_LOCATION, location.getId());
                startActivity(intent);

                return true;

            case R.id.menu_supprimer:

                View view = findViewById(R.id.view_location_detail);

                //Suppression
                Snackbar mySnackbar = Snackbar.make(view,
                        "Etes vous sur de vouloir supprimer cette location ?", Snackbar.LENGTH_SHORT);
                mySnackbar.setAction("Supprimer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationDao.delete(location);
                        finish();
                    }
                });
                mySnackbar.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
