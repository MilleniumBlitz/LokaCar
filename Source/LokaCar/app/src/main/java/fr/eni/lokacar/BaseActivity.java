package fr.eni.lokacar;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Activity Title");
    }

    public void onClickMnuLocationEnCours(MenuItem item) {

        Intent intent = new Intent(this, LocationListeActivity.class);
        startActivity(intent);
    }

    public void onClickMnuHistorique(MenuItem item) {

        Intent intent = new Intent(this, LocationListeActivity.class);
        intent.putExtra(LocationListeActivity.EXTRA_MODE_LOCATIONS, LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_EN_COURS);
        startActivity(intent);
    }

    public void onClickMnuListeClients(MenuItem item) {

        //Affichage de la la liste des clients
    }

    public void onClickMnuAgence(MenuItem item) {

        //Affichage de l'agence
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
}
