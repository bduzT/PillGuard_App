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
        String user = userList.get(position);

        holder.tvUserName.setText(user);
        holder.tvUserDetails.setText("Details");

        holder.ivEditUser.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(position);
            }
        });

        holder.ivDeleteUser.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserDetails;
        ImageView ivEditUser, ivDeleteUser;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserDetails = itemView.findViewById(R.id.tvUserDetails);
            ivEditUser = itemView.findViewById(R.id.ivEditUser);
            ivDeleteUser = itemView.findViewById(R.id.ivDeleteUser);
        }
    }

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
