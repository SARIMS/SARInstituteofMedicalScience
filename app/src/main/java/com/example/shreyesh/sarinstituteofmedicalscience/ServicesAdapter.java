package com.example.shreyesh.sarinstituteofmedicalscience;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    Context context;
    private List<Services> servicesList;

    public ServicesAdapter(List<Services> servicesList) {
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_single_layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {

        String title = servicesList.get(position).getTitle();
        String img = servicesList.get(position).getImage();
        holder.setTitle(title);
        holder.setImage(img);


        switch (position) {

            //Blood tests
            case 0:
                final String[] bloodTest = {"TSH", "ABO Typing", "Liver Enzymes"};
                final ArrayList selecteditem = new ArrayList();

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Tests");
                builder.setMultiChoiceItems(bloodTest, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selecteditem.add(i);
                        } else if (selecteditem.contains(i)) {
                            selecteditem.remove(Integer.valueOf(i));
                        }

                    }
                });

                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        HashMap<String, Integer> testMap = new HashMap<>();
                        testMap.put("TSH", 500);
                        testMap.put("ABO Typing", 650);
                        testMap.put("Liver Enzymes", 600);

                        Integer total = 0;
                        for (Object s : selecteditem) {

                            if (testMap.containsKey(bloodTest[Integer.valueOf(s.toString())])) {
                                total = total + testMap.get(bloodTest[Integer.valueOf(s.toString())]);
                            }
                        }
                        Intent intent = new Intent(context, ConfirmServicesActivity.class);
                        intent.putExtra("total", total.toString());
                        intent.putStringArrayListExtra("itemSelectList", selecteditem);
                        intent.putExtra("itemMap", testMap);
                        ArrayList<String> itemList = new ArrayList<>(Arrays.asList(bloodTest));
                        intent.putStringArrayListExtra("itemList", itemList);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selecteditem.clear();

                    }
                });
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog dialog = builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                });

                break;

            //Imaging
            case 1:
                final String[] imaging = {"X-Ray", "CT Scan", "MRI"};
                final ArrayList selectedImaging = new ArrayList();

                final AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setTitle("Select ");
                builder2.setMultiChoiceItems(imaging, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            selectedImaging.add(i);
                        } else if (selectedImaging.contains(i)) {
                            selectedImaging.remove(Integer.valueOf(i));
                        }

                    }
                });

                builder2.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        HashMap<String, Integer> imgMap = new HashMap<>();
                        imgMap.put("X-Ray", 600);
                        imgMap.put("CT Scan", 800);
                        imgMap.put("MRI", 10000);

                        int total = 0;
                        for (Object s : selectedImaging) {
                            if (imgMap.containsKey(imaging[Integer.valueOf(s.toString())])) {

                                total = total + imgMap.get(imaging[Integer.valueOf(s.toString())]);
                            }
                        }

                        Intent intent = new Intent(context, ConfirmServicesActivity.class);
                        intent.putExtra("total", String.valueOf(total));
                        intent.putExtra("itemMap", imgMap);
                        intent.putStringArrayListExtra("itemSelectList", selectedImaging);
                        ArrayList<String> iTest = new ArrayList<>(Arrays.asList(imaging));
                        intent.putStringArrayListExtra("itemList", iTest);
                        context.startActivity(intent);
                        ((Activity) context).finish();

                    }
                });

                builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedImaging.clear();

                    }
                });
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog dialog = builder2.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                    }
                });

                break;


        }


    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setImage(String img) {
            ImageView imageView = (ImageView) view.findViewById(R.id.servicesSingleImage);
            Picasso.get().load(img).into(imageView);
        }

        public void setTitle(String t) {
            TextView title = (TextView) view.findViewById(R.id.servicesSingleTitle);
            title.setText(t);

        }
    }
}
