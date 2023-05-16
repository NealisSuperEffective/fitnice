package com.nashss.se.fitnice.dynamodb;

import VacanzaLambda.src.main.java.musicplaylistservice.dynamodb.models.Activity;
import VacanzaLambda.src.main.java.musicplaylistservice.exceptions.ActivityNotFoundException;
import VacanzaLambda.src.main.java.musicplaylistservice.metrics.MetricsConstants;
import VacanzaLambda.src.main.java.musicplaylistservice.metrics.MetricsPublisher;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Accesses data for an activity using {@link Activity} to represent the model in DynamoDB.
 */
@Singleton
public class ActivityDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;
    /**
     * Instantiates an ActivityDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the activities table
     */
    @Inject
    public ActivityDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
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
    public Activity getActivity(String cityCountry, String name) {
        Activity activity = dynamoDbMapper.load(Activity.class, cityCountry, name);
        if (null == activity) {
            metricsPublisher.addCount(MetricsConstants.GETACTIVITY_ACTIVITYNOTFOUND_COUNT, 1);
            throw new ActivityNotFoundException(
                    String.format("Could not find Activity with cityCountry '%s' and name %s", cityCountry, name));
        }
        metricsPublisher.addCount(MetricsConstants.GETACTIVITY_ACTIVITYNOTFOUND_COUNT, 0);
        return activity;

    }
    /**
     * Saves (creates or updates) the given activities
     * @param activity The activity to save
     * @return The Activity object that was saved
     */
    public Activity saveActivity(Activity activity) {
        this.dynamoDbMapper.save(activity);
        return activity;
    }

    /**
     * Searches the activities table by cityCountry
     * @param cityCountry The cityCountry to search for
     * @return activityList the activities that were found
     */
    public List<Activity> getActivitiesByCityCountry(String cityCountry) {
        Activity activity = new Activity();
        activity.setCityCountry(cityCountry);

        DynamoDBQueryExpression<Activity> queryExpression = new DynamoDBQueryExpression<Activity>()
                .withHashKeyValues(activity);
        PaginatedQueryList<Activity> activityList = dynamoDbMapper.query(Activity.class, queryExpression);

        if (null == activityList||activityList.size() == 0) {
            metricsPublisher.addCount(MetricsConstants.SEARCHACTIVITIES_ACTIVITYNOTFOUND_COUNT, 1);
            throw new ActivityNotFoundException(
                    String.format("Could not find Activity with cityCountry '%s'", cityCountry));
        }
        metricsPublisher.addCount(MetricsConstants.SEARCHACTIVITIES_ACTIVITYNOTFOUND_COUNT, 0);
        return activityList;
    }
}


