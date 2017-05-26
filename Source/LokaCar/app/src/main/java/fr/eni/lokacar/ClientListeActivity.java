package fr.eni.lokacar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.eni.lokacar.adapter.ClientAdapter;
import fr.eni.lokacar.adapter.LocationAdapter;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.Client;
import fr.eni.lokacar.dao.ClientDao;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.LocationDao;

public class ClientListeActivity extends AppCompatActivity {

    private ClientAdapter adapter;
    private List<Client> clients;
    private ClientDao clientDao;
    private LocationDao locationDao;

    @BindView(R.id.btn_new_client) FloatingActionButton btnNewClient;
    @BindView(R.id.recycler_client) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_liste);
        ButterKnife.bind(this);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        clientDao = daoSession.getClientDao();
        locationDao = daoSession.getLocationDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_liste, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        clients = clientDao.loadAll();

        Log.i("lol", "Il y a " + clients.size() + " clients.");

        adapter = new ClientAdapter(clients, locationDao, new ClientAdapter.OnClickListener() {
            @Override
            public void onClick(Client client) {
                Log.i("lol", "On envoie l'id " + client.getId() + " pour " + ClientModifierActivity.EXTRA_ID_CLIENT);

                Intent intent = new Intent(getApplicationContext(), ClientModifierActivity.class);
                intent.putExtra(ClientModifierActivity.EXTRA_ID_CLIENT, client.getId());
                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void btnMenuRechercher(MenuItem item) {
    }

    @OnClick(R.id.btn_new_client)
    public void addNewClient() {

        Log.d("lol", "Ajout d'un client");
        startActivity(new Intent(getApplicationContext(), ClientModifierActivity.class));

    }
}
