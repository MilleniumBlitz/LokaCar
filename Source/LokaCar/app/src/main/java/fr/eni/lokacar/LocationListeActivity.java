package fr.eni.lokacar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.eni.lokacar.adapter.LocationAdapter;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.Location;
import fr.eni.lokacar.dao.LocationDao;

public class LocationListeActivity extends AppCompatActivity implements LocationAdapter.OnClickListener {

    public static final String EXTRA_MODE_LOCATIONS = "modeLocations";

    @BindView(R.id.btn_new_location) FloatingActionButton btnNewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_liste);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MODE_LOCATIONS modeLocations = (MODE_LOCATIONS) getIntent().getSerializableExtra(EXTRA_MODE_LOCATIONS);
        Log.d("lol","Liste locations mode " + modeLocations);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        LocationDao locationDao = daoSession.getLocationDao();

        LocationAdapter adapter = null;

        switch (modeLocations)
        {
            case LISTE_LOC_EN_COURS:

                adapter = new LocationAdapter(locationDao.loadLocations(MODE_LOCATIONS.LISTE_LOC_EN_COURS), this);
                setTitle("Locations en cours");

                break;
            case LISTE_LOC_PASSEES:

                adapter = new LocationAdapter(locationDao.loadLocations(MODE_LOCATIONS.LISTE_LOC_PASSEES), this);
                setTitle("Historique des locations");
                btnNewLocation.setVisibility(View.GONE);

                break;
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_location);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean
    onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_liste, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_rechercher:

                Log.d("lol", "Recherche d'une location");

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(Location location) {
        Intent intent = new Intent(this, LocationDetailActivity.class);
        intent.putExtra(LocationDetailActivity.EXTRA_ID_LOCATION, location.getId());
        startActivity(intent);
    }

    @OnClick(R.id.btn_new_location)
    public void addNewLocation() {

        Log.d("lol", "Ajout d'une location");
        Intent intent = new Intent(getApplicationContext(), LocationModifierActivity.class);
        startActivity(intent);

    }

    public enum MODE_LOCATIONS {
        LISTE_LOC_EN_COURS,
        LISTE_LOC_PASSEES,
        LISTE_LOC_DEPASSEES, LISTE_LOC_TOUS
    }
}
