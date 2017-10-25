package com.learning.insane.wecheaters;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learning.insane.wecheaters.Util.recentAdapter;


public class RecentFragment extends Fragment {

    RecyclerView recyclerView;

    public RecentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret =  inflater.inflate(R.layout.fragment_recent, container, false);
        recyclerView = container.findViewById(R.id.recentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new recentAdapter());
        return ret;
    }

}
