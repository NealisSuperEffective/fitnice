package com.nashss.se.fitnice.activity;

import com.nashss.se.fitnice.activity.requests.CreateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutRoutineResult;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.exceptions.InvalidAttributeValueException;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CreateWorkoutRoutineActivity {
    private final Logger log = LogManager.getLogger();
    private final WorkoutRoutineDao workoutRoutineDao;

    /**
     * Instantiates a new CreateWorkoutRoutineActivity object.
     *
     * @param workoutRoutineDao WorkoutRoutineDao to access the workouts table.
     */
    @Inject
    public CreateWorkoutRoutineActivity(WorkoutRoutineDao workoutRoutineDao) {
        this.workoutRoutineDao = workoutRoutineDao;
    }

    /**
     * This method handles the incoming request by persisting a new itinerary
     * with the provided name and customer email from the request.
     * <p>
     * It then returns the newly created itinerary.
     * <p>
     * If the provided name has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createItineraryRequest request object containing the itinerary name and customer email
     *                              associated with it
     * @return createItineraryResult result object containing the API defined {@link ItineraryModel}
     */
    public CreateWorkoutRoutineResult handleRequest(final CreateWorkoutRoutineRequest createWorkoutRoutineRequest) {
        log.info("Received CreateRequest {}", createWorkoutRoutineRequest);

        if (!MusicPlaylistServiceUtils.isValidString(createWorkoutRoutineRequest.getRoutineName())) {
            throw new InvalidAttributeValueException("WorkoutRoutine name [" + createWorkoutRoutineRequest.getName() +
                    "] contains illegal characters");
        }

        List<String> workoutRoutineTags = null;
        if (createWorkoutRoutineRequest.getTags() != null) {
            workoutRoutineTags = new ArrayList<>(createWorkoutRoutineRequest.getTags());
        }
        List<String> workoutRoutineDescription = null;
        if (createWorkoutRoutineRequest.getDescription() != null) {
            workoutRoutineDescription = new ArrayList<>(createWorkoutRoutineRequest.getDescription());
        }
        List<String> workoutRoutineExercises = null;
        if (createWorkoutRoutineRequest.getExercises() != null) {
            workoutRoutineExercises = new ArrayList<>(createWorkoutRoutineRequest.getExercises());
        }

        WorkoutRoutine newWorkoutRoutine = new WorkoutRoutine();

        newWorkoutRoutine.setRoutineName(createWorkoutRoutineRequest.getName());
        newWorkoutRoutine.setTags(workoutRoutineTags);
        newWorkoutRoutine.setDescription(workoutRoutineDescription);
        newWorkoutRoutine.setExercises(workoutRoutineExercises);

        itineraryDao.saveItinerary(newItinerary);

        ItineraryModel itineraryModel = new VModelConverter().toItineraryModel(newItinerary);
        return CreateItineraryResult.builder()
                .withItineraryModel(itineraryModel)
                .build();
    }
}
