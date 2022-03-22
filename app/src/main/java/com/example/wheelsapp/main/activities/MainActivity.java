package com.example.wheelsapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.wheelsapp.R;
import com.example.wheelsapp.main.fragments.BookingFragment;
import com.example.wheelsapp.main.fragments.BusinessesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_main);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.main_nav_graph);
        if(getIntent()!=null && getIntent()!=null) {
            if(getIntent().getStringExtra("customer") !=null)
                navGraph.setStartDestination(R.id.bookingFragment);
            else if(getIntent().getStringExtra("business")!=null)
                navGraph.setStartDestination(R.id.businessesFragment);
        }else navGraph.setStartDestination(R.id.businessesFragment);
            navController.setGraph(navGraph);

    }
}