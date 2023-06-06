package com.nashss.se.fitnice.activity;
import com.nashss.se.fitnice.activity.requests.UpdateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.UpdateWorkoutRoutineResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.exceptions.InvalidAttributeException;
import com.nashss.se.fitnice.exceptions.InvalidAttributeValueException;
import com.nashss.se.fitnice.exceptions.WorkoutRoutineNotFoundException;
import com.nashss.se.fitnice.metrics.MetricsConstants;
import com.nashss.se.fitnice.metrics.MetricsPublisher;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;
import com.nashss.se.fitnice.utils.ServiceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class UpdateWorkoutRoutineActivity {
    private final Logger log = LogManager.getLogger();
    private final WorkoutRoutineDao workoutRoutineDao;
    private final MetricsPublisher metricsPublisher;

    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param playlistDao      PlaylistDao to access the playlist table.
     * @param metricsPublisher MetricsPublisher to track exception occurrences.
     */
    @Inject
    public UpdateWorkoutRoutineActivity(WorkoutRoutineDao workoutRoutineDao, MetricsPublisher metricsPublisher) {
        this.workoutRoutineDao = workoutRoutineDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the playlist, updating it,
     * and persisting the playlist.
     * <p>
     * It then returns the updated playlist.
     * <p>
     * If the playlist does not exist, this should throw a PlaylistNotFoundException.
     * <p>
     * If the provided playlist name or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the customer ID,
     * this should throw an InvalidAttributeChangeException
     *
     * @param updatePlaylistRequest request object containing the playlist ID, playlist name, and customer ID
     *                              associated with it
     * @return updatePlaylistResult result object containing the API defined {@link PlaylistModel}
     */
    public UpdateWorkoutRoutineResult handleRequest(final UpdateWorkoutRoutineRequest updateWorkoutRoutineRequest) {
        log.info("Received UpdatePlaylistRequest {}", updateWorkoutRoutineRequest);

        WorkoutRoutine workoutRoutine = workoutRoutineDao.getWorkoutRoutine(updateWorkoutRoutineRequest.getRoutineName());
        if (workoutRoutine == null) {
            throw new WorkoutRoutineNotFoundException("Playlist with " + updateWorkoutRoutineRequest.getRoutineName() + " not found.");
        }
        if (!ServiceUtils.isValidString(updateWorkoutRoutineRequest.getRoutineName())) {
            metricsPublisher.addCount(MetricsConstants.UPDATEWORKOUTROUTINE_INVALIDATTRIBUTEVALUE_COUNT, 1);
            throw new InvalidAttributeValueException("Illegal character found in name.");
        }
        metricsPublisher.addCount(MetricsConstants.UPDATEWORKOUTROUTINE_INVALIDATTRIBUTEVALUE_COUNT, 0);
        if (!updateWorkoutRoutineRequest.getRoutineName().equals(workoutRoutine.getRoutineName())) {
            metricsPublisher.addCount(MetricsConstants.UPDATEWORKOUTROUTINE_INVALIDATTRIBUTEVALUE_COUNT, 1);
            throw new InvalidAttributeException("RoutineName does not match playlist RoutineName!");
        }
        metricsPublisher.addCount(MetricsConstants.UPDATEWORKOUTROUTINE_INVALIDATTRIBUTEVALUE_COUNT, 0);
        workoutRoutine.setRoutineName(updateWorkoutRoutineRequest.getRoutineName());
        workoutRoutine.setTags(updateWorkoutRoutineRequest.getTags());
        workoutRoutine.setDescription(updateWorkoutRoutineRequest.getDescription());
        workoutRoutine.setExercises(updateWorkoutRoutineRequest.getExercises());
        WorkoutRoutine updatedNameWorkoutRoutine = workoutRoutineDao.saveWorkoutRoutine(workoutRoutine);
        WorkoutRoutineModel workoutRoutineModel = new VModelConverter().toWorkoutRoutineModel(updatedNameWorkoutRoutine);

        return UpdateWorkoutRoutineResult.builder()
                .withWorkoutRoutine(workoutRoutineModel)
                .build();
    }
}
