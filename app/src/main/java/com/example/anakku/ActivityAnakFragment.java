package com.example.anakku;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anakku.adapters.ActivityAdapter;
import com.example.anakku.settings.SharedPref;
import com.example.anakku.viewmodels.ActivityAnakViewModel;

import java.sql.Timestamp;
import java.util.Date;

public class ActivityAnakFragment extends Fragment {

    private ActivityAnakViewModel activityAnakViewModel;
    private RecyclerView activityRecycler;
    private ActivityAdapter activityAdapter;
    private RelativeLayout loadingPanel;
    private EditText newActivityEditText;
    private Button addActivityButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAnakViewModel = new ViewModelProvider(this).get(ActivityAnakViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_anak, container, false);

        loadingPanel = view.findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.VISIBLE);

        activityRecycler = view.findViewById(R.id.recyclerViewActivityAnak);
        activityRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        activityRecycler.setHasFixedSize(true);
        activityAnakViewModel.getActivityAnakListMutableLiveData().observe(getViewLifecycleOwner(), activityAnak -> {
            loadingPanel.setVisibility(View.GONE);
            activityAdapter = new ActivityAdapter(activityAnak);
            activityRecycler.setAdapter(activityAdapter);
            activityAdapter.notifyDataSetChanged();

            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (200 * scale + 0.5f);
            ViewGroup.LayoutParams recyclerParam = activityRecycler.getLayoutParams();
            recyclerParam.width = ViewGroup.LayoutParams.MATCH_PARENT;
            recyclerParam.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            if(activityAnak.size() >= 5) recyclerParam.height = pixels;
            activityRecycler.setLayoutParams(recyclerParam);
        });

        newActivityEditText = view.findViewById(R.id.editTextNewActivity);
        addActivityButton = view.findViewById(R.id.buttonAddActivity);
        addActivityButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                if(newActivityEditText.getText().length() == 0) {
                    Toast.makeText(getContext(), "Aktifitas baru tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    Date currentDate = new Date();
                    Timestamp currentTimeStamp = new Timestamp(currentDate.getTime());
                    activityAnakViewModel.addNewActivity(newActivityEditText.getText().toString(), currentTimeStamp);
                    newActivityEditText.setText("");
                    Toast.makeText(getContext(), "Aktifitas baru berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
                try {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Exception e) {

                }
                newActivityEditText.clearFocus();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(), SharedPref.read(SharedPref.ACTIVE_CHILD, "NULL"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        activityAnakViewModel.stopActivityAnakListener();
    }
}