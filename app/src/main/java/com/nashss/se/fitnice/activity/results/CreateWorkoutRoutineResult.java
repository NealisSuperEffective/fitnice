package com.nashss.se.fitnice.activity.results;

import com.nashss.se.fitnice.models.WorkoutRoutineModel;

public class CreateWorkoutRoutineResult {
    private final WorkoutRoutineModel workoutRoutineModel;

    private CreateWorkoutRoutineResult(WorkoutRoutineModel workoutRoutineModel) {
        this.workoutRoutineModel = workoutRoutineModel;
    }

    public WorkoutRoutineModel getWorkoutRoutine() {
        return workoutRoutineModel;
    }

    @Override
    public String toString() {
        return "CreateWorkoutRoutineResult{" +
                "workoutRoutine=" + workoutRoutineModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WorkoutRoutineModel workoutRoutineModel;

        public Builder withWorkoutRoutineModel(WorkoutRoutineModel workoutRoutineModel) {
            this.workoutRoutineModel = workoutRoutineModel;
            return this;
        }

        public CreateWorkoutRoutineResult build() {
            return new CreateWorkoutRoutineResult(workoutRoutineModel);
        }
    }
}
