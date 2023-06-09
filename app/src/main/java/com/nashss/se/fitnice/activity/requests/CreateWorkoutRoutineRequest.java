package com.nashss.se.fitnice.activity.requests;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;
import java.util.Set;

import static com.nashss.se.fitnice.utils.CollectionUtils.copyToList;

@JsonDeserialize(builder = CreateWorkoutRoutineRequest.Builder.class)
public class CreateWorkoutRoutineRequest {
    private String routineName;
    private Set<String> tags;
    private String description;
    private List<String> exercises;

    private CreateWorkoutRoutineRequest(String routineName, String description, List<String> exercises, Set<String> tags) {
        this.routineName = routineName;
        this.description = description;
        this.tags = tags;
        this.exercises = exercises;
    }
    public String getRoutineName() {
        return routineName;
    }
    public String getDescription() {
        return description;
    }
    public Set<String> getTags() {
        return tags;
    }
    public List<String> getExercises() {return copyToList(exercises);}

    @Override
    public String toString() {
        return "CreateWorkoutRoutineRequest{" +
                "name='" + routineName + '\'' +
                ", tags=" + tags + '\'' +
                ", description=" + description + '\'' +
                ", exercises=" + exercises +
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

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTags(Set<String> tags) {
            this.tags = tags;
            return this;
        }
        public Builder withExercises(List<String> exercises) {
            this.exercises = copyToList(exercises);
            return this;
        }
        public CreateWorkoutRoutineRequest build() {
            return new CreateWorkoutRoutineRequest(routineName, description, exercises, tags);
        }
    }
}
