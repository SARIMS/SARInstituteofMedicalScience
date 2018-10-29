package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AppointmentRecyclerAdapter extends RecyclerView.Adapter<AppointmentRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Appointment> appointmentList;

    public AppointmentRecyclerAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_layout_single,parent,false);
        context=parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentRecyclerAdapter.ViewHolder holder, int position) {

        String date=appointmentList.get(position).getAppointmentDate();
        String time=appointmentList.get(position).getAppointmentTime();
        String doctorName=appointmentList.get(position).getDoctorName();

        holder.setAppointmentDate(date);
        holder.setAppointmentTime(time);
        holder.setDName(doctorName);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
        }

        public void setDName(String dname) {
            TextView doctorName=(TextView)view.findViewById(R.id.appointmentSingleDoctorName);
            doctorName.setText(dname);
        }

        public void setAppointmentDate(String aDate){
            TextView ad=(TextView)view.findViewById(R.id.appointmentSingleDate);
            ad.setText(aDate);
        }

        public void setAppointmentTime(String aTime){
            TextView at=(TextView)view.findViewById(R.id.appointmentSingleTime);
            at.setText(aTime);
        }
    }
}
