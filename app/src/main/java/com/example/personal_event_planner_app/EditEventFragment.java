package com.example.personal_event_planner_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personal_event_planner_app.data.AppDatabase;
import com.example.personal_event_planner_app.data.Event;

public class EditEventFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextCategory;
    private EditText editTextLocation;
    private Button buttonUpdate;

    private int eventId;
    private long dateTime;

    public EditEventFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_event, container, false);

        // Connect Java variables to XML views
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextCategory = view.findViewById(R.id.editTextCategory);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);

        // Read values passed from EventListFragment
        Bundle args = getArguments();
        if (args != null) {
            eventId = args.getInt("id");
            editTextTitle.setText(args.getString("title"));
            editTextCategory.setText(args.getString("category"));
            editTextLocation.setText(args.getString("location"));
            dateTime = args.getLong("dateTime");
        }

        // Update the event when the button is clicked
        buttonUpdate.setOnClickListener(v -> updateEvent());

        return view;
    }

    // validate the input and update the selected event in the database
    private void updateEvent() {
        String title = editTextTitle.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        // validate if the title is not empty
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // create an updated Event object
        Event event = new Event(title, category, location, dateTime);
        event.setId(eventId); // keep the original ID so Room updates instead of inserting

        // Update the event in the database
        AppDatabase.getInstance(requireContext()).eventDao().update(event);

        // Show confirmation message
        Toast.makeText(requireContext(), "Event updated", Toast.LENGTH_SHORT).show();

        // Return to the previous screen
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
