package com.khas.pillguard.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.khas.pillguard.R;
import com.khas.pillguard.models.User;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> userList;
    private OnItemClickListener listener;

    public UserAdapter(ArrayList<User> users, OnItemClickListener listener) {
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
        User user = userList.get(position);

        holder.tvUserName.setText(user.getFullName());
        holder.tvUserDetails.setText(user.getDateOfBirth());

        if (user.getPhotoBytes() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPhotoBytes(), 0, user.getPhotoBytes().length);
            holder.patientPhoto.setImageBitmap(bitmap);
        } else {
            holder.patientPhoto.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        holder.ivEditUser.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(user);
            }
        });

        holder.ivDeleteUser.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(user.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserDetails;
        ImageView ivEditUser, ivDeleteUser, patientPhoto;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserDetails = itemView.findViewById(R.id.tvUserDetails);
            ivEditUser = itemView.findViewById(R.id.ivEditUser);
            ivDeleteUser = itemView.findViewById(R.id.ivDeleteUser);
            patientPhoto = itemView.findViewById(R.id.patientPhoto);
        }
    }

    public interface OnItemClickListener {
        void onEditClick(User user);
        void onDeleteClick(int userId);
    }
}
