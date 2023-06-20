package com.nashss.se.fitnice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a record in the itineraries table.
 */
@DynamoDBTable(tableName = "workoutRoutines")
public class  WorkoutRoutine {
    private String routineName;
    private Set<String> tags;
    private String description;
    private List<String> exercises;

    @DynamoDBHashKey(attributeName = "routineName")
    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName= routineName;
    }


    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //@DynamoDBTypeConverted(converter = ActivitiesListConverter.class)
    @DynamoDBAttribute(attributeName = "exercises")
    public List<String> getExercises() {
        return exercises;
    }

    public void setExercises(List<String> exercises) {
        this.exercises = exercises;
    }


    /**
     * Returns the set of tags associated with this Itinerary, null if there are none.
     *
     * @return Set of tags for this itinerary.
     */
    @DynamoDBAttribute(attributeName = "tags")
    public Set<String> getTags() {

        if (null == tags) {
            return null;
        }

        return tags;
    }

    /**
     * Sets the tags for this Itinerary as a copy of input, or null if input is null.
     *
     * @param tags Set of tags for this itinerary
     */
    public void setTags(Set<String> tags) {
        // see comment in getTags()
        if (null == tags) {
            this.tags = null;
        } else {
            this.tags = tags;
        }
        this.tags = tags;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutRoutine workoutRoutine = (WorkoutRoutine) o;
        return Objects.equals(routineName, workoutRoutine.routineName) && Objects.equals(tags, workoutRoutine.tags) && Objects.equals(description, workoutRoutine.description) && Objects.equals(exercises, workoutRoutine.exercises);
    }
    @Override
    public int hashCode() {
        return Objects.hash(routineName, tags, description, exercises);
    }

    @Override
    public String toString() {
        return "WorkoutRoutine{" +
                "name='" + routineName + '\'' +
                ", tags='" + tags + '\'' +
                ", description=" + description +
                ", exercises=" + exercises +
                '}';
    }
}
