package com.nashss.se.fitnice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a record in the activities table.
 */
@DynamoDBTable(tableName = "workouts")
public class Workout{
    private String date;
    private String name;

    private Set<String> tags;

    private String description;

    private List<String> exercises;

    @DynamoDBHashKey(attributeName = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "tags")
    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
//    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "exercises")
    public List<String> getExercises() {
        return exercises;
    }

    public void setExercises(List<String> exercises) {
        this.exercises = exercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(date, workout.date) && Objects.equals(name, workout.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name);
    }
    @Override
    public String toString() {
        return "Workout{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", tags='" + tags + '\'' +
                ", description=" + description +
                ", exercises=" + exercises +
                '}';
    }
}
