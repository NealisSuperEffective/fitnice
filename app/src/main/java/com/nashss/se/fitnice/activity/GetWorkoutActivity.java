package com.nashss.se.fitnice.activity;

import com.nashss.se.fitnice.activity.requests.GetWorkoutRequest;
import com.nashss.se.fitnice.activity.results.GetWorkoutResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.models.WorkoutModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetWorkoutActivity {
    private final Logger log = LogManager.getLogger();
    private final WorkoutDao workoutDao;

    /**
     * Instantiates a new GetItineraryActivity object.
     *
     * @param itineraryDao ItineraryDao to access the itinerary table.
     */
    @Inject
    public GetWorkoutActivity (WorkoutDao workoutDao) {
        this.workoutDao = workoutDao;
    }

    /**
     * This method handles the incoming request by retrieving the  itinerary from the database.
     * <p>
     * It then returns the itinerary
     *
     * @param getItineraryRequest request object containing the itinerary email and tripName
     * @return getItineraryResult result object containing the API defined {@link ItineraryModel}
     */
    public GetWorkoutResult handleRequest(final GetWorkoutRequest getWorkoutRequest) {
        log.info("Received GetWorkoutRequest {}", getWorkoutRequest);
        String date = getWorkoutRequest.getDate();
        Workout workout = workoutDao.getWorkout(date);
        WorkoutModel workoutModel = new VModelConverter().toWorkoutModel(workout);

        return GetWorkoutResult.builder()
                .withWorkout(workoutModel)
                .build();
    }
}
