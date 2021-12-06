package com.example.rustlabs.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rustlabs.R;
import com.example.rustlabs.model.Armor;
import com.example.rustlabs.model.Structure;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class StructureAdapter extends FirestoreAdapter<StructureAdapter.ViewHolder>
{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //TODO: Create Structure item XML
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.structure, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnStructureSelectedListener {
        void onStructureSelected(DocumentSnapshot structure);
    }

    private OnStructureSelectedListener mListener;

    public StructureAdapter(Query query, OnStructureSelectedListener listener)
    {
        super(query);
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView photoView;
        TextView nameView;
        TextView raidCostC4View;
        TextView raidCostRocketView;
        TextView raidCostExplosiveAmmoView;
        TextView raidCostSatchelView;
        TextView healthView;

        public ViewHolder(View itemView)
        {
            //TODO: Finalize View contents. Keep toplocation?
            super(itemView);
            photoView = itemView.findViewById(R.id.structure_image);
            nameView = itemView.findViewById(R.id.structure_name);
            raidCostC4View =  itemView.findViewById(R.id.c4_cost);
            raidCostRocketView =  itemView.findViewById(R.id.rocket_cost);
            raidCostExplosiveAmmoView =  itemView.findViewById(R.id.explosive_ammo_cost);
            raidCostSatchelView =  itemView.findViewById(R.id.satchel_cost);
            healthView = itemView.findViewById(R.id.health);
        }

        public void bind(final DocumentSnapshot snapshot, final OnStructureSelectedListener listener)
        {
            //TODO: FILL bind
            Structure structure = snapshot.toObject(Structure.class);
            Resources resources = itemView.getResources();

            // Load image
            Glide.with(photoView.getContext()).load(structure.getPicture()).into(photoView);

            // Update row entry
            nameView.setText(structure.getName());
            raidCostC4View.setText(String.valueOf(structure.getRaidC4()));
            raidCostRocketView.setText(String.valueOf(structure.getRaidRocket()));
            raidCostExplosiveAmmoView.setText(String.valueOf(structure.getRaidExplosiveAmmo()));
            raidCostSatchelView.setText(String.valueOf(structure.getRaidSatchel()));
            healthView.setText(String.valueOf(structure.getHealth()));

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.onStructureSelected(snapshot);
                    }
                }
            });
        }
    }
}
