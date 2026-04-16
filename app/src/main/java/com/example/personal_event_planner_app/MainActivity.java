package com.example.personal_event_planner_app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// MainActivity: Acts as the container (shell) that holds fragments and the bottom navigation
public class MainActivity extends AppCompatActivity {

    // Reference to BottomNavigationView (bottom tab bar)
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Connect XML BottomNavigationView to Java variable
        bottomNav = findViewById(R.id.bottom_nav);

        // Load default fragment only when activity is first created
        if (savedInstanceState == null) {
            // Initially show EventListFragment in the fragment container
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new EventListFragment())
                    .commit();
        }

        // Handle bottom navigation item clicks
        bottomNav.setOnItemSelectedListener(item -> {
            // If Event List tab is selected, show EventListFragment
            if (item.getItemId() == R.id.menu_event_list) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new EventListFragment())
                        .commit();
                return true;
            }
            // If Add Event tab is selected, show AddEventFragment
            else if (item.getItemId() == R.id.menu_add_event) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddEventFragment())
                        .commit();
                return true;
            }
            return false;
        });
    }
}