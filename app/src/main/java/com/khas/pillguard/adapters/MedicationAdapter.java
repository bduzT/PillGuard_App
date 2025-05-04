package com.khas.pillguard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khas.pillguard.Medication;
import com.khas.pillguard.R;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private List<Medication> medicationList;

    public MedicationAdapter(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication medication = medicationList.get(position);
        holder.tvMedicationName.setText(medication.getName());
        holder.tvTime.setText(medication.getTime());
        holder.tvStatus.setText(medication.getStatus());
    }

    @Override
    public int getItemCount() {
        return medicationList.size();
    }

    static class MedicationViewHolder extends RecyclerView.ViewHolder {
        TextView tvMedicationName;
        TextView tvTime;
        TextView tvStatus;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicationName = itemView.findViewById(R.id.tvMedicationName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
} 