package com.nashss.se.fitnice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.exceptions.WorkoutNotFoundException;
import com.nashss.se.fitnice.metrics.MetricsConstants;
import com.nashss.se.fitnice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Accesses data for a workout using {@link Workout} to represent the model in DynamoDB.
 */
@Singleton
public class WorkoutDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    /**
     * Instantiates an WorkoutDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the workouts table
     */
    @Inject
    public WorkoutDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }
    /**
     * Retrieves a Workout by date and name.
     * <p>
     * If not found, throws WorkoutNotFoundException.
     *
     * @param date The date to look up
     * @return The corresponding Workout if found
     */
    public Workout getWorkout(String date) {
        Workout workout = dynamoDbMapper.load(Workout.class, date);
        if (null == workout) {
            metricsPublisher.addCount(MetricsConstants.GETWORKOUT_WORKOUTNOTFOUND_COUNT, 1);
            throw new WorkoutNotFoundException(
                    String.format("Could not find Workout with date %s", date));
        }
        metricsPublisher.addCount(MetricsConstants.GETWORKOUT_WORKOUTNOTFOUND_COUNT, 0);
        return workout;

    }
    /**
     * Saves (creates or updates) the given workout
     * @param workout The Workout to save
     * @return The Workout object that was saved
     */
    public Workout saveWorkout(Workout workout) {
        this.dynamoDbMapper.save(workout);
        return workout;
    }

    /**
     * Searches the workouts table by name
     * @param name The name to search for
     * @return workoutList the workouts that were found
     */
    public List<Workout> getWorkoutByName(String name) {
        Workout workout = new Workout();
        workout.setName(name);

        DynamoDBQueryExpression<Workout> queryExpression = new DynamoDBQueryExpression<Workout>()
                .withHashKeyValues(workout);
        PaginatedQueryList<Workout> workoutList = dynamoDbMapper.query(Workout.class, queryExpression);

        if (null == workoutList||workoutList.size() == 0) {
            metricsPublisher.addCount(MetricsConstants.SEARCHWORKOUTS_WORKOUTNOTFOUND_COUNT, 1);
            throw new WorkoutNotFoundException(
                    String.format("Could not find Workout with name '%s'", name));
        }
        metricsPublisher.addCount(MetricsConstants.SEARCHWORKOUTS_WORKOUTNOTFOUND_COUNT, 0);
        return workoutList;
    }
}


