# Personal Event Planner App

## Overview
This project is an Android application developed for Task 4.1.  
The purpose of the app is to help users manage personal events such as meetings, trips, and appointments.

The app allows users to create, view, update, and delete events.  
All event data is stored locally on the device using Room Database.

## Features
The app includes the following features:

1. Add a new event  
   Users can add an event with a title, category, location, and date/time.

2. View all events  
   All saved events are displayed in a list and sorted by date.

3. Update an event  
   Users can select an existing event and edit its details.

4. Delete an event  
   Users can remove an event by long pressing on it.

5. Data persistence  
   The app uses Room Database so the data remains saved even after the app is closed or restarted.

6. Input validation  
   The app checks that the title is not empty, the date is selected, and past dates are not allowed.

## Tools and Technologies Used
This project was developed using:

- Java
- Android Studio
- Room Database
- Fragments
- ListView
- Bottom Navigation

## Project Structure
The main parts of the app are:

- AddEventFragment: used to add new events
- EventListFragment: used to display saved events
- EditEventFragment: used to update event details
- AppDatabase / EventDao / Event: used for Room Database

## How to Run the Project
1. Download or clone the project from GitHub.
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or Android device.