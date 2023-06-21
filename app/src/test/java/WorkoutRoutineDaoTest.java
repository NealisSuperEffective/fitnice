import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.exceptions.WorkoutRoutineNotFoundException;
import com.nashss.se.fitnice.metrics.MetricsConstants;
import com.nashss.se.fitnice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WorkoutRoutineDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;


    private WorkoutRoutineDao workoutRoutineDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        workoutRoutineDao = new WorkoutRoutineDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getWorkout_withDateAndWorkoutName_callsMapperWithPartitionAndSortKey() {
        // GIVEN
        String routineName = "routineName";
        when(dynamoDBMapper.load(WorkoutRoutine.class, routineName)).thenReturn(new WorkoutRoutine());

        // WHEN
        WorkoutRoutine workoutRoutine = workoutRoutineDao.getWorkoutRoutine(routineName);

        // THEN
        assertNotNull(workoutRoutine);
        verify(dynamoDBMapper).load(WorkoutRoutine.class, routineName);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETWORKOUTROUTINE_WORKOUTROUTINENOTFOUND_COUNT), anyDouble());

    }

    @Test
    public void getWorkout_workoutDateAndWorkoutNameNotFound_throwsWorkoutNotFoundException() {
        // GIVEN
        String nonexistentName = "NotReal";
        when(dynamoDBMapper.load(WorkoutRoutine.class, nonexistentName)).thenReturn(null);

        // WHEN + THEN
        assertThrows(WorkoutRoutineNotFoundException.class, () -> workoutRoutineDao.getWorkoutRoutine(nonexistentName));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETWORKOUTROUTINE_WORKOUTROUTINENOTFOUND_COUNT), anyDouble());
    }

    @Test
    public void saveWorkout_callsMapperWithWorkout() {
        // GIVEN
        WorkoutRoutine workoutRoutine = new WorkoutRoutine();

        // WHEN
        WorkoutRoutine result = workoutRoutineDao.saveWorkoutRoutine(workoutRoutine);

        // THEN
        verify(dynamoDBMapper).save(workoutRoutine);
        assertEquals(workoutRoutine, result);
    }
}
