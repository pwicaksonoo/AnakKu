package com.example.anakku;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.anakku.viewmodels.ChildViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterChildFragment extends Fragment {

    private EditText namaEditText;
    private Spinner jenisKelaminSpinner;
    private EditText tanggalLahirEditText;
    private DatePickerDialog picker;
    private Button registerChildButton;

    private FirebaseAuth mAuth;
    private ChildViewModel childViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        childViewModel = new ViewModelProvider(requireActivity()).get(ChildViewModel.class);
        childViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser == null) {
                logoutAndBack();
            }
        });
        childViewModel.getChild().observe(this, child -> {
            if (child != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_registerChildFragment_to_homeFragment);
            }
        });

        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                logoutAndBack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            Toast.makeText(getContext(), "Session Expired!", Toast.LENGTH_SHORT).show();
            logoutAndBack();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_child, container, false);

        namaEditText = view.findViewById(R.id.editTextNama);
        jenisKelaminSpinner = view.findViewById(R.id.spinnerJenisKelamin);
        tanggalLahirEditText = view.findViewById(R.id.editTextTanggalLahir);
        registerChildButton = view.findViewById(R.id.buttonRegisterAnak);

        ArrayAdapter<CharSequence> jenisKelaminAdapter = ArrayAdapter.createFromResource(getContext(), R.array.jenis_kelamin, android.R.layout.simple_spinner_dropdown_item);
        jenisKelaminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jenisKelaminSpinner.setAdapter(jenisKelaminAdapter);

        tanggalLahirEditText.setInputType(InputType.TYPE_NULL);
        tanggalLahirEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                tanggalLahirEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        registerChildButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                String nama = namaEditText.getText().toString();
                String jenisKelamin = (jenisKelaminSpinner.getSelectedItemPosition() == 0) ? "L" : "P";
                Date tanggalLahir;
                try {
                    tanggalLahir = new SimpleDateFormat("dd/MM/yyyy").parse(tanggalLahirEditText.getText().toString());
                } catch (Exception e) {
                    tanggalLahir = null;
                }

                String toastText = null;

                if (nama.length() > 0) {
                    if (tanggalLahir != null) {
                        childViewModel.registerChild(nama, jenisKelamin, tanggalLahir);
                    } else toastText = "Tanggal lahir tidak boleh kosong!";
                } else toastText = "Nama anak tidak boleh kosong!";

                if(toastText != null) {
                    Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getContext(), childViewModel.getChild().getValue().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void logoutAndBack() {
        childViewModel.logOut();
        Toast.makeText(getContext(), "Logged out! ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
//        Navigation.findNavController(getView()).navigate(R.id.action_registerChildFragment_to_loginFragment);
    }
}