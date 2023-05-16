package com.nashss.se.fitnice.dynamodb.models;

import VacanzaLambda.src.main.java.musicplaylistservice.converters.ActivitiesListConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a record in the itineraries table.
 */
@DynamoDBTable(tableName = "itineraries")
public class  Itinerary {
    private String tripName;
    private String email;
    private List<String> cities;
    private List<Activity> activities;
    private List<String> users;
    private List<String> tags;
    @DynamoDBRangeKey(attributeName = "tripName")
    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName= tripName;
    }
    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email= email;
    }

    @DynamoDBAttribute(attributeName = "cities")
    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    @DynamoDBTypeConverted(converter = ActivitiesListConverter.class)
    @DynamoDBAttribute(attributeName = "activities")
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities= activities;
    }

    @DynamoDBAttribute(attributeName = "users")
    public List<String> getUsers() {
        if (null == users) {
            return null;
        }
        return new ArrayList<>(users);
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    /**
     * Returns the set of tags associated with this Itinerary, null if there are none.
     *
     * @return Set of tags for this itinerary.
     */
    @DynamoDBAttribute(attributeName = "tags")
    public List<String> getTags() {

        if (null == tags) {
            return null;
        }

        return new ArrayList<>(tags);
    }

    /**
     * Sets the tags for this Itinerary as a copy of input, or null if input is null.
     *
     * @param tags Set of tags for this itinerary
     */
    public void setTags(List<String> tags) {
        // see comment in getTags()
        if (null == tags) {
            this.tags = null;
        } else {
            this.tags = new ArrayList<>(tags);
        }
        this.tags = tags;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return Objects.equals(tripName, itinerary.tripName) && Objects.equals(email, itinerary.email) && Objects.equals(cities, itinerary.cities) && Objects.equals(activities, itinerary.activities) && Objects.equals(users, itinerary.users);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tripName, email, cities, activities, users);
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "tripName='" + tripName + '\'' +
                ", email='" + email + '\'' +
                ", cities=" + cities +
                ", activities=" + activities +
                ", users=" + users +
                '}';
    }
}
