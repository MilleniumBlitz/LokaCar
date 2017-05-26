package fr.eni.lokacar.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.eni.lokacar.ClientListeActivity;
import fr.eni.lokacar.LocationListeActivity;
import fr.eni.lokacar.MainActivity;
import fr.eni.lokacar.R;
import fr.eni.lokacar.app.App;
import fr.eni.lokacar.dao.Client;
import fr.eni.lokacar.dao.ClientDao;
import fr.eni.lokacar.dao.DaoSession;
import fr.eni.lokacar.dao.LocationDao;

/**
 * Created by Administrateur on 23/05/2017.
 */

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientHolder> {

    private List<Client> listeClients;
    private OnClickListener onItemClickListener;
    private LocationDao locationDao;

    public ClientAdapter(List<Client> listeClients, LocationDao locationDao, OnClickListener listener) {
        this.listeClients = listeClients;
        onItemClickListener = listener;
        this.locationDao = locationDao;
    }

    public static class ClientHolder extends RecyclerView.ViewHolder {
        private TextView tvNomPrenom;
        private TextView tvTelephone;
        private TextView tvNbLocations;
        private TextView tvNbLocationsEnCours;

        public ClientHolder(View itemView) {
            super(itemView);

            tvNomPrenom = (TextView) itemView.findViewById(R.id.tv_client_layout_nom_prenom);
            tvTelephone = (TextView) itemView.findViewById(R.id.tv_client_layout_telephone);
            tvNbLocations = (TextView) itemView.findViewById(R.id.tv_client_layout_nb_locations);
            tvNbLocationsEnCours = (TextView) itemView.findViewById(R.id.tv_client_layout_nb_locations_en_cours);
        }

        public void bind(final Client client, final ClientAdapter.OnClickListener listener, final LocationDao locationDao) {
            tvNomPrenom.setText(client.getNom() + " " + client.getPrenom());
            tvTelephone.setText(client.getTelephone());

            // TODO: afficher les nb locations du client


            int nbLocations = locationDao.getNbLocations(client, LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_TOUS);
            int nbLocationsEnCours = locationDao.getNbLocations(client, LocationListeActivity.MODE_LOCATIONS.LISTE_LOC_EN_COURS);
            Log.i("lol","nbLocations = " + nbLocations);
            Log.i("lol","nbLocationsEnCours = " + nbLocationsEnCours);

            tvNbLocations.setText(nbLocations + " locations");
            tvNbLocationsEnCours.setText(nbLocationsEnCours + " locations en cours");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(client);
                }
            });

            itemView.findViewById(R.id.ib_client_layout_telephone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + client.getTelephone()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public ClientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_layout, parent, false);
        return new ClientHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientHolder holder, int position) {
        holder.bind(listeClients.get(position), onItemClickListener, locationDao);
    }

    @Override
    public int getItemCount() {
        return listeClients.size();
    }

    public interface OnClickListener{
        void onClick(Client client);
    }
}
