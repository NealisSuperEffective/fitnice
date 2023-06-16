package com.nashss.se.fitnice.activity.results;

import com.nashss.se.fitnice.models.WorkoutRoutineModel;

public class DeleteWorkoutRoutineResult {
    private final WorkoutRoutineModel workoutRoutine;

    private DeleteWorkoutRoutineResult(WorkoutRoutineModel workoutRoutine) {
        this.workoutRoutine = workoutRoutine;
    }
    public WorkoutRoutineModel deleteWorkoutRoutine() {
        return workoutRoutine;
    }
    @Override
    public String toString() {
        return "DeleteWorkoutRoutineResult{" +
                "workoutRoutine=" + workoutRoutine +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static DeleteWorkoutRoutineResult.Builder builder() {
        return new DeleteWorkoutRoutineResult.Builder();
    }
    public static class Builder {
        private WorkoutRoutineModel workoutRoutine;

        public DeleteWorkoutRoutineResult.Builder withWorkoutRoutine(WorkoutRoutineModel workoutRoutine) {
            this.workoutRoutine = workoutRoutine;
            return this;
        }

        public DeleteWorkoutRoutineResult build() {
            return new DeleteWorkoutRoutineResult(workoutRoutine);
        }
    }
}
