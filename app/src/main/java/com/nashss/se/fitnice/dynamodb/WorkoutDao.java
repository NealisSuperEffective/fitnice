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
 * Accesses data for an activity using {@link Activity} to represent the model in DynamoDB.
 */
@Singleton
public class WorkoutDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    /**
     * Instantiates an ActivityDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the activities table
     */
    @Inject
    public WorkoutDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }
    /**
     * Retrieves an Activity by cityCountry and name.
     *
     * If not found, throws ActivityNotFoundException.
     *
     * @param cityCountry The cityCountry to look up
     * @param name The activity name to look up
     * @return The corresponding Activity if found
     */
    public Workout getWorkout(String date, String name) {
        Workout workout = dynamoDbMapper.load(Workout.class, name, date);
        if (null == workout) {
            metricsPublisher.addCount(MetricsConstants.GETWORKOUT_WORKOUTNOTFOUND_COUNT, 1);
            throw new WorkoutNotFoundException(
                    String.format("Could not find Workout with name '%s' and date %s", name, date));
        }
        metricsPublisher.addCount(MetricsConstants.GETWORKOUT_WORKOUTNOTFOUND_COUNT, 0);
        return workout;

    }
    /**
     * Saves (creates or updates) the given activities
     * @param activity The activity to save
     * @return The Activity object that was saved
     */
    public Workout saveWorkout(Workout workout) {
        this.dynamoDbMapper.save(workout);
        return workout;
    }

    /**
     * Searches the activities table by cityCountry
     * @param cityCountry The cityCountry to search for
     * @return activityList the activities that were found
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

