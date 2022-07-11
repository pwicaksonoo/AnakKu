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

import com.example.anakku.viewmodels.RegisterViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterFragment extends Fragment {
    private static final String TAG = "debuk";
    private EditText namaEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Spinner jenisKelaminSpinner;
    private EditText tanggalLahirEditText;
    private DatePickerDialog picker;
    private EditText jumlahAnakEditText;
    private Button registerButton;
    private Button loginButton;

    private FirebaseAuth mAuth;

    private RegisterViewModel registerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getUserMutableLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
//                Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_registerChildFragment);
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Toast.makeText(getContext(), "Logged in as " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
//            Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_registerChildFragment);
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        namaEditText = view.findViewById(R.id.editTextNama);
        emailEditText = view.findViewById(R.id.editTextEmail);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        confirmPasswordEditText = view.findViewById(R.id.editTextConfirmPassword);
        jenisKelaminSpinner = view.findViewById(R.id.spinnerJenisKelamin);
        tanggalLahirEditText = view.findViewById(R.id.editTextTanggalLahir);
        jumlahAnakEditText = view.findViewById(R.id.editTextJumlahAnak);
        registerButton = view.findViewById(R.id.buttonRegister);
        loginButton = view.findViewById(R.id.buttonLogin);

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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                String nama = namaEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String jenisKelamin = (jenisKelaminSpinner.getSelectedItemPosition() == 0) ? "L" : "P";
                Date tanggalLahir;
                try {
                    tanggalLahir = new SimpleDateFormat("dd/MM/yyyy").parse(tanggalLahirEditText.getText().toString());
                } catch (Exception e) {
                    tanggalLahir = null;
                }

                Integer jumlahAnak;
                try {
                    jumlahAnak = Integer.parseInt(jumlahAnakEditText.getText().toString());
                } catch (Exception e) {
                    jumlahAnak = 0;
                }

                if (nama.length() > 0) {
                    if (email.length() > 0 && password.length() > 0) {
                        if (password.equals(confirmPassword)) {
                            if (tanggalLahir != null) {
                                if (jumlahAnak >= 0) {
                                    registerViewModel.register(nama, email, password, jenisKelamin, tanggalLahir, jumlahAnak);
                                    Toast.makeText(getContext(), "Registerasi berhasil!", Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(getContext(), "Jumlah anak minimal adalah 0", Toast.LENGTH_SHORT).show();
                            } else Toast.makeText(getContext(), "Tanggal lahir tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(getContext(), "Password dan Konfirm Password harus sama!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getContext(), "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getContext(), "Nama wajib diisi!", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        return view;
    }
}