package com.nashss.se.fitnice.converters;

import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;

import java.util.ArrayList;
import java.util.List;

public class VModelConverter {
    /**
     * Converts an itinerary to an ItineraryModels.
     *
     * @param itinerary the Itinerary to convert to ItineraryModel.
     * @return the converted ItineraryModel
     */
    public WorkoutRoutineModel toWorkoutRoutineModel(WorkoutRoutine workoutRoutine) {

        return WorkoutRoutineModel.builder()
                .withName(workoutRoutine.getRoutineName())
                .withTags(workoutRoutine.getTags())
                .withDescription(workoutRoutine.getDescription())
                .withExercises(workoutRoutine.getExercises())
                .build();
    }
    /**
     * Converts an activity to an ActivityModel.
     *
     * @param activity the activity  to convert to ActivityModel.
     * @return The converted ActivityModel
     */
    public ActivityModel toActivityModel(Activity activity) {
        return ActivityModel.builder()
                .withName(activity.getName())
                .withCityCountry(activity.getCityCountry())
                .withAddress(activity.getAddress())
                .withTypeOfActivity(activity.getTYPE_OF_ACTIVITY())
                .withKidFriendly(activity.getKidFriendly())
                .withWeatherPermitting(activity.getWeatherPermitting())
                .build();
    }

    /**
     * Converts a list of Itineraries to a list of ItineraryModels.
     *
     * @param itineraries The Itinerary to convert to ItineraryModels
     * @return The converted list of ItineraryModels
     */
    public List<WorkoutRoutineModel> toWorkoutRoutineModelList(List<WorkoutRoutine> workoutRoutines) {
        List<WorkoutRoutineModel> workoutRoutineModels = new ArrayList<>();

        for (WorkoutRoutine i : workoutRoutines) {
            workoutRoutineModels.add(toWorkoutRoutineModel(i));
        }

        return workoutRoutineModels;
    }

    /**
     * Converts a list of activities to a list of ActivityModels.
     *
     * @param activities the activity  to convert to ActivityModels
     * @return The converted list of ActivityModels
     */public List<ActivityModel> toActivityModelList(List<Activity> activities) {
        List<ActivityModel> activitiesModels = new ArrayList<>();

        for (Activity a : activities) {
            activitiesModels.add(toActivityModel(a));
        }

        return activitiesModels;
    }
}
