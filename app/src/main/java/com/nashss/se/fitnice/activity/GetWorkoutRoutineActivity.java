package com.nashss.se.fitnice.activity;

import com.nashss.se.fitnice.activity.requests.GetWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.GetWorkoutRoutineResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetWorkoutRoutineActivity {
    private final Logger log = LogManager.getLogger();
    private final WorkoutRoutineDao workoutRoutineDao;

    /**
     * Instantiates a new GetItineraryActivity object.
     *
     * @param itineraryDao ItineraryDao to access the itinerary table.
     */
    @Inject
    public GetWorkoutRoutineActivity (WorkoutRoutineDao workoutRoutineDao) {
        this.workoutRoutineDao = workoutRoutineDao;
    }

    /**
     * This method handles the incoming request by retrieving the  itinerary from the database.
     * <p>
     * It then returns the itinerary
     *
     * @param getItineraryRequest request object containing the itinerary email and tripName
     * @return getItineraryResult result object containing the API defined {@link ItineraryModel}
     */
    public GetWorkoutRoutineResult handleRequest(final GetWorkoutRoutineRequest getWorkoutRoutineRequest) {
        log.info("Received GetItineraryRequest {}", getWorkoutRoutineRequest);
        String routineName = getWorkoutRoutineRequest.getRoutineName();
        WorkoutRoutine workoutRoutine = workoutRoutineDao.getWorkoutRoutine(routineName);
        WorkoutRoutineModel workoutRoutineModel = new VModelConverter().toWorkoutRoutineModel(workoutRoutine);

        return GetWorkoutRoutineResult.builder()
                .withWorkoutRoutine(workoutRoutineModel)
                .build();
    }
}
