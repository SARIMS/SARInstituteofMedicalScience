package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AppointmentRecyclerAdapter extends RecyclerView.Adapter<AppointmentRecyclerAdapter.ViewHolder> {

    private Context context;
    private DatabaseReference aRef;
    private FirebaseAuth firebaseAuth;
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
    public void onBindViewHolder(@NonNull AppointmentRecyclerAdapter.ViewHolder holder, final int position) {

        String date=appointmentList.get(position).getAppointmentDate();
        String time=appointmentList.get(position).getAppointmentTime();
        String doctorName=appointmentList.get(position).getDoctorName();
        final String id = appointmentList.get(position).getId();

        holder.setAppointmentDate(date);
        holder.setAppointmentTime(time);
        holder.setDName(doctorName);

        System.out.println(id);
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        aRef = FirebaseDatabase.getInstance().getReference().child("appointments").child(uid);

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cancel Appointment");
                builder.setMessage("Are you sure you want to cancel this ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println(id);
                        aRef.child(id).removeValue();
                        appointmentList.remove(position);
                        notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                builder.create().show();
                return true;
            }
        });
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
