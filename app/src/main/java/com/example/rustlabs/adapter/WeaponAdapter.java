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
import com.example.rustlabs.model.Weapon;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class WeaponAdapter extends FirestoreAdapter<WeaponAdapter.ViewHolder>
{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //TODO: Create weapon item XML
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.weapon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind(getSnapshot(position), mListener);
    }

    public interface OnWeaponSelectedListener {
        void onWeaponSelected(DocumentSnapshot weapon);
    }

    private OnWeaponSelectedListener mListener;

    public WeaponAdapter(Query query, OnWeaponSelectedListener listener)
    {
        super(query);
        mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView photoView;
        TextView nameView;
        TextView ammoTypeView;
        TextView topLocationView;
        TextView damageView;

        public ViewHolder(View itemView)
        {
            //TODO: Finalize View contents. Keep toplocation?
            super(itemView);
            photoView = itemView.findViewById(R.id.weapon_image);
            nameView = itemView.findViewById(R.id.weapon_name);
            ammoTypeView = itemView.findViewById(R.id.weapon_damage);
            //topLocationView = itemView.findViewById(R.id.weapon_top_location);
            damageView =  itemView.findViewById(R.id.weapon_damage);
        }

        public void bind(final DocumentSnapshot snapshot, final OnWeaponSelectedListener listener)
        {
            //TODO: FILL bind
            Weapon weapon = snapshot.toObject(Weapon.class);
            Resources resources = itemView.getResources();

            // Load image
            Glide.with(photoView.getContext()).load(weapon.getPhoto()).into(photoView);

            // Update row entry
            nameView.setText(weapon.getName());
            ammoTypeView.setText(weapon.getAmmoType());
            topLocationView.setText(weapon.getTopLocation());
            damageView.setText(weapon.getTopLocation());

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.onWeaponSelected(snapshot);
                    }
                }
            });
        }
    }
}
