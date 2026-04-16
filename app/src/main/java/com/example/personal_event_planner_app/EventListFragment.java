package com.example.personal_event_planner_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personal_event_planner_app.data.AppDatabase;
import com.example.personal_event_planner_app.data.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventListFragment extends Fragment {

    private ListView listViewEvents;
    private TextView textViewEmpty;

    public EventListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        // Connect Java variables to XML views
        listViewEvents = view.findViewById(R.id.listViewEvents);
        textViewEmpty = view.findViewById(R.id.textViewEmpty);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Reload the event list every time the fragment becomes visible again
        loadEvents();
    }

    // loads all events from the database
    // format them as readable text,
    // and display them in the ListView
    private void loadEvents() {
        List<Event> eventList = AppDatabase.getInstance(requireContext())
                .eventDao()
                .getAllEventsSortedByDate();

        List<String> displayList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (Event event : eventList) {
            String eventText =
                    "Title: " + event.getTitle()
                            + "\nCategory: " + event.getCategory()
                            + "\nLocation: " + event.getLocation()
                            + "\nDate: " + sdf.format(event.getDateTime());

            displayList.add(eventText);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                displayList
        );

        listViewEvents.setAdapter(adapter);

        // Show empty message if there are no saved events
        if (displayList.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
            listViewEvents.setVisibility(View.GONE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
            listViewEvents.setVisibility(View.VISIBLE);
        }
    }
}