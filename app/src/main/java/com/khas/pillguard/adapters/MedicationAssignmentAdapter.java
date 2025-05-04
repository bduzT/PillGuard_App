package com.khas.pillguard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicationAssignmentAdapter extends RecyclerView.Adapter<MedicationAssignmentAdapter.ViewHolder> {

    private List<MedicationAssignmentModel> assignmentList;

    public MedicationAssignmentAdapter(List<MedicationAssignmentModel> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MedicationAssignmentModel item = assignmentList.get(position);
        holder.tvPatient.setText("Patient: " + item.getPatientName());
        holder.tvMedication.setText("Medication: " + item.getMedicationName());
        holder.tvDays.setText("Days: " + String.join(", ", item.getDays()));
        holder.tvTime.setText("Time: " + item.getTime());
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatient, tvMedication, tvDays, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatient = itemView.findViewById(R.id.tvPatient);
            tvMedication = itemView.findViewById(R.id.tvMedication);
            tvDays = itemView.findViewById(R.id.tvDays);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
