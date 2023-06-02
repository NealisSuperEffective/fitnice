package com.nashss.se.fitnice.activity;

import com.nashss.se.fitnice.activity.requests.CreateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutRoutineResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.exceptions.InvalidAttributeValueException;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            throw new InvalidAttributeValueException("WorkoutRoutine name [" + createWorkoutRoutineRequest.getRoutineName() +
                    "] contains illegal characters");
        }

        Set<String> workoutRoutineTags = null;
        if (createWorkoutRoutineRequest.getTags() != null) {
            workoutRoutineTags = createWorkoutRoutineRequest.getTags();
        }
        String workoutRoutineDescription = null;
        if (createWorkoutRoutineRequest.getDescription() != null) {
            workoutRoutineDescription = createWorkoutRoutineRequest.getDescription();
        }
        List<String> workoutRoutineExercises = null;
        if (createWorkoutRoutineRequest.getExercises() != null) {
            workoutRoutineExercises = new ArrayList<>(createWorkoutRoutineRequest.getExercises());
        }

        WorkoutRoutine newWorkoutRoutine = new WorkoutRoutine();

        newWorkoutRoutine.setRoutineName(createWorkoutRoutineRequest.getRoutineName());
        newWorkoutRoutine.setTags(workoutRoutineTags);
        newWorkoutRoutine.setDescription(workoutRoutineDescription);
        newWorkoutRoutine.setExercises(workoutRoutineExercises);

        workoutRoutineDao.saveWorkoutRoutine(newWorkoutRoutine);

        WorkoutRoutineModel workoutRoutineModel = new VModelConverter().toWorkoutRoutineModel(newWorkoutRoutine);
        return CreateWorkoutRoutineResult.builder()
                .withWorkoutRoutineModel(workoutRoutineModel)
                .build();
    }
}
