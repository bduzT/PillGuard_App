package com.khas.pillguard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.khas.pillguard.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<String> userList;
    private OnItemClickListener listener;

    // Constructor
    public UserAdapter(ArrayList<String> users, OnItemClickListener listener) {
        this.userList = users;
        this.listener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        String patient = userList.get(position);
        holder.tvPatientName.setText(patient);
        holder.tvPatientDetails.setText("Details");


        holder.ivEditPatient.setOnClickListener(v -> listener.onEditClick(position));
        holder.ivDeletePatient.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvPatientDetails;
        ImageView ivPatientPhoto, ivEditPatient, ivDeletePatient;


    }


    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
