package com.example.personal_event_planner_app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/*
 * Main Room Database class.
 * This class creates and manages the database instance.
 *
 * entities: list of tables included in this database
 * version: database version (increase when schema changes)
 */
@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    // Provides access to EventDao
    // Room automatically generates the implementation
    public abstract EventDao eventDao();

    // singleton instance of the database
    // ensures only one database object exists in the app.
    private static AppDatabase instance;


    // Returns the database instance
    // if it does not exist, create a new one
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "event_database"   // Name of the database file
                    )
                    /*
                     * If schema changes without migration,
                     * the database will be cleared and recreated.
                     */
                    .allowMainThreadQueries() // allow db operations on the main thread for simplicity
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}