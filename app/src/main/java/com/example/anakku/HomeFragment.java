package com.example.anakku;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.models.Child;
import com.example.anakku.viewmodels.ChildViewModel;

public class HomeFragment extends Fragment {

    private RelativeLayout loadingPanel;
    private TextView welcomeTextView;
    private Button profileAnakButton;
    private Button imunisasiButton;
    private Button keluhanAnakButton;
    private Button artikelButton;

    private ChildViewModel childViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childViewModel = new ViewModelProvider(requireActivity()).get(ChildViewModel.class);
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

        childViewModel.getChild().observe(getViewLifecycleOwner(), child -> {
            if(child != null) welcomeTextView.setText("Selamat Datang, " + child.getNama() + "!");
            else Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_registerChildFragment);
            loadingPanel.setVisibility(view.GONE);
        });

        childViewModel.getChilds().observe(getViewLifecycleOwner(), childs -> {
            for ( Child c : childs ) {
                Log.d("TESTT", c.getNama());
            }
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
}