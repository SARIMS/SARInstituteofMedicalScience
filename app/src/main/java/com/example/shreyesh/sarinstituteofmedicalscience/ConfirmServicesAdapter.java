package com.example.shreyesh.sarinstituteofmedicalscience;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConfirmServicesAdapter extends RecyclerView.Adapter<ConfirmServicesAdapter.ViewHolder> {


    private List<ServiceConfirm> confirmList;
    private Context context;

    public ConfirmServicesAdapter(List<ServiceConfirm> confirmList) {
        this.confirmList = confirmList;
    }

    @NonNull
    @Override
    public ConfirmServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_confirm_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmServicesAdapter.ViewHolder holder, int position) {

        String itemName = confirmList.get(position).getItemName();
        String price = confirmList.get(position).getPrice();

        holder.setitemName(itemName);
        holder.setPrice(price);

    }

    @Override
    public int getItemCount() {
        return confirmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setitemName(String name) {
            TextView item = (TextView) view.findViewById(R.id.singleConfirmItemName);
            item.setText(name);
        }

        public void setPrice(String price) {
            TextView p = (TextView) view.findViewById(R.id.singleConfirmPrice);
            p.setText(price);
        }
    }
}
