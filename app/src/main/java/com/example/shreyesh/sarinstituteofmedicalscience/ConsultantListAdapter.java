package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ConsultantListAdapter extends RecyclerView.Adapter<ConsultantListAdapter.ViewHolder> {

    private Context context;
    private List<Patient> consultantList;

    public ConsultantListAdapter(List<Patient> consultantList) {
        this.consultantList = consultantList;
    }

    @NonNull
    @Override
    public ConsultantListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_single_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultantListAdapter.ViewHolder holder, int position) {
        String name = consultantList.get(position).getName();
        String age = consultantList.get(position).getAge();
        String gender = consultantList.get(position).getGender();
        String image = consultantList.get(position).getImage();

        holder.setCGender(gender);
        holder.setConsultantName(name);
        holder.setCImage(image);
        holder.setConsultantAge(age);
    }

    @Override
    public int getItemCount() {
        return consultantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setConsultantName(String s) {
            TextView name = (TextView) view.findViewById(R.id.singleName);
            name.setText(s);

        }

        public void setConsultantAge(String s) {
            TextView age = (TextView) view.findViewById(R.id.singleAge);
            age.setText(s);
        }

        public void setCGender(String s) {
            TextView g = (TextView) view.findViewById(R.id.singleSex);
            g.setText(s);
        }

        public void setCImage(String s) {
            ImageView imageView = (ImageView) view.findViewById(R.id.singleImage);
            Picasso.get().load(s).placeholder(R.drawable.avatarplaceholder).into(imageView);
        }
    }
}
