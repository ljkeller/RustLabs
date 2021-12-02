package com.example.rustlabs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rustlabs.R;
import com.example.rustlabs.model.Tip;
import com.google.firebase.firestore.Query;

/**
 * RecyclerView adapter for collection of tips
 */
public class TipAdapter extends FirestoreAdapter<TipAdapter.ViewHolder>
{
    public TipAdapter(Query query) { super(query); }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // TODO: Implement item tip layout
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip,
                                                                               parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind(getSnapshot(position).toObject(Tip.class));
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        //TODO: link views here.
        TextView nameView;
        TextView textView;

        public ViewHolder(View itemView)
        {
            //TODO: Implement R.id.tip_item_name & R.id.tip_item_text
            super(itemView);
            nameView = itemView.findViewById(R.id.tip_item_name);
            textView = itemView.findViewById(R.id.tip_item_text);
        }

        public void bind(Tip tip)
        {
            nameView.setText(tip.getUserName());
            textView.setText(tip.getText());
        }
    }
}
