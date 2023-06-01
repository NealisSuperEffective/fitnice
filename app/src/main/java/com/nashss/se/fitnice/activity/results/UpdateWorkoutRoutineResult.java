package com.nashss.se.fitnice.activity.results;

import com.nashss.se.fitnice.models.WorkoutRoutineModel;

public class UpdateWorkoutRoutineResult {
    private final WorkoutRoutineModel workoutRoutine;

    private UpdateWorkoutRoutineResult(WorkoutRoutineModel workoutRoutine) {
        this.workoutRoutine = workoutRoutine;
    }

    public WorkoutRoutineModel getWorkoutRoutine() {
        return workoutRoutine;
    }

    @Override
    public String toString() {
        return "UpdateWorkoutRoutineResult{" +
                "workoutRoutine=" + workoutRoutine +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private WorkoutRoutineModel workoutRoutine;

        public Builder withWorkoutRoutine(WorkoutRoutineModel workoutRoutine) {
            this.workoutRoutine = workoutRoutine;
            return this;
        }

        public UpdateWorkoutRoutineResult build() {
            return new UpdateWorkoutRoutineResult(workoutRoutine);
        }
    }
}
