package com.nashss.se.fitnice.activity.results;

import com.nashss.se.fitnice.models.WorkoutModel;

public class CreateWorkoutResult {
    private final WorkoutModel workoutModel;

    private CreateWorkoutResult(WorkoutModel workoutModel) {
        this.workoutModel = workoutModel;
    }

    public WorkoutModel getWorkout() {
        return workoutModel;
    }

    @Override
    public String toString() {
        return "CreateWorkoutResult{" +
                "workout=" + workoutModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WorkoutModel workoutModel;

        public Builder withWorkoutModel(WorkoutModel workoutModel) {
            this.workoutModel = workoutModel;
            return this;
        }

        public CreateWorkoutResult build() {
            return new CreateWorkoutResult(workoutModel);
        }
    }
}
