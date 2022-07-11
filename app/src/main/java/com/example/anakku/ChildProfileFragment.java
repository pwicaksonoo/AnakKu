package com.example.anakku;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.anakku.viewmodels.ChildViewModel;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class ChildProfileFragment extends Fragment {

    private TextView namaAnakTextView;
    private EditText tinggiEditText;
    private Button hitungButton;
    private EditText beratBadanEditText;
    private TextView kelaminValueTextView;
    private TextView umurValueTextView;

    private ChildViewModel childViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        childViewModel = new ViewModelProvider(requireActivity()).get(ChildViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_profile, container, false);

        namaAnakTextView = view.findViewById(R.id.textViewNamaAnak);
        tinggiEditText = view.findViewById(R.id.editTextTinggi);
        hitungButton = view.findViewById(R.id.buttonHitung);
        beratBadanEditText = view.findViewById(R.id.editTextBeratBadan);
        kelaminValueTextView = view.findViewById(R.id.textViewKelaminValue);
        umurValueTextView = view.findViewById(R.id.textViewUmurValue);

        if(childViewModel.getChild().getValue() != null) {
            namaAnakTextView.setText(childViewModel.getChild().getValue().getNama());
            if(childViewModel.getChild().getValue().getTinggiBadan() != null) tinggiEditText.setText(childViewModel.getChild().getValue().getTinggiBadan().toString());
            kelaminValueTextView.setText((childViewModel.getChild().getValue().getJenisKelamin().equals("L")) ? "Laki-laki" : "Perempuan");
            LocalDate dob = childViewModel.getChild().getValue().getTanggalLahir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            umurValueTextView.setText(String.valueOf(Period.between(dob, LocalDate.now()).getYears()) + " Tahun");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}