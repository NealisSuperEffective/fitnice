package com.nashss.se.fitnice.activity;

import com.nashss.se.fitnice.activity.requests.CreateWorkoutRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.exceptions.InvalidAttributeValueException;
import com.nashss.se.fitnice.models.WorkoutModel;
import com.nashss.se.fitnice.utils.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateWorkoutActivity {
    private final Logger log = LogManager.getLogger();
    private final WorkoutDao workoutDao;

    /**
     * Instantiates a new CreateWorkoutRoutineActivity object.
     *
     * @param workoutRoutineDao WorkoutRoutineDao to access the workouts table.
     */
    @Inject
    public CreateWorkoutActivity(WorkoutDao workoutDao) {
        this.workoutDao = workoutDao;
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
    public CreateWorkoutResult handleRequest(final CreateWorkoutRequest createWorkoutRequest) {
        log.info("Received CreateRequest {}", createWorkoutRequest);

        if (!ServiceUtils.isValidString(createWorkoutRequest.getName())) {
            throw new InvalidAttributeValueException("Workout name [" + createWorkoutRequest.getName() +
                    "] contains illegal characters");
        }

//        String workoutDate = null;
//        if (createWorkoutRequest.getDate() != null) {
//            workoutDate = createWorkoutRequest.getDate();
//        }
//        Set<String> workoutTags = null;
//        if (createWorkoutRequest.getTags() != null) {
//            workoutTags = createWorkoutRequest.getTags();
//        }
//        String workoutDescription = null;
//        if (createWorkoutRequest.getDescription() != null) {
//            workoutDescription = createWorkoutRequest.getDescription();
//        }
//        List<String> workoutExercises = null;
//        if (createWorkoutRequest.getExercises() != null) {
//            workoutExercises = new ArrayList<>(createWorkoutRequest.getExercises());
//        }

        Workout newWorkout = new Workout();

        newWorkout.setDate(createWorkoutRequest.getDate());
        newWorkout.setName(createWorkoutRequest.getName());
        newWorkout.setTags(createWorkoutRequest.getTags());
        newWorkout.setDescription(createWorkoutRequest.getDescription());
        newWorkout.setExercises(createWorkoutRequest.getExercises());

        workoutDao.saveWorkout(newWorkout);

        WorkoutModel workoutModel = new VModelConverter().toWorkoutModel(newWorkout);
        return CreateWorkoutResult.builder()
                .withWorkoutModel(workoutModel)
                .build();
    }
}
