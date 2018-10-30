package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AllReportsAdapter extends RecyclerView.Adapter<AllReportsAdapter.ViewHolder> {

    private List<Report> reportList;
    private Context context;

    public AllReportsAdapter(List<Report> reportList) {
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public AllReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_single_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllReportsAdapter.ViewHolder holder, int position) {
        String name = reportList.get(position).getTitle();
        String staff = reportList.get(position).getStaff();
        final String pid = reportList.get(position).getPid();
        final String reportid = reportList.get(position).getReportid();

        holder.setReportStaff(staff);
        holder.setReportTitle(name);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewReportsActivity.class);
                intent.putExtra("pid", pid);
                intent.putExtra("reportid", reportid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setReportTitle(String s) {
            TextView t = (TextView) view.findViewById(R.id.reportTitleSingle);
            t.setText(s);

        }

        public void setReportStaff(String s) {
            TextView t = (TextView) view.findViewById(R.id.reportDoctorSingle);
            t.setText(s);
        }

    }
}
