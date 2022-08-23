package com.example.anakku;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.viewmodels.ChildViewModel;

public class EducationalArticleFragment extends Fragment {

    private Button kekerasanFisikButton, pertumbuhanPerkembanganButton, tumbuhKembangButton, kekerasanAnakButton;

    private ChildViewModel childViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        childViewModel = new ViewModelProvider(requireActivity()).get(ChildViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_educational_article, container, false);

        kekerasanFisikButton = view.findViewById(R.id.buttonKekerasanFisik);
        pertumbuhanPerkembanganButton = view.findViewById(R.id.buttonpertumbuhanperkembangan);
        tumbuhKembangButton = view.findViewById(R.id.buttontumbuhkembang);
        kekerasanAnakButton = view.findViewById(R.id.buttonkekerasananak);

        kekerasanFisikButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_educationalArticleFragment_to_articleChildViolenceFragment);
            }
        });

        pertumbuhanPerkembanganButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_educationalArticleFragment_to_artikelPertumbuhanPerkembanganFragment);
            }
        });

        tumbuhKembangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_educationalArticleFragment_to_artikelTumbuhKembangAnakFragment);
            }
        });

        kekerasanAnakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_educationalArticleFragment_to_artikelKekerasanPadaAnakFragment);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}