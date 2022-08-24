package com.example.anakku;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.viewmodels.ChildProfileViewModel;
import com.example.anakku.viewmodels.ChildViewModel;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class ChildProfileFragment extends Fragment {

    private RelativeLayout loadingPanel;
    private TextView namaAnakTextView;
    private EditText tinggiEditText;
    private Button hitungButton;
    private EditText beratBadanEditText;
    private TextView kelaminValueTextView;
    private TextView umurValueTextView;

    private ChildProfileViewModel childProfileViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        childProfileViewModel = new ViewModelProvider(this).get(ChildProfileViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_profile, container, false);

        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.VISIBLE);

        namaAnakTextView = view.findViewById(R.id.textViewNamaAnak);
        tinggiEditText = view.findViewById(R.id.editTextTinggi);
        hitungButton = view.findViewById(R.id.buttonHitung);
        beratBadanEditText = view.findViewById(R.id.editTextBeratBadan);
        kelaminValueTextView = view.findViewById(R.id.textViewKelaminValue);
        umurValueTextView = view.findViewById(R.id.textViewUmurValue);

        childProfileViewModel.getChild().observe(getViewLifecycleOwner(), child -> {
            loadingPanel.setVisibility(View.GONE);
            namaAnakTextView.setText(child.getNama());
            if(child.getTinggiBadan() != null) tinggiEditText.setText(child.getTinggiBadan().toString());
            kelaminValueTextView.setText((child.getJenisKelamin().equals("L")) ? "Laki-laki" : "Perempuan");
            LocalDate dob = child.getTanggalLahir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(Period.between(dob, LocalDate.now()).getYears() == 0) umurValueTextView.setText(String.valueOf(Period.between(dob, LocalDate.now()).getMonths()) + " Bulan");
            else umurValueTextView.setText(String.valueOf(Period.between(dob, LocalDate.now()).getYears()) + " Tahun");

            Double beratIdeal = 0.0;
            Integer umurBulan = Period.between(dob, LocalDate.now()).getMonths();
            Integer umurTahun = Period.between(dob, LocalDate.now()).getYears();
            Boolean isMale = (child.getJenisKelamin().equals("L")) ? true : false;
            if(umurTahun == 0) {
                if(umurBulan < 3) {
                    if(isMale) {
                        switch (umurBulan) {
                            case 0:
                                beratIdeal = 3.3;
                                break;
                            case 1:
                                beratIdeal = 4.5;
                                break;
                            case 2:
                                beratIdeal = 5.6;
                                break;
                        }
                    }
                    else {
                        switch (umurBulan) {
                            case 0:
                                beratIdeal = 3.2;
                                break;
                            case 1:
                                beratIdeal = 4.2;
                                break;
                            case 2:
                                beratIdeal = 5.1;
                                break;
                        }
                    }
                }
                else beratIdeal = (umurBulan + 9.0) / 2;
            } else {
                if(umurTahun >= 1 && umurTahun <=6 ) beratIdeal = (umurTahun * 2.0) + 8;
                else if(umurTahun >= 7 && umurTahun <= 12) beratIdeal = ((umurTahun * 7.0) - 5) / 2;
            }

            beratBadanEditText.setText(beratIdeal.toString());
            beratBadanEditText.setInputType(InputType.TYPE_NULL);
            Toast.makeText(getContext(), "Halo " + child.getNama(), Toast.LENGTH_SHORT).show();
        });

        if(false && childProfileViewModel.getChild().getValue() != null) {
            namaAnakTextView.setText(childProfileViewModel.getChild().getValue().getNama());
            if(childProfileViewModel.getChild().getValue().getTinggiBadan() != null) tinggiEditText.setText(childProfileViewModel.getChild().getValue().getTinggiBadan().toString());
            kelaminValueTextView.setText((childProfileViewModel.getChild().getValue().getJenisKelamin().equals("L")) ? "Laki-laki" : "Perempuan");
            LocalDate dob = childProfileViewModel.getChild().getValue().getTanggalLahir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(Period.between(dob, LocalDate.now()).getYears() == 0) umurValueTextView.setText(String.valueOf(Period.between(dob, LocalDate.now()).getMonths()) + " Bulan");
            else umurValueTextView.setText(String.valueOf(Period.between(dob, LocalDate.now()).getYears()) + " Tahun");

            Double beratIdeal = 0.0;
            Integer umurBulan = Period.between(dob, LocalDate.now()).getMonths();
            Integer umurTahun = Period.between(dob, LocalDate.now()).getYears();
            Boolean isMale = (childProfileViewModel.getChild().getValue().getJenisKelamin().equals("L")) ? true : false;
            if(umurTahun == 0) {
                if(umurBulan < 3) {
                    if(isMale) {
                        switch (umurBulan) {
                            case 0:
                                beratIdeal = 3.3;
                                break;
                            case 1:
                                beratIdeal = 4.5;
                                break;
                            case 2:
                                beratIdeal = 5.6;
                                break;
                        }
                    }
                    else {
                        switch (umurBulan) {
                            case 0:
                                beratIdeal = 3.2;
                                break;
                            case 1:
                                beratIdeal = 4.2;
                                break;
                            case 2:
                                beratIdeal = 5.1;
                                break;
                        }
                    }
                }
                else beratIdeal = (umurBulan + 9.0) / 2;
            } else {
                if(umurTahun >= 1 && umurTahun <=6 ) beratIdeal = (umurTahun * 2.0) + 8;
                else if(umurTahun >= 7 && umurTahun <= 12) beratIdeal = ((umurTahun * 7.0) - 5) / 2;
            }

            beratBadanEditText.setText(beratIdeal.toString());
            beratBadanEditText.setInputType(InputType.TYPE_NULL);
            Toast.makeText(getContext(), "Halo", Toast.LENGTH_SHORT).show();
        }

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tinggiEditText.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), "Input tinggi harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    Integer tinggi = Integer.parseInt(tinggiEditText.getText().toString());
                    Double genderFactor = (childProfileViewModel.getChild().getValue().getJenisKelamin().equals("L")) ? 0.1 : 0.15;
                    Double beratIdeal = (tinggi-100) - ((tinggi-100) * genderFactor);
                    beratBadanEditText.setText(beratIdeal.toString());
                    Toast.makeText(getContext(), "" + beratIdeal.toString(), Toast.LENGTH_SHORT).show();
                }
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
        childProfileViewModel.stopChildProfileListener();
    }
}