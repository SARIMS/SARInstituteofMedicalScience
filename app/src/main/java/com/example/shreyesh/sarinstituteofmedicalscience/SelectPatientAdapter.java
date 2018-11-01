package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectPatientAdapter extends RecyclerView.Adapter<SelectPatientAdapter.ViewHolder> {

    private Context context;
    private List<SelectedPatient> patientList;
    private String pid;

    public SelectPatientAdapter(List<SelectedPatient> patientList) {
        this.patientList = patientList;
    }

    public SelectPatientAdapter(Context context, String pid) {
        this.context = context;
        this.pid = pid;
    }

    public SelectPatientAdapter(List<SelectedPatient> patientList, String pid) {
        this.patientList = patientList;
        this.pid = pid;
    }

    @NonNull
    @Override
    public SelectPatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_patient_single_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectPatientAdapter.ViewHolder holder, int position) {
        String image = patientList.get(position).getImage();
        final String name = patientList.get(position).getName();
        final String id = patientList.get(position).getUserid();

        holder.setName(name);
        holder.setSelectPatientImage(image);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] option = {"View Reports", "Add New Report", "View Recommendations"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent1 = new Intent(context, AllReportsActivity.class);
                                intent1.putExtra("pid", id);
                                context.startActivity(intent1);
                                break;
                            case 1:
                                Intent intent = new Intent(context, AddReportActivity.class);
                                intent.putExtra("type", pid);
                                intent.putExtra("name", name);
                                intent.putExtra("pid", id);
                                context.startActivity(intent);
                                break;
                            case 2:
                                Intent intent2 = new Intent(context, ViewRecommendationActivity.class);
                                intent2.putExtra("pid", id);
                                context.startActivity(intent2);
                                break;
                        }
                    }
                });

                Dialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setSelectPatientImage(String image) {
            CircleImageView imageView = (CircleImageView) view.findViewById(R.id.selectPatientImage);
            Picasso.get().load(image).placeholder(R.drawable.avatarplaceholder).into(imageView);
        }

        public void setName(String name) {
            TextView tv = (TextView) view.findViewById(R.id.selectPatientName);
            tv.setText(name);
        }
    }
}
