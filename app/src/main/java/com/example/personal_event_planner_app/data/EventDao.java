package com.example.personal_event_planner_app.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
 * DAO (Data Access Object) defines methods to interact with the database.
 * It provides operations such as insert, update, delete, and query.
 */
@Dao
public interface EventDao {

    // Insert a new event into the database
    @Insert
    void insert(Event event);

     // Update an existing event.
    @Update
    void update(Event event);

    // Delete a specific event
    @Delete
    void delete(Event event);

    // Retrieve all events sorted by date in ascending order
    // So the ealriest event appear first
    @Query("SELECT * FROM events ORDER BY dateTime ASC")
    List<Event> getAllEventsSortedByDate();
}