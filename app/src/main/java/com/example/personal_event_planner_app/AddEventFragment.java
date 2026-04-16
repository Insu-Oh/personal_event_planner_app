package com.example.personal_event_planner_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.personal_event_planner_app.data.AppDatabase;
import com.example.personal_event_planner_app.data.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEventFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextCategory;
    private EditText editTextLocation;
    private TextView textViewDateTime;
    private Button buttonPickDateTime;
    private Button buttonSaveEvent;

    // Stores the selected date and time as milliseconds
    private long selectedDateTimeMillis = 0;

    public AddEventFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        // Connect Java variables to XML views
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextCategory = view.findViewById(R.id.editTextCategory);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        textViewDateTime = view.findViewById(R.id.textViewDateTime);
        buttonPickDateTime = view.findViewById(R.id.buttonPickDateTime);
        buttonSaveEvent = view.findViewById(R.id.buttonSaveEvent);

        // Open the date and time picker when the button is clicked
        buttonPickDateTime.setOnClickListener(v -> showDateTimePicker());

        // Save the event when the save button is clicked
        buttonSaveEvent.setOnClickListener(v -> saveEvent());

        return view;
    }

    // date picker & time picker
    // Open a datapicker first, then a time picker
    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (datePicker, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(Calendar.YEAR, year);
                    selectedCalendar.set(Calendar.MONTH, month);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (timePicker, hourOfDay, minute) -> {
                                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedCalendar.set(Calendar.MINUTE, minute);
                                selectedCalendar.set(Calendar.SECOND, 0);
                                selectedCalendar.set(Calendar.MILLISECOND, 0);

                                selectedDateTimeMillis = selectedCalendar.getTimeInMillis();

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                                textViewDateTime.setText(sdf.format(selectedCalendar.getTime()));
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true
                    );

                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    // Validates user input and inserts a new event into the room database
    private void saveEvent() {
        String title = editTextTitle.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        // Validate that the title is not empty
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(requireContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate that a date and time were selected
        if (selectedDateTimeMillis == 0) {
            Toast.makeText(requireContext(), "Please select a date and time", Toast.LENGTH_SHORT).show();
            return;
        }

        // prevent users from selecting a date in the past
        if (selectedDateTimeMillis < System.currentTimeMillis()) {
            Toast.makeText(requireContext(), "Past dates are not allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create an Event object using the entered values
        Event event = new Event(title, category, location, selectedDateTimeMillis);

        // Insert the event into the Room database
        AppDatabase.getInstance(requireContext()).eventDao().insert(event);

        // Show a confirmation message
        Toast.makeText(requireContext(), "Event saved successfully", Toast.LENGTH_SHORT).show();

        // reset the input form
        editTextTitle.setText("");
        editTextCategory.setText("");
        editTextLocation.setText("");
        textViewDateTime.setText("No date selected");
        selectedDateTimeMillis = 0;
    }
}