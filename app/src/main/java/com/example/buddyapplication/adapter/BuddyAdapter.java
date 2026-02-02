package com.example.buddyapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddyapplication.R;
import com.example.buddyapplication.activities.AddEditBuddyActivity;
import com.example.buddyapplication.database.DBHelper;
import com.example.buddyapplication.model.Buddy;

import java.io.Serializable;
import java.util.List;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.ViewHolder> {
    Context context;
    List<Buddy> buddyList;
    DBHelper dbHelper;

    public BuddyAdapter(Context context, List<Buddy> buddyList, DBHelper dbHelper) {
        this.context = context;
        this.buddyList = buddyList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buddy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Buddy buddy = buddyList.get(position);
        holder.tvName.setText(buddy.getName());
        holder.tvPhone.setText(buddy.getPhone());

        // Set first letter of name as avatar
        String name = buddy.getName();
        if (name != null && !name.trim().isEmpty()) {
            String firstLetter = name.trim().substring(0, 1).toUpperCase();
            holder.tvAvatarLetter.setText(firstLetter);
        } else {
            holder.tvAvatarLetter.setText("?");
        }

        // Set background color based on gender
        if ("Female".equalsIgnoreCase(buddy.getGender())) {
            holder.tvAvatarLetter.setBackgroundResource(R.drawable.bg_avatar_female);
        } else {
            holder.tvAvatarLetter.setBackgroundResource(R.drawable.bg_avatar_male);
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditBuddyActivity.class);
            intent.putExtra("EXTRA_BUDDY", (Serializable) buddy);
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteBuddy(buddy.getId());
            buddyList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, buddyList.size());
            Toast.makeText(context, "Deleted " + buddy.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            String phone = buddy.getPhone();
            if (!phone.isEmpty()) {
                String message = "Happy Birthday " + buddy.getName() + "!";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://wa.me/" + phone + "?text=" + message));
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return buddyList.size();
    }

    public void updateList(List<Buddy> newList) {
        buddyList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        ImageView btnEdit, btnDelete;
        TextView tvAvatarLetter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvAvatarLetter = itemView.findViewById(R.id.tv_avatar_letter);
        }
    }
}