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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class ArmorAdapter extends FirestoreAdapter<ArmorAdapter.ViewHolder>
{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //TODO: Create weapon item XML
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.armor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnArmorSelectedListener {
        void onArmorSelected(DocumentSnapshot armor);
    }

    private OnArmorSelectedListener mListener;

    public ArmorAdapter(Query query, OnArmorSelectedListener listener)
    {
        super(query);
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView photoView;
        TextView nameView;
        TextView protectionProjectileView;
        TextView protectionMeleeView;
        TextView protectionColdView;
        TextView protectionRadiationView;

        public ViewHolder(View itemView)
        {
            //TODO: Finalize View contents. Keep toplocation?
            super(itemView);
            photoView = itemView.findViewById(R.id.weapon_image);
            nameView = itemView.findViewById(R.id.armor_name);
            protectionProjectileView =  itemView.findViewById(R.id.protection_projectile);
            protectionMeleeView =  itemView.findViewById(R.id.protection_melee);
            protectionColdView =  itemView.findViewById(R.id.protection_cold);
            protectionRadiationView =  itemView.findViewById(R.id.protection_radiation);
        }

        public void bind(final DocumentSnapshot snapshot, final OnArmorSelectedListener listener)
        {
            //TODO: FILL bind
            Armor armor = snapshot.toObject(Armor.class);
            Resources resources = itemView.getResources();

            // Load image
            Glide.with(photoView.getContext()).load(armor.getPicture()).into(photoView);

            // Update row entry
            nameView.setText(armor.getName());
            protectionProjectileView.setText(armor.getProtectionProjectile());
            protectionMeleeView.setText(armor.getProtectionMelee());
            protectionColdView.setText(armor.getProtectionCold());
            protectionRadiationView.setText(armor.getProtectionRadiation());

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.onArmorSelected(snapshot);
                    }
                }
            });
        }
    }
}
