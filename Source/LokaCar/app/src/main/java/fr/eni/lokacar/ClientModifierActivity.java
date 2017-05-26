package fr.eni.lokacar;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.Client;
import fr.eni.lokacar.dao.ClientDao;
import fr.eni.lokacar.dao.DaoSession;

public class ClientModifierActivity extends AppCompatActivity {

    public final static String EXTRA_ID_CLIENT = "idClient";
    private long clientId;

    private ClientDao clientDao;
    private Client client;

    @BindView(R.id.et_client_modifier_nom) TextView tvNom;
    @BindView(R.id.et_client_modifier_prenom) TextView tvPrenom;
    @BindView(R.id.et_client_modifier_telephone) TextView tvTelephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_modifier);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        clientId = intent.getLongExtra(EXTRA_ID_CLIENT, -1);

        Log.i("lol", "Clientid : " + clientId  + " pour " + EXTRA_ID_CLIENT);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        clientDao = daoSession.getClientDao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_modifier, menu);
        // Masquer le bouton supprimer si nouveau client
        if (clientId == -1)
            menu.findItem(R.id.menu_supprimer).setVisible(false);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (clientId != -1) {
            client = clientDao.load(clientId);
            if (client != null) {
                tvNom.setText(client.getNom());
                tvPrenom.setText(client.getPrenom());
                tvTelephone.setText(client.getTelephone());
            }
        } else {
            client = new Client();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        View view = findViewById(R.id.view_client_modifier);

        switch (item.getItemId()) {
            case R.id.menu_enregistrer:

                //Modification
                try {
                    client.setNom(tvNom.getText().toString());
                    client.setPrenom(tvPrenom.getText().toString());
                    client.setTelephone(tvTelephone.getText().toString());

                    if ((client.getNom() + client.getPrenom() + client.getTelephone()).trim().isEmpty()){
                        Snackbar.make(view, "Vous devez remplir au moins un champ", Snackbar.LENGTH_LONG).show();
                        return true;
                    }

                    if (clientId != -1) {
                        clientDao.update(client);
                    } else {
                        clientDao.insert(client);
                    }
                } catch (Exception ex){
                    Log.e("lol", "Erreur dans la mise à jour client : " + ex.getMessage());
                    ex.printStackTrace();
                }
                finish();

                return true;

            case R.id.menu_supprimer:

                //Suppression
                Snackbar mySnackbar = Snackbar.make(view,
                        R.string.confirmation_suppression_client, Snackbar.LENGTH_LONG);
                mySnackbar.setAction(R.string.supprimer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clientId != -1) {
                            clientDao.deleteByKey(clientId);
                            finish();
                        }
                    }
                });
                mySnackbar.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
