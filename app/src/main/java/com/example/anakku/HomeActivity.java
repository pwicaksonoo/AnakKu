package com.example.anakku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.anakku.settings.SharedPref;

public class HomeActivity extends AppCompatActivity {

    private Button buttonNavActivity;
    private Button buttonNavLocation;
    private Button buttonNavHome;
    private Button buttonNavChild;
    private Button buttonNavProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.init(getApplicationContext());
        setContentView(R.layout.activity_home);

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_navHostFragment);
        final NavController navController = navHostFragment.getNavController();

        buttonNavActivity = findViewById(R.id.nav_activity_button);
        buttonNavActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof ActivityAnakFragment)) {
//                    navController.popBackStack(R.id.activityAnakFragment, true);
                    navController.navigate(R.id.activityAnakFragment);
                }
            }
        });

        buttonNavLocation = findViewById(R.id.nav_location_button);

        buttonNavHome = findViewById(R.id.nav_home_button);
        buttonNavHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof HomeFragment)) {
                    navController.popBackStack(R.id.homeFragment, true);
                    navController.navigate(R.id.homeFragment);
                }
            }
        });

        buttonNavChild = findViewById(R.id.nav_child_button);
        buttonNavChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof PickChildFragment)) {
                    navController.navigate(R.id.pickChildFragment);
                }
            }
        });

        buttonNavProfile = findViewById(R.id.nav_profile_button);
        buttonNavProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof ProfileUserFragment)) {
                    navController.navigate(R.id.profileUserFragment);
                }
            }
        });
    }
}