package fr.eni.lokacar.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.ReadableInterval;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.R;
import fr.eni.lokacar.dao.Location;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationHolder> {

    private final OnClickListener listener;
    private List<Location> listeLocations;

    public LocationAdapter(List<Location> listeLocations, OnClickListener listener) {
        this.listeLocations = listeLocations;
        this.listener = listener;
    }

    @Override
    public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_layout, parent, false);
        return new LocationHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationHolder holder, int position) {
        holder.bind(listeLocations.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listeLocations.size();
    }

    public static class LocationHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_location_layout_client) TextView tvLocationClient;
        @BindView(R.id.tv_location_layout_vehicule) TextView tvLocationVehicule;
        @BindView(R.id.tv_location_layout_dates) TextView tvLocationDates;
        @BindView(R.id.tv_location_layout_prix) TextView tvLocationPrix;

        public LocationHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Location location, final OnClickListener listener)
        {
            tvLocationClient.setText(location.getClient().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            String locationDateDebut = dateFormat.format(location.getDateDebut());
            String locationDateFin = dateFormat.format(location.getDatePrevue());

            tvLocationVehicule.setText(location.getVehicule().getModele());
            tvLocationDates.setText("Du " + locationDateDebut + " au " + locationDateFin);

            DateTime dateDebut = new DateTime(location.getDateDebut());
            DateTime dateFin = new DateTime(location.getDatePrevue());


            int nbJours = Days.daysBetween(dateDebut.toLocalDate(), dateFin.toLocalDate()).getDays();
            Log.d("lol", "LocationAdapter, mise à jour du prix, nombre de jours " + nbJours);
            if (nbJours == 0) { nbJours = 1; }
            tvLocationPrix.setText(String.format(java.util.Locale.US,"%.2f €",  nbJours * location.getPrixJour()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(location);
                }
            });
        }
    }



    public interface OnClickListener {
        void onClick(Location location);
    }




}
