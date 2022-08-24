package com.example.anakku;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.adapters.ChildAdapter;
import com.example.anakku.models.Child;
import com.example.anakku.viewmodels.PickChildViewModel;

public class PickChildFragment extends Fragment {
    private PickChildViewModel pickChildViewModel;

    private RelativeLayout loadingPanel;
    private RecyclerView childRecyclerView;
    private ChildAdapter childAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pickChildViewModel = new ViewModelProvider(this).get(PickChildViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_child, container, false);

        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.VISIBLE);

        childRecyclerView = view.findViewById(R.id.recyclerViewChild);
        childRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        childRecyclerView.setHasFixedSize(true);

        pickChildViewModel.getChildListMutableLiveData().observe(getViewLifecycleOwner(), children -> {
            loadingPanel.setVisibility(View.GONE);

            if(children.size() == 0 || children.get(children.size()-1) != null) children.add(null);
            childAdapter = new ChildAdapter(children);
            childRecyclerView.setAdapter(childAdapter);
            childAdapter.notifyDataSetChanged();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        pickChildViewModel.stopChildsListener();
    }
}