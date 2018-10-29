package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDoctorListAdapter extends RecyclerView.Adapter<AdminDoctorListAdapter.ViewHolder> {
    @NonNull

    private Context context;
    private List<Doctor> doctorList;

    public AdminDoctorListAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }


    @Override
    public AdminDoctorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_single_layout, parent, false);
        context = parent.getContext();
        return new AdminDoctorListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminDoctorListAdapter.ViewHolder holder, int position) {
        String name = doctorList.get(position).getName();
        String department = doctorList.get(position).getDepartment();
        String monday = doctorList.get(position).getMonday();
        String tuesday = doctorList.get(position).getTuesday();
        String wednesday = doctorList.get(position).getWednesday();
        String thursday = doctorList.get(position).getThursday();
        String friday = doctorList.get(position).getFriday();
        String saturday = doctorList.get(position).getSaturday();
        String sunday = doctorList.get(position).getSunday();
        String image = doctorList.get(position).getImage();


        holder.setname(name);
        holder.setDepartment(department);
        holder.setSunday(sunday);
        holder.setMonday(monday);
        holder.setThursday(thursday);
        holder.setTuesday(tuesday);
        holder.setWednesday(wednesday);
        holder.setFriday(friday);
        holder.setSaturday(saturday);


        holder.setBookingButton();
        holder.setImage(image);


    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setname(String n) {
            TextView dname = (TextView) view.findViewById(R.id.doctorNameSingle);
            dname.setText(n);
        }

        public void setDepartment(String dept) {
            TextView department = (TextView) view.findViewById(R.id.doctorDepartmentSingle);
            department.setText(dept);
        }

        public void setSunday(String time) {
            TextView sunday = (TextView) view.findViewById(R.id.sundayTimings);
            sunday.setText(time);
        }

        public void setMonday(String time) {
            TextView monday = (TextView) view.findViewById(R.id.mondayTimings);
            monday.setText(time);
        }

        public void setTuesday(String time) {
            TextView tuesday = (TextView) view.findViewById(R.id.tuesdayTimings);
            tuesday.setText(time);
        }

        public void setWednesday(String time) {
            TextView wed = (TextView) view.findViewById(R.id.wednesdayTimings);
            wed.setText(time);
        }

        public void setThursday(String time) {
            TextView th = (TextView) view.findViewById(R.id.thursdayTimings);
            th.setText(time);
        }

        public void setFriday(String time) {
            TextView friday = (TextView) view.findViewById(R.id.fridayTimings);
            friday.setText(time);
        }

        public void setSaturday(String time) {
            TextView sat = (TextView) view.findViewById(R.id.saturdayTimings);
            sat.setText(time);
        }

        public void setBookingButton() {
            Button button = (Button) view.findViewById(R.id.bookAppointmentSingle);
            button.setVisibility(View.GONE);

        }

        public void setImage(String img) {
            CircleImageView imageView = (CircleImageView) view.findViewById(R.id.doctorImageSingle);
            Picasso.get().load(img).placeholder(R.drawable.avatarplaceholder).into(imageView);
        }
    }

}

