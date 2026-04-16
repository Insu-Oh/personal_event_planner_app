package com.example.personal_event_planner_app.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


// This class represents a single Event entity in the Room database.
@Entity(tableName = "events")
public class Event {

    // Unique ID for each event
    @PrimaryKey(autoGenerate = true) // Automatically generated when a new event is inserted
    private int id;

    // Title of the event
    private String title;

    // Category of the event (e.g., Work, Social, Travel)
    private String category;

    // Location of the event
    private String location;

    // Date and time stored as a timestamp (long).
    private long dateTime;

    /*
     * Constructor used when creating a new Event.
     * The id is not included because it is auto-generated.
     */
    public Event(String title, String category, String location, long dateTime) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.dateTime = dateTime;
    }

    // Getter and Setter method
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}