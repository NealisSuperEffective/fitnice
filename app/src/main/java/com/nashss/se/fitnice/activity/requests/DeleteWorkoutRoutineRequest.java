package com.nashss.se.fitnice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteWorkoutRoutineRequest.Builder.class)
public class DeleteWorkoutRoutineRequest {
    private final String routineName;

    public DeleteWorkoutRoutineRequest(String routineName) {
        this.routineName = routineName;
    }

    public String getRoutineName() {
        return routineName;
    }

    @Override
    public String toString() {
        return "DeleteWorkoutRoutineRequest{" +
                "routineName='" + routineName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {

        private String routineName;


        public Builder withRoutineName(String routineName) {
            this.routineName = routineName;
            return this;
        }


        public DeleteWorkoutRoutineRequest build() {
            return new DeleteWorkoutRoutineRequest(routineName);
        }
    }
}

