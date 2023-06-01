package com.nashss.se.fitnice.activity.requests;

public class GetWorkoutRoutineRequest {
    private final String routineName;

    private GetWorkoutRoutineRequest(String routineName) {
        this.routineName = routineName;
    }

    public String getRoutineName() {
        return routineName;
    }

    @Override
    public String toString() {
        return "GetWorkoutRoutineRequest{" +
                "routineName='" + routineName + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String routineName;


        public Builder withRoutineName(String routineName) {
            this.routineName = routineName;
            return this;
        }


        public GetWorkoutRoutineRequest build() {
            return new GetWorkoutRoutineRequest(routineName);
        }
    }
}
