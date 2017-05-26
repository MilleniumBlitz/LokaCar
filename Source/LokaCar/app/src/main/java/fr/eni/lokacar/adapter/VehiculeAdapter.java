package fr.eni.lokacar.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.eni.lokacar.R;
import fr.eni.lokacar.dao.Vehicule;

/**
 * Created by Administrateur on 22/05/2017.
 */

public class VehiculeAdapter extends RecyclerView.Adapter<VehiculeAdapter.VehiculeHolder>{

    private List<Vehicule> listeVehicules;
    private OnClickListener onItemClickListener;


    public VehiculeAdapter(List<Vehicule> listeVehicules, OnClickListener listener) {
        this.listeVehicules = listeVehicules;
        onItemClickListener = listener;
    }

    public static class VehiculeHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_vehicule_layout_marque) TextView tv_vehicule_layout_marque;
        @BindView(R.id.tv_vehicule_layout_modele) TextView tv_vehicule_layout_modele;
        @BindView(R.id.tv_vehicule_layout_coutparjour) TextView tv_vehicule_layout_coutparjour;
        @BindView(R.id.tv_vehicule_layout_disponible) TextView tv_vehicule_layout_disponible;

        public VehiculeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final Vehicule vehicule, final VehiculeAdapter.OnClickListener listener)
        {
            tv_vehicule_layout_marque.setText(vehicule.getMarque());
            tv_vehicule_layout_modele.setText(vehicule.getModele());
            tv_vehicule_layout_coutparjour.setText(Integer.toString((int) vehicule.getCoutParJour())+"€");
            if (vehicule.getDisponible()){
                tv_vehicule_layout_disponible.setText("Disponible");
            } else {
                tv_vehicule_layout_disponible.setText("Indisponible");
                tv_vehicule_layout_disponible.setTextColor(Color.parseColor("#FF0000"));
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(vehicule);
                }
            });
        }
    }

    @Override
    public VehiculeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicule_layout, parent, false);
        return new VehiculeHolder(view);
    }

    @Override
    public void onBindViewHolder(VehiculeHolder holder, int position) {
        holder.bind(listeVehicules.get(position),onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listeVehicules.size();
    }

    public interface OnClickListener{
        void onClick(Vehicule vehicule);
    }

}
