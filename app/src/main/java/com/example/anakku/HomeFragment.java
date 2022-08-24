package com.example.anakku;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.adapters.ActivityAdapter;
import com.example.anakku.settings.SharedPref;
import com.example.anakku.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {

    private RelativeLayout loadingPanel;
    private TextView welcomeTextView;
    private Button profileAnakButton;
    private Button imunisasiButton;
    private Button keluhanAnakButton;
    private Button artikelButton;
    private RecyclerView activityRecycler;
    private ActivityAdapter activityAdapter;

    private RelativeLayout loadingPanelRecycler;
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeTextView = view.findViewById(R.id.textViewWelcome);
        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(view.VISIBLE);
        profileAnakButton = view.findViewById(R.id.buttonProfileAnak);
        imunisasiButton = view.findViewById(R.id.buttonImunisasi);
        keluhanAnakButton = view.findViewById(R.id.buttonKeluhanAnak);
        artikelButton = view.findViewById(R.id.buttonArtikel);

        loadingPanelRecycler = view.findViewById(R.id.loadingPanelRecycler);
        loadingPanelRecycler.setVisibility(View.VISIBLE);

        activityRecycler = view.findViewById(R.id.recyclerViewActivityAnak);
        activityRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        activityRecycler.setHasFixedSize(true);

        homeViewModel.updateUserDocumentIdSharedPref();

        homeViewModel.getChild().observe(getViewLifecycleOwner(), child -> {
            if(child != null) {
                welcomeTextView.setText("Selamat Datang, " + child.getNama() + "!");

                homeViewModel.getActivityAnakMutableLiveData().observe(getViewLifecycleOwner(), activityAnakList -> {
                    loadingPanelRecycler.setVisibility(View.GONE);
                    activityAdapter = new ActivityAdapter(activityAnakList);
                    activityRecycler.setAdapter(activityAdapter);
                    activityAdapter.notifyDataSetChanged();
                });
            }
            else Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_registerChildFragment);
            loadingPanel.setVisibility(view.GONE);
        });

        profileAnakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_childProfileFragment);
            }
        });

        imunisasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_immunizationFragment);
            }
        });

        keluhanAnakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_childComplaintFragment);
            }
        });

        artikelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_educationalArticleFragment);
            }
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
        homeViewModel.stopHomeListener();
    }
}