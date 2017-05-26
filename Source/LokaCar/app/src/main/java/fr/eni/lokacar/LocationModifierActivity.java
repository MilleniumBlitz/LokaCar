package fr.eni.lokacar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.Client;
import fr.eni.lokacar.dao.ClientDao;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.Location;
import fr.eni.lokacar.dao.LocationDao;
import fr.eni.lokacar.dao.Vehicule;
import fr.eni.lokacar.dao.VehiculeDao;

import static fr.eni.lokacar.LocationDetailActivity.EXTRA_ID_LOCATION;

public class LocationModifierActivity extends AppCompatActivity {

    @BindView(R.id.tv_location_modifier_clients) AutoCompleteTextView tvClients;
    @BindView(R.id.tv_location_modifier_vehicules) AutoCompleteTextView tvVehicules;
    @BindView(R.id.et_location_modifier_date_signature) EditText etDateSignature;
    @BindView(R.id.et_location_modifier_date_debut) EditText etDateDebut;
    @BindView(R.id.et_location_modifier_time_debut) EditText etTimeDebut;
    @BindView(R.id.et_location_modifier_date_fin_prevue) EditText etDateFinPrevue;
    @BindView(R.id.et_location_modifier_time_fin) EditText etTimeFin;
    @BindView(R.id.et_location_modifier_commentaire_avant) EditText etCommentaireAvant;
    @BindView(R.id.tv_location_modifier_prix) TextView tvPrix;

    SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE d MMM yyyy", Locale.FRENCH);
    SimpleDateFormat sdfTime = new SimpleDateFormat("kk'h'mm", Locale.FRENCH);

    DaoSession daoSession;
    LocationDao locationDao;

    Location location;
    Long locationID;
    Client clientSelectionne;
    Vehicule vehiculeSelectionne;

