package com.example.shreyesh.sarinstituteofmedicalscience;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentsFragment extends Fragment {


    private List<Departments> departmentsList;
    private DepartmentsAdapter departmentsAdapter;
    private RecyclerView departmentRecyclerView;

    public DepartmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_departments, container, false);


        departmentRecyclerView = (RecyclerView) view.findViewById(R.id.departmentsrecyclerView);
        departmentsList = new ArrayList<>();
        departmentsAdapter = new DepartmentsAdapter(departmentsList);

        departmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        departmentRecyclerView.setAdapter(departmentsAdapter);


        departmentsList.add(new Departments("Department of Cardiology", "Hey"));


        return view;
    }

}
