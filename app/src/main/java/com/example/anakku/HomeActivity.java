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

public class HomeActivity extends AppCompatActivity {

    private Button buttonNavHome;
    private Button buttonNavActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    }
}