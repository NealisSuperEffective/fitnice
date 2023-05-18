# Project Design Doc - Fitnice Design Doc


## 1. Problem Statement

I'd like an application where I can record workout exercises and their descriptions, use them to build workout routines, and use those routines as a
template for workouts that can be saved in an effort to track progress per workout and per exercise. The application should allow me to add, remove, search for,
and edit exercises that I have found and want to do. The application should allow me to build workout sessions using any number of exercises that I
have saved, and these workout sessions should also be able to be added, removed, searched for, and edited. Finally, the application should allow me to
add, remove, and search for workouts that contain exercise information such as weight used, time spent, and repititions completed in order to track progress
between workouts.

## 2. Top Questions to Resolve in Review

1. Should an Exercise table exist, or should exercise choices for a workout be preloaded?
2. Should workouts be saved using a ZonedDateTime for easy organization beyond just workout names?
3. How much freedom should be afforded to the application's user? More freedom for input lends to ambiguity using
the application, but allows for more customer freedom when designing exercises.

## 3. Use Cases

U1. As a customer, I want to create a new workout routine with a name, and a list of exercises.

U2. As a customer, I want to create a new workout where I can log exercises and their attributes (repetitions, weight, duration)

U3. As a customer, I want to retrieve a workout by name or by date.

U4. As a customer, I want to update my workout routine.

U5. As a customer, I want to add exercises to a workout.

U5. As a customer, I want to delete a workout routine.

U6. As a customer, I want to view all my workout routines.

U7. As a customer, I want to view all my workouts.

U8. As a customer, I want to use my workout routines as a template for a workout.

## 4. Project Scope

### 4.1. In Scope

* Creating, retrieving, and updating a workout plan.
* Creating and retrieving a workout.
* Adding to and retrieving a saved workout's exercises and their attributes.
* Retrieving all workout plans a customer has created.
* Retrieving all workouts a customer has created.

### 4.2. Out of Scope

* Updating exercise tags and attributes
* Exercise data display (tables, graphs, etc.)
* Being able to add tags individually or in some nicer way than a comma
  separated list of strings on the website
* Sharing workout plans and workouts between users
* Using a previous workout as a template for a new one
* Reorganizing workout exercise order
* Removing workouts
* Using a previous workout as a template for a new workout

## 5. Proposed Architecture Overview

This initial iteration will provide the minimum lovable product (MLP) including
creating, retrieving, and updating a workout routine, as well as adding to and
retrieving a workout's list of exercises and attributes.

We will use API Gateway and Lambda to create nine endpoints (`GetWorkoutRoutine`,
`CreateWorkoutRoutine`, `UpdateWorkoutRoutine`,`RemoveWorkoutRoutine`, `CreateWorkout`, and `GetWorkout`) 
that will handle the creation, update, and retrieval of
workout routine and workouts to satisfy our requirements.

Workout routine and workouts will be stored in DynamoDB. For simpler exercise list retrieval, we
will store the list of exercises in a given workout routine or workout directly in the respective table.

Fitnice will also provide a web interface for users to manage
their workout routines and workouts. A main page providing a list view of all of their workout routines and workouts,
another page will let them create a new workout routine or workout respectively and link off to pages 
per-workout to view metadata and exercises.

## 6. API

### 6.1. Public Models

```
// WorkoutRoutineModel

String name;
String description;
List<String> tags;
List<Enum> exercises
```

```
// WorkoutModel

String date;
String name;
String description
List<Enum> exercises;
List<String> tags;
```

### 6.1. Get Workout for Date Endpoint

* Accepts `GET` requests to `/workouts/:date`
* Accepts a workout date and returns the corresponding workout.
    * If the given workout date is not found, will throw a
      `WorkoutNotFoundException`

### 6.2. Create Workout Endpoint

* Accepts `POST` requests to `/workouts`
* Accepts data to create a new workout with a provided date, name, description, exercises
* and an optional list of tags. 
* Returns the new workout
* For security concerns, we will validate the provided workout name does not
  contain any invalid characters: `" ' \`
    * If the workout name contains any of the invalid characters, will throw an
      `InvalidAttributeValueException`.

### 6.3. Get Workout for Name Endpoint

* Accepts `GET` requests to `/workouts/:name`
* Accepts a workout name and returns a list of WorkoutModels created under that name.
    * If the given customer has not created any workouts, an empty list will be returned

![Client sends submit playlist update form to Website Playlist page. Website
playlist page sends an update request to UpdatePlaylistActivity.
UpdatePlaylistActivity saves updates to the playlists
database.](images/example_design_document/UpdatePlaylistSD.png)

[//]: # (### 6.4. Add Exercise To Workout Endpoint)

[//]: # ()
[//]: # (* Accepts `POST` requests to `/workouts/:date/exercises`)

[//]: # (* Accepts a workout date and an exercise to be added. The exercise is specified by the)

[//]: # (  exercise's name.)

[//]: # (    * If the workout is not found, will throw a `WorkoutNotFoundException`)

[//]: # (    * If the given exercise doesn't exist, or if the given exercise name does)

[//]: # (      not exist, will throw an `ExerciseNotFoundException`)

[//]: # (* By default, will insert the new exercise to the end of the workout)

![Client submits the add song form to the Website Add Song page. The website
add song page sends an add song request to the AddSongToPlaylistActivity. The
AddSongToPlaylistActivity save the updated playlist song list in the playlists
database.](images/example_design_document/AddSongSD.png)

![The client visits the playlist page of the Website Playlist. The Website
playlist page sends a get song request to the GetPlaylistSongsActivity. The
GetPlaylistSongsActivity calls the playlists database to load the playlist. The
playlists database returns the playlist item to the GetPlaylistSongsActivity.
The GetPlaylistSongsActivity returns a List<SongModel> to the Website Playlist
page. The Website playlist page presents a list of songs to the
client.](images/example_design_document/GetPlaylistSD.png)

### 6.5. Get Workout Routine Endpoint

* Accepts `GET` requests to `/workoutroutines/:name`
* Accepts a workout routine name and returns the corresponding workout routine.
    * If the given workout routine name is not found, will throw a
      `WorkoutRoutineNotFoundException`

### 6.6. Create Workout Routine Endpoint

* Accepts `POST` requests to `/workoutroutines`
* Accepts data to create a new workout routine with a provided name, description, exercise list,
* and an optional list of tags.
* Returns the new workout routine
* For security concerns, we will validate the provided workout routine name does not
  contain any invalid characters: `" ' \`
    * If the exercise name contains any of the invalid characters, will throw an
      `InvalidAttributeValueException`.

### 6.7. Update Workout Routine Endpoint

* Accepts `PUT` requests to `/workoutroutines/:name`
* Accepts data to update a workout routine including a name, an updated description, an updated exercise list
* and the updated tags associated with the workout routine. Returns the updated workout routine.
    * If the workout routine name is not found, will throw a `WorkoutRoutineNotFoundException`
* For security concerns, we will validate the provided workout routine name does not
  contain invalid characters: `" ' \`
    * If the exercise name contains invalid characters, will throw an
      `InvalidAttributeValueException`

## 7. Tables

### 7.1. `workoutroutines`

```
name // partition key, string
tags // stringSet
description // string
exerciseList // list
```

### 7.2. `workouts`

```
date // partition key, number
name // sort key, string
tags // stringSet
description // string
exerciseList // list
```



