import com.nashss.se.fitnice.activity.GetWorkoutActivity;
import com.nashss.se.fitnice.activity.requests.GetWorkoutRequest;
import com.nashss.se.fitnice.activity.results.GetWorkoutResult;
import com.nashss.se.fitnice.dynamodb.WorkoutDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetWorkoutActivityTest {
    @Mock
    private WorkoutDao workoutDao;

    private GetWorkoutActivity getWorkoutActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getWorkoutActivity = new GetWorkoutActivity(workoutDao);
    }

    @Test
    public void handleRequest_savedItineraryFound_returnsWorkoutModelInResult() {
        // GIVEN
        String expectedDate = "expectedDate";
        String expectedName = "expectedName";

        Workout workout = new Workout();
        workout.setDate(expectedDate);
        workout.setName(expectedName);


        when(workoutDao.getWorkout(expectedDate, expectedName)).thenReturn(workout);

        GetWorkoutRequest request = GetWorkoutRequest.builder()
                .withWorkoutDate(expectedDate)
                .withWorkoutName(expectedName)
                .build();

        // WHEN
        GetWorkoutResult result = getWorkoutActivity.handleRequest(request);

        // THEN
        assertEquals(expectedDate, result.getWorkout().getDate());
        assertEquals(expectedName, result.getWorkout().getName());
    }
}
