package com.example.buddyapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.ViewHolder> {
    // TODO: Define List<Buddy> variable here

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the row layout
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the TextViews
        // Handle "Happy Birthday" WhatsApp click here
    }

    @Override
    public int getItemCount() {
        return 0; // Return list size
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize Views (TextView name, phone, etc.)
        }
    }
}
