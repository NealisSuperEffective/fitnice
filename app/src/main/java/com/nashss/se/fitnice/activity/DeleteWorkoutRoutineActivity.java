package com.nashss.se.fitnice.activity;

import com.nashss.se.fitnice.activity.requests.DeleteWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.DeleteWorkoutRoutineResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteWorkoutRoutineActivity {
    private final Logger log = LogManager.getLogger();
    private final WorkoutRoutineDao workoutRoutineDao;

    @Inject
    public DeleteWorkoutRoutineActivity(WorkoutRoutineDao workoutRoutineDao) {this.workoutRoutineDao = workoutRoutineDao;}

    public DeleteWorkoutRoutineResult handleRequest(final DeleteWorkoutRoutineRequest request) {
        log.info("Received DeleteWorkoutRoutineRequest {}", request);
        String requestRoutineName = request.getRoutineName();

        WorkoutRoutine workoutRoutine = new WorkoutRoutine();
        WorkoutRoutine workoutRoutine1 = workoutRoutineDao.getWorkoutRoutine(requestRoutineName);
        workoutRoutine.setRoutineName(requestRoutineName);
        workoutRoutineDao.deleteWorkoutRoutine(workoutRoutine);
        WorkoutRoutineModel workoutRoutineModel = new VModelConverter().toWorkoutRoutineModel(workoutRoutine1);

        return DeleteWorkoutRoutineResult.builder()
                .withWorkoutRoutine(workoutRoutineModel)
                .build();
    }
}
