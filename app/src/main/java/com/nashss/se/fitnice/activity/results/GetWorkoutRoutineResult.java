package com.nashss.se.fitnice.activity.results;

import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;

public class GetWorkoutRoutineResult {
    private final WorkoutRoutineModel workoutRoutine;

    private GetWorkoutRoutineResult(WorkoutRoutineModel workoutRoutine) {
        this.workoutRoutine = workoutRoutine;
    }
    public WorkoutRoutineModel getWorkoutRoutine() {
        return workoutRoutine;
    }
    @Override
    public String toString() {
        return "GetWorkoutRoutineResult{" +
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

        public GetWorkoutRoutineResult build() {
            return new GetWorkoutRoutineResult(workoutRoutine);
        }
    }
}
