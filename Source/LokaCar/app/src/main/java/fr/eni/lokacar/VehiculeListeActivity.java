package fr.eni.lokacar;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.eni.lokacar.adapter.VehiculeAdapter;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.Vehicule;
import fr.eni.lokacar.dao.VehiculeDao;

public class VehiculeListeActivity extends AppCompatActivity{

    private VehiculeAdapter adapter;
    private List<Vehicule> vehicules;
    public static final String EXTRA_ID_VEHICULE = "IdVehicule";

    @BindView(R.id.btn_new_vehicule) FloatingActionButton btnNewVehicule;
    @BindView(R.id.recycler_vehicule) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicule_liste);
        ButterKnife.bind(this);
        setTitle("Gestion du parking");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        VehiculeDao vehiculeDao = daoSession.getVehiculeDao();
        vehicules = new ArrayList<>();
        vehicules = vehiculeDao.loadAll();

        adapter = new VehiculeAdapter(vehicules, new VehiculeAdapter.OnClickListener() {
            @Override
            public void onClick(Vehicule vehicule) {
                Intent intentionGestionParking = new Intent(getApplicationContext(),VehiculeModifierActivity.class);
                intentionGestionParking.putExtra(EXTRA_ID_VEHICULE,vehicule.getId());
                startActivity(intentionGestionParking);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public enum MODE_VEHICULE {LISTE_VEH_INDISPONIBLES, LISTE_VEH_TOUS, LISTE_VEH_DISPONIBLES}

    @OnClick(R.id.btn_new_vehicule)
    public void addNewVehicule() {

        Log.d("lol", "Ajout d'un vehicule");
        Intent intentionGestionParking = new Intent(getApplicationContext(),VehiculeModifierActivity.class);
        startActivity(intentionGestionParking);

    }


}
