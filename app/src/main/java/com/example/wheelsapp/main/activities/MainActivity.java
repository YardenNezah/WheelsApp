package com.example.wheelsapp.main.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wheelsapp.R;
import com.example.wheelsapp.db.local.AppDatabase;
import com.example.wheelsapp.db.local.ThemeDao;
import com.example.wheelsapp.main.fragments.BookingFragment;
import com.example.wheelsapp.main.fragments.BusinessBookingHistory;
import com.example.wheelsapp.main.fragments.BusinessesFragment;
import com.example.wheelsapp.main.fragments.HistoryFragment;

public class MainActivity extends AppCompatActivity {

    public static String BUSINESS = "business";
    public static String CUSTOMER = "customer";
    public static String CURRENT_USER_TYPE = CUSTOMER;

    private LinearLayout tabBarLayout;
    private LinearLayout bookings;
    private LinearLayout history;

    private TextView bookingText, historyText;


    private final View.OnClickListener historyAction = (view) -> {

        Fragment exists = getSupportFragmentManager().findFragmentByTag("historyFragment");
        if (exists != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_main, exists,"historyFragment")
                    .commit();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_main, CURRENT_USER_TYPE.equals(CUSTOMER) ?  new HistoryFragment() : new BusinessBookingHistory(),"historyFragment")
                    .addToBackStack("historyFragment")
                    .commit();
        }
        historyText.setTypeface(historyText.getTypeface(), Typeface.BOLD);
        bookingText.setTypeface(bookingText.getTypeface(), Typeface.NORMAL);

    };

    private final View.OnClickListener bookingsAction = (view) -> {

        Fragment exists = getSupportFragmentManager().findFragmentByTag("bookingsFragment");
        if (exists != null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_main, exists, "bookingsFragment")
                    .commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_main, CURRENT_USER_TYPE.equals(CUSTOMER) ? new BusinessesFragment() : new BookingFragment(),"bookingsFragment")
                    .addToBackStack("bookingsFragment")
                    .commit();
        }
        bookingText.setTypeface(bookingText.getTypeface(), Typeface.BOLD);
        historyText.setTypeface(historyText.getTypeface(), Typeface.NORMAL);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabBarLayout = findViewById(R.id.tab_bar_main);
        bookings = findViewById(R.id.bookings_tab);
        history = findViewById(R.id.history_tab);
        bookingText = findViewById(R.id.bookings_tab_text);
        historyText = findViewById(R.id.history_tab_text);
        bookings.setOnClickListener(bookingsAction);
        history.setOnClickListener(historyAction);


        if (getIntent() != null && getIntent() != null) {
            if (getIntent().getStringExtra("business") != null) {
                CURRENT_USER_TYPE = BUSINESS;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_main, new BookingFragment(),"businessesFragment")
                        .commit();
            } else {
                CURRENT_USER_TYPE = CUSTOMER;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_main, new BusinessesFragment(),"customerFragment")
                        .commit();
            }
        }
    }
}