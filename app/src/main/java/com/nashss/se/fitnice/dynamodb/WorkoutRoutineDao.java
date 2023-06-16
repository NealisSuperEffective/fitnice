package com.nashss.se.fitnice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.exceptions.WorkoutRoutineNotFoundException;
import com.nashss.se.fitnice.metrics.MetricsConstants;
import com.nashss.se.fitnice.metrics.MetricsPublisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Accesses data for a workoutRoutine using {@link WorkoutRoutine} to represent the model in DynamoDB.
 */
@Singleton
public class WorkoutRoutineDao {
    private final DynamoDBMapper dynamoDbMapper;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a WorkoutRoutineDao object.
     *
     * @param dynamoDbMapper   the {@link DynamoDBMapper} used to interact with the itinerary table
     * @param metricsPublisher the {@link MetricsPublisher} used to record metrics.
     */
    @Inject
    public WorkoutRoutineDao(DynamoDBMapper dynamoDbMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDbMapper = dynamoDbMapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Returns the {@link WorkoutRoutine} corresponding to the specified routineName/
     *
     * @param routineName the WorkoutRoutine routineName
     * @return the stored Itinerary,
     */
    public WorkoutRoutine getWorkoutRoutine(String routineName) {
        WorkoutRoutine workoutRoutine = this.dynamoDbMapper.load(WorkoutRoutine.class, routineName);

        if (workoutRoutine == null) {
            metricsPublisher.addCount(MetricsConstants.GETWORKOUTROUTINE_WORKOUTROUTINENOTFOUND_COUNT, 1);
            throw new WorkoutRoutineNotFoundException("Could not find WorkoutRoutine with routineName: " + routineName);
        }
        metricsPublisher.addCount(MetricsConstants.GETWORKOUTROUTINE_WORKOUTROUTINENOTFOUND_COUNT, 0);
        return workoutRoutine;
    }

    /**
     * Saves (creates or updates) the given WorkoutRoutine
     * @param workoutRoutine The WorkoutRoutine to save
     * @return The WorkoutRoutine object that was saved
     */
    public WorkoutRoutine saveWorkoutRoutine(WorkoutRoutine workoutRoutine) {
//        System.out.println("start of saveWorkoutRoutine");
        this.dynamoDbMapper.save(workoutRoutine);
//        System.out.println("end of saveWorkoutRoutine");
        return workoutRoutine;
    }

    public void deleteWorkoutRoutine(WorkoutRoutine workoutRoutine) {
        this.dynamoDbMapper.delete(workoutRoutine);
    }
    /**
     * Perform a search (via a "scan") of the itinerary table for itineraries matching the given criteria.
     *
     * Both "tripName" and "cities" attributes are searched.
     * The criteria are an array of Strings. Each element of the array is search individually.
     * ALL elements of the criteria array must appear in the tripName or the cities (or both).
     * Searches are CASE SENSITIVE.
     *
     * @param criteria an array of String containing search criteria.
     * @return a List of Itinerary objects that match the search criteria.
     */
//    public List<Itinerary> searchItinerary (String[] criteria) {
//        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression();
//
//        if (criteria.length > 0) {
//            Map<String, AttributeValue> valueMap = new HashMap<>();
//            String valueMapNamePrefix = ":c";
//
//            StringBuilder nameFilterExpression = new StringBuilder();
//            StringBuilder citiesFilterExpression = new StringBuilder();
//
//            for (int i = 0; i < criteria.length; i++) {
//                valueMap.put(valueMapNamePrefix + i,
//                        new AttributeValue().withS(criteria[i]));
//                nameFilterExpression.append(
//                        filterExpressionPart("tripName", valueMapNamePrefix, i));
//                citiesFilterExpression.append(
//                        filterExpressionPart("cities", valueMapNamePrefix, i));
//            }
//
//            dynamoDBScanExpression.setExpressionAttributeValues(valueMap);
//            dynamoDBScanExpression.setFilterExpression(
//                    "(" + nameFilterExpression + ") or (" + citiesFilterExpression + ")");
//        }
//
//        return this.dynamoDbMapper.scan(Itinerary.class, dynamoDBScanExpression);
//    }
//
//    private StringBuilder filterExpressionPart(String target, String valueMapNamePrefix, int position) {
//        String possiblyAnd = position == 0 ? "" : "and ";
//        return new StringBuilder()
//                .append(possiblyAnd)
//                .append("contains(")
//                .append(target)
//                .append(", ")
//                .append(valueMapNamePrefix).append(position)
//                .append(") ");
//    }
//    public List<Itinerary> searchItinerariesByEmail(String email) {
//        Itinerary itinerary = new Itinerary();
//        itinerary.setEmail(email);
//        DynamoDBQueryExpression<Itinerary> queryExpression = new DynamoDBQueryExpression<Itinerary>()
//                .withHashKeyValues(itinerary);
//        PaginatedQueryList<Itinerary> itineraries = dynamoDbMapper.query(Itinerary.class, queryExpression);
//        if(null == itineraries||itineraries.size() == 0){
//            throw new ItineraryNotFoundException("Could not find any itineraries associated with this email");
//        }
//        return itineraries;
//    }
}