    Date dateDebut;
    Date dateFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_modifier);
        ButterKnife.bind(this);

        daoSession = ((App)getApplication()).getDaoSession();
        locationDao =  daoSession.getLocationDao();

        Intent intent = getIntent();
        locationID =  intent.getLongExtra(EXTRA_ID_LOCATION, -1);
        if (locationID == -1)
        {
            //Création d'une location
            Log.i("lol", getString(R.string.creation_location));
            setTitle(getString(R.string.creation_location));

            location = new Location();

            Date date = new Date();
            etDateSignature.setText(sdfDate.format(date));
            etDateDebut.setText(sdfDate.format(date));
            etTimeDebut.setText(sdfTime.format(date));
            etDateFinPrevue.setText(sdfDate.format(date));
            etTimeFin.setText(sdfTime.format(date));
            tvPrix.setText("Veuillez séléctionner un véhicule");
        }
        else
        {
            //Modification d'une location
            Log.i("lol", getString(R.string.modification_location) + ", ID : " + locationID);
            setTitle(getString(R.string.modification_location));
            location = locationDao.load(locationID);
            clientSelectionne = location.getClient();
            tvClients.setText(location.getClient().toString());
            vehiculeSelectionne = location.getVehicule();
            tvVehicules.setText(location.getVehicule().toString());
            etDateSignature.setText(sdfDate.format(location.getDateContrat()));
            etDateDebut.setText(sdfDate.format(location.getDateDebut()));
            etTimeDebut.setText(sdfTime.format(location.getDateDebut()));
            etDateFinPrevue.setText(sdfDate.format(location.getDatePrevue()));
            etTimeFin.setText(sdfTime.format(location.getDatePrevue()));
            etCommentaireAvant.setText(location.getCommentaireAvant());
            majPrix();

        }

        //Intialisation des textView
        initTextViewClients();
        initTextViewVehicules();

        //Initialisation des dates pickers
        initDatePicker(etDateSignature);
        initDatePicker(etDateDebut);
        initDatePicker(etDateFinPrevue);

        initTimePicker(etTimeDebut);
        initTimePicker(etTimeFin);
    }

    //Chargements des clients
    private void initTextViewClients() {

        ClientDao clientDao =  daoSession.getClientDao();
        ArrayAdapter<Client> adapterClients = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, clientDao.loadAll());
        tvClients.setAdapter(adapterClients);
        tvClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clientSelectionne = (Client)parent.getItemAtPosition(position);
            }
        });
    }

    //Chargements des vehicules
    private void initTextViewVehicules() {

        VehiculeDao vehiculeDao =  daoSession.getVehiculeDao();
        ArrayAdapter<Vehicule> adapterVehicules = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehiculeDao.loadVehicules(VehiculeListeActivity.MODE_VEHICULE.LISTE_VEH_DISPONIBLES));
        tvVehicules.setAdapter(adapterVehicules);
        tvVehicules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vehiculeSelectionne = (Vehicule)parent.getItemAtPosition(position);
                majPrix();
            }
        });
    }

    @Override
    public boolean
    onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_modifier, menu);

        if (locationID == -1)
        {
            MenuItem menuItem = menu.findItem(R.id.menu_supprimer);
            menuItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enregistrer:

                View view = findViewById(R.id.view_location_modifier);

                if (clientSelectionne == null)
                {
                    Snackbar.make(view, "Veuillez séléctionner un client", Snackbar.LENGTH_LONG).show();
                    return true;
                }

                if (vehiculeSelectionne == null)
                {
                    Snackbar.make(view, "Veuillez séléctionner un véhicule", Snackbar.LENGTH_LONG).show();
                    return true;
                }

                try {
                    Date dateContrat = sdfDate.parse(etDateSignature.getText().toString());
                    Date dateDebut = sdfDate.parse(etDateDebut.getText().toString());
                    Date dateFin = sdfDate.parse(etDateFinPrevue.getText().toString());

                    if (dateContrat.after(dateDebut))
                    {
                        Snackbar.make(view, "La date de début ne peut pas être inférieur à la date de signature", Snackbar.LENGTH_LONG).show();
                        return true;
                    }

                    if (dateDebut.after(dateFin))
                    {
                        Snackbar.make(view, "La date de début ne peut pas être supérieur à la date de fin", Snackbar.LENGTH_LONG).show();
                        return true;
                    }

                    location.setDateContrat(dateContrat);
                    location.setDateDebut(dateDebut);
                    location.setDatePrevue(dateFin);
                    location.setCommentaireAvant(etCommentaireAvant.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                location.setClient(clientSelectionne);
                location.setVehicule(vehiculeSelectionne);
                location.setPrixJour(vehiculeSelectionne.getCoutParJour());
                //Dates


                if (locationID == -1)
                {
                    locationDao.insert(location);
                }
                else
                {
                    locationDao.update(location);
                }

                finish();

                return true;

            case R.id.menu_supprimer:

                //Suppression

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void initDatePicker(final EditText editText)
    {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editText.setText(sdfDate.format(calendar.getTime()));

                if (editText == etDateDebut)
                {
                    dateDebut = calendar.getTime();
                }
                else if (editText == etDateFinPrevue)
                {
                    dateFin = calendar.getTime();
                }
                majPrix();
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(LocationModifierActivity.this, dateSetListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initTimePicker(final EditText editText)
    {
        final Calendar calendar = Calendar.getInstance();
        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                editText.setText(sdfTime.format(calendar.getTime()));
                majPrix();
            }
        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(LocationModifierActivity.this, timeSetListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH), true).show();
            }
        });
    }

    private void majPrix()
    {
        if (vehiculeSelectionne != null)
        {
            DateTime dateTimeDebut = new DateTime(dateDebut);
            DateTime dateTimeFin = new DateTime(dateFin);

            int nbJours = Days.daysBetween(dateTimeDebut, dateTimeFin).getDays();
            Log.d("lol", "Mise à jour du prix, nombre de jours " + nbJours);
            if (nbJours == 0) { nbJours = 1; }
            tvPrix.setText(String.format(java.util.Locale.US,"%.2f €",  nbJours * vehiculeSelectionne.getCoutParJour()));
        }
    }
}
