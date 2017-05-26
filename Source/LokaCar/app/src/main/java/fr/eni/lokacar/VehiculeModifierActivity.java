package fr.eni.lokacar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.Vehicule;
import fr.eni.lokacar.dao.VehiculeDao;
import fr.eni.lokacar.webService.Servlet;
import fr.eni.lokacar.webService.WebService;

public class VehiculeModifierActivity extends AppCompatActivity {

    @BindView(R.id.sp_vehicule_modifier_marque) Spinner sp_vehicule_modifier_marque;
    @BindView(R.id.sp_vehicule_modifier_modele) Spinner sp_vehicule_modifier_modele;
    @BindView(R.id.et_vehicule_modifier_coutparjour) EditText et_vehicule_modifier_coutparjour;
    @BindView(R.id.cb_vehicule_modifier_disponible) CheckBox cb_vehicule_modifier_disponible;
    private long idVehicule;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicule_modifier);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        idVehicule = getIntent().getLongExtra(VehiculeListeActivity.EXTRA_ID_VEHICULE, -1);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        final VehiculeDao vehiculeDao = daoSession.getVehiculeDao();
        try {
            WebService.executeQuery(Servlet.MARQUE, new WebService.WebServiceCallback() {
                @Override
                public void onResult(Object json) {
                    try {
                        ArrayList<String> listeMarque;
                        listeMarque = getList(((JSONObject) json).getJSONArray("Marque"));
                        ArrayAdapter<String> dataMarqueAdapter = new ArrayAdapter<String>(VehiculeModifierActivity.this, android.R.layout.simple_spinner_dropdown_item,
                                listeMarque);
                        sp_vehicule_modifier_marque.setAdapter(dataMarqueAdapter);
                        if (idVehicule != -1) {
                            Vehicule vehicule = vehiculeDao.load(idVehicule);
                            int index = listeMarque.indexOf(vehicule.getMarque());
                            if (index != -1) sp_vehicule_modifier_marque.setSelection(index);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        sp_vehicule_modifier_marque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    WebService.executeQuery(Servlet.MODELE, new WebService.WebServiceCallback() {
                        @Override
                        public void onResult(Object json) {
                            ArrayList<String> listeModele;
                            listeModele = getListModele((JSONArray) json);
                            ArrayAdapter<String> dataMarqueAdapter = new ArrayAdapter<String>(VehiculeModifierActivity.this, android.R.layout.simple_spinner_item,
                                    listeModele);
                            sp_vehicule_modifier_modele.setAdapter(dataMarqueAdapter);
                            if (idVehicule != -1) {
                                Vehicule vehicule = vehiculeDao.load(idVehicule);
                                int index = listeModele.indexOf(vehicule.getModele());
                                if (index != -1) sp_vehicule_modifier_modele.setSelection(index);
                            }
                        }
                    }, sp_vehicule_modifier_marque.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (idVehicule != -1) {
            Vehicule vehicule = vehiculeDao.load(idVehicule);
            et_vehicule_modifier_coutparjour.setText(String.valueOf(vehicule.getCoutParJour()));
            cb_vehicule_modifier_disponible.setChecked(vehicule.getDisponible());
        }

    }

    private ArrayList<String> getList(JSONArray data) {
        ArrayList<String> listeData = new ArrayList<>();
        try {
            for (int i = 0; i < data.length(); i++) {
                listeData.add(data.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listeData;
    }

    private ArrayList<String> getListModele(JSONArray data) {
        ArrayList<String> listeData = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = null;
            try {
                object = data.getJSONObject(i);
                listeData.add(object.getString("Designation"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listeData;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modifier, menu);
        if (idVehicule == -1)
            menu.findItem(R.id.menu_supprimer).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        VehiculeDao vehiculeDao = daoSession.getVehiculeDao();
        switch (item.getItemId()) {
            case R.id.menu_enregistrer:
                Vehicule vehicule = new Vehicule();
                vehicule.setMarque(sp_vehicule_modifier_marque.getSelectedItem().toString());
                vehicule.setModele(sp_vehicule_modifier_modele.getSelectedItem().toString());
                vehicule.setDisponible(cb_vehicule_modifier_disponible.isChecked());
                vehicule.setCoutParJour(Float.parseFloat(String.valueOf(et_vehicule_modifier_coutparjour.getText())));
                vehicule.setCNIT(""); // TODO: 24/05/2017
                if (idVehicule == -1) {
                    vehiculeDao.insert(vehicule);

                } else {
                    Vehicule vehiculeLoad = vehiculeDao.load(idVehicule);
                    vehiculeLoad.setMarque(sp_vehicule_modifier_marque.getSelectedItem().toString());
                    vehiculeLoad.setModele(sp_vehicule_modifier_modele.getSelectedItem().toString());
                    vehiculeLoad.setDisponible(cb_vehicule_modifier_disponible.isChecked());
                    vehiculeLoad.setCoutParJour(Float.parseFloat(String.valueOf(et_vehicule_modifier_coutparjour.getText())));
                    vehiculeLoad.setCNIT("");
                    vehiculeDao.update(vehiculeLoad);
                }
                finish();
                return true;
            case R.id.menu_supprimer:
                vehiculeDao.deleteByKey(idVehicule);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
