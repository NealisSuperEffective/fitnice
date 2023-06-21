import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.nashss.se.fitnice.dynamodb.WorkoutDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.exceptions.WorkoutNotFoundException;
import com.nashss.se.fitnice.metrics.MetricsConstants;
import com.nashss.se.fitnice.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class WorkoutDaoTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @Mock
    private MetricsPublisher metricsPublisher;


    private WorkoutDao workoutDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        workoutDao = new WorkoutDao(dynamoDBMapper, metricsPublisher);
    }

    @Test
    public void getWorkout_withDateAndWorkoutName_callsMapperWithPartitionAndSortKey() {
        // GIVEN
        String date = "date";
        String workoutName = "workoutName";
        when(dynamoDBMapper.load(Workout.class, date, workoutName)).thenReturn(new Workout());

        // WHEN
        Workout workout = workoutDao.getWorkout(date, workoutName);

        // THEN
        assertNotNull(workout);
        verify(dynamoDBMapper).load(Workout.class, date, workoutName);
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETWORKOUT_WORKOUTNOTFOUND_COUNT), anyDouble());

    }

    @Test
    public void getWorkout_workoutDateAndWorkoutNameNotFound_throwsWorkoutNotFoundException() {
        // GIVEN
        String nonexistentDate = "NotReal";
        String nonexistentName = "NotReal";
        when(dynamoDBMapper.load(Workout.class, nonexistentDate, nonexistentName)).thenReturn(null);

        // WHEN + THEN
        assertThrows(WorkoutNotFoundException.class, () -> workoutDao.getWorkout(nonexistentDate, nonexistentName));
        verify(metricsPublisher).addCount(eq(MetricsConstants.GETWORKOUT_WORKOUTNOTFOUND_COUNT), anyDouble());
    }

    @Test
    public void saveWorkout_callsMapperWithWorkout() {
        // GIVEN
        Workout workout = new Workout();

        // WHEN
        Workout result = workoutDao.saveWorkout(workout);

        // THEN
        verify(dynamoDBMapper).save(workout);
        assertEquals(workout, result);
    }
}
