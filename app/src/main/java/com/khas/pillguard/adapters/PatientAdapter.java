package com.khas.pillguard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.khas.pillguard.R;
import com.khas.pillguard.PatientModel;
import java.util.List;


public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private List<PatientModel> patientList;

    public PatientAdapter(List<PatientModel> patientList) {
        this.patientList = patientList;
    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        PatientModel patient = patientList.get(position);
        holder.tvName.setText(patient.getFirstName() + " " + patient.getLastName());
        holder.tvDob.setText("DoB: " + patient.getDob());
        holder.tvGender.setText("Gender: " + patient.getGender());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDob, tvGender;

        public PatientViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPatientName);
            tvDob = itemView.findViewById(R.id.tvPatientDob);
            tvGender = itemView.findViewById(R.id.tvPatientGender);
        }
    }
}
