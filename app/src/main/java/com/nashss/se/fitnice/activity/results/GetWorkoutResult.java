package com.nashss.se.fitnice.activity.results;

import com.nashss.se.fitnice.models.WorkoutModel;

public class GetWorkoutResult {
    private final WorkoutModel workout;

    private GetWorkoutResult(WorkoutModel workout) {
        this.workout = workout;
    }
    public WorkoutModel getWorkout() {
        return workout;
    }
    @Override
    public String toString() {
        return "GetWorkoutResult{" +
                "workout=" + workout +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static GetWorkoutResult.Builder builder() {
        return new GetWorkoutResult.Builder();
    }
    public static class Builder {
        private WorkoutModel workout;

        public GetWorkoutResult.Builder withWorkout(WorkoutModel workout) {
            this.workout = workout;
            return this;
        }

        public GetWorkoutResult build() {
            return new GetWorkoutResult(workout);
        }
    }
}
