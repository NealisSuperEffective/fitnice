package com.nashss.se.fitnice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Set;

@JsonDeserialize(builder = UpdateWorkoutRoutineRequest.Builder.class)
public class UpdateWorkoutRoutineRequest {
    private String routineName;
    private Set<String> tags;
    private String description;
    private List<String> exercises;

    private UpdateWorkoutRoutineRequest(String routineName, Set<String> tags, String description, List<String> exercises) {
        this.routineName = routineName;
        this.tags = tags;
        this.description = description;
        this.exercises = exercises;
    }

    public String getRoutineName() {
        return routineName;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getExercises() {
        return exercises;
    }

    @Override
    public String toString() {
        return "UpdateWorkoutRoutineRequest{" +
                "routineName='" + routineName + '\'' +
                ", tags='" + tags + '\'' +
                ", description='" + description + '\'' +
                ", exercises='" + exercises + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String routineName;
        private Set<String> tags;
        private String description;
        private List<String> exercises;

        public Builder withRoutineName(String routineName) {
            this.routineName = routineName;
            return this;
        }

        public Builder withTags(Set<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withExercises(List<String> exercises) {
            this.exercises = exercises;
            return this;
        }

        public UpdateWorkoutRoutineRequest build() {
            return new UpdateWorkoutRoutineRequest(routineName, tags, description, exercises);
        }
    }
}
