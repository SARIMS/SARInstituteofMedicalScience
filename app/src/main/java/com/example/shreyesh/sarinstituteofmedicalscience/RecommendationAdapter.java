package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {

    private Context context;
    private List<Appointment> recList;

    public RecommendationAdapter(List<Appointment> recList) {
        this.recList = recList;
    }

    @NonNull
    @Override
    public RecommendationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_layout_single, parent, false);
        context = parent.getContext();
        return new RecommendationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.ViewHolder holder, int position) {
        String date = recList.get(position).getAppointmentDate();
        String time = recList.get(position).getAppointmentTime();
        String doctorName = recList.get(position).getDoctorName();

        holder.setAppointmentDate(date);
        holder.setAppointmentTime();
        holder.setRec(doctorName);

    }

    @Override
    public int getItemCount() {
        return recList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setRec(String dname) {
            TextView doctorName = (TextView) view.findViewById(R.id.appointmentSingleDoctorName);
            doctorName.setText(dname);
        }

        public void setAppointmentDate(String aDate) {
            TextView ad = (TextView) view.findViewById(R.id.appointmentSingleDate);
            ad.setText(aDate);
        }

        public void setAppointmentTime() {
            TextView at = (TextView) view.findViewById(R.id.appointmentSingleTime);
            at.setVisibility(View.GONE);
        }
    }
}
