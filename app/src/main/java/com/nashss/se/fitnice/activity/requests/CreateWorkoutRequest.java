package com.nashss.se.fitnice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Set;

import static com.nashss.se.fitnice.utils.CollectionUtils.copyToList;

public class CreateWorkoutRequest {
    private String date;
    private String name;
    private Set<String> tags;
    private String description;
    private List<String> exercises;

    private CreateWorkoutRequest(String date, String name, String description, List<String> exercises, Set<String> tags) {
        this.date = date;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.exercises = exercises;
    }
    public String getName() {
        return name;
    }
    public String getDate() {return date; }
    public String getDescription() {
        return description;
    }
    public Set<String> getTags() {
        return tags;
    }
    public List<String> getExercises() {return copyToList(exercises);}

    @Override
    public String toString() {
        return "CreateWorkoutRequest{" +
                "Workout name='" + name + '\'' +
                ", date=" + date + '\'' +
                ", tags=" + tags + '\'' +
                ", description=" + description + '\'' +
                ", exercises=" + exercises +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateWorkoutRequest.Builder builder() {
        return new CreateWorkoutRequest.Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String date;
        private String name;
        private Set<String> tags;
        private String description;
        private List<String> exercises;

        public CreateWorkoutRequest.Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public CreateWorkoutRequest.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CreateWorkoutRequest.Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public CreateWorkoutRequest.Builder withTags(Set<String> tags) {
            this.tags = tags;
            return this;
        }
        public CreateWorkoutRequest.Builder withExercises(List<String> exercises) {
            this.exercises = copyToList(exercises);
            return this;
        }
        public CreateWorkoutRequest build() {
            return new CreateWorkoutRequest(date, name, description, exercises, tags);
        }
    }
}
