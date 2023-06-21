import com.nashss.se.fitnice.activity.GetWorkoutActivity;
import com.nashss.se.fitnice.activity.GetWorkoutRoutineActivity;
import com.nashss.se.fitnice.activity.requests.GetWorkoutRequest;
import com.nashss.se.fitnice.activity.requests.GetWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.GetWorkoutResult;
import com.nashss.se.fitnice.activity.results.GetWorkoutRoutineResult;
import com.nashss.se.fitnice.dynamodb.WorkoutDao;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetWorkoutRoutineActivityTest {
    @Mock
    private WorkoutRoutineDao workoutRoutineDao;

    private GetWorkoutRoutineActivity getWorkoutRoutineActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getWorkoutRoutineActivity = new GetWorkoutRoutineActivity(workoutRoutineDao);
    }

    @Test
    public void handleRequest_savedItineraryFound_returnsWorkoutRoutineModelInResult() {
        // GIVEN
        String expectedName = "expectedName";

        WorkoutRoutine workoutRoutine = new WorkoutRoutine();
        workoutRoutine.setRoutineName(expectedName);


        when(workoutRoutineDao.getWorkoutRoutine(expectedName)).thenReturn(workoutRoutine);

        GetWorkoutRoutineRequest request = GetWorkoutRoutineRequest.builder()
                .withRoutineName(expectedName)
                .build();

        // WHEN
        GetWorkoutRoutineResult result = getWorkoutRoutineActivity.handleRequest(request);

        // THEN
        assertEquals(expectedName, result.getWorkoutRoutine().getName());
    }
}
