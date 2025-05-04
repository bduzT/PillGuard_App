package com.khas.pillguard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IntakeHistoryAdapter extends RecyclerView.Adapter<IntakeHistoryAdapter.HistoryViewHolder> {

    private List<IntakeHistoryModel> historyList;

    public IntakeHistoryAdapter(List<IntakeHistoryModel> historyList) {
        this.historyList = historyList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intake_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        IntakeHistoryModel item = historyList.get(position);
        holder.tvPatientName.setText(item.getPatientName());
        holder.tvMedicine.setText(item.getMedicineName());
        holder.tvDateTime.setText(item.getDate() + " - " + item.getTime());
        holder.tvStatus.setText(item.isTaken() ? "Taken" : "Missed");
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvMedicine, tvDateTime, tvStatus;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvMedicine = itemView.findViewById(R.id.tvMedicine);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
