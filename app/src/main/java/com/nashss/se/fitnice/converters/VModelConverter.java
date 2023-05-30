package com.nashss.se.fitnice.converters;

import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.models.WorkoutModel;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;

import java.util.ArrayList;
import java.util.List;

public class VModelConverter {
    /**
     * Converts a workoutRoutine to a WorkoutRoutineModel.
     *
     * @param workoutRoutine the WorkoutRoutine to convert to WorkoutRoutineModel.
     * @return the converted WorkoutRoutineModel
     */
    public WorkoutRoutineModel toWorkoutRoutineModel(WorkoutRoutine workoutRoutine) {

        return WorkoutRoutineModel.builder()
                .withName(workoutRoutine.getRoutineName())
                .withTags(workoutRoutine.getTags())
                .withDescription(workoutRoutine.getDescription())
                .withExercises(workoutRoutine.getExercises())
                .build();
    }
    /**
     * Converts a workout to an WorkoutModel.
     *
     * @param workout the workout  to convert to WorkoutModel.
     * @return The converted WorkoutModel
     */
    public WorkoutModel toWorkoutModel(Workout workout) {
        return WorkoutModel.builder()
                .withDate(workout.getDate())
                .withName(workout.getName())
                .withTags(workout.getTags())
                .withDescription(workout.getDescription())
                .withExercises(workout.getExercises())
                .build();
    }

    /**
     * Converts a list of WorkoutRoutines to a list of WorkoutRoutineModels.
     *
     * @param workoutRoutines The WorkoutRoutine to convert to WorkoutRoutineModels
     * @return The converted list of WorkoutRoutineModels
     */
    public List<WorkoutRoutineModel> toWorkoutRoutineModelList(List<WorkoutRoutine> workoutRoutines) {
        List<WorkoutRoutineModel> workoutRoutineModels = new ArrayList<>();

        for (WorkoutRoutine i : workoutRoutines) {
            workoutRoutineModels.add(toWorkoutRoutineModel(i));
        }

        return workoutRoutineModels;
    }

    /**
     * Converts a list of workouts to a list of WorkoutModels.
     *
     * @param workouts the workout  to convert to WorkoutModels
     * @return The converted list of WorkoutModels
     */public List<WorkoutModel> toWorkoutModelList(List<Workout> workouts) {
        List<WorkoutModel> workoutModels = new ArrayList<>();

        for (Workout w : workouts) {
            workoutModels.add(toWorkoutModel(w));
        }

        return workoutModels;
    }
}
