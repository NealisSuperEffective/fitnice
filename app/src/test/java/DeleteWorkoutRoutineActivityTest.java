import com.nashss.se.fitnice.activity.DeleteWorkoutRoutineActivity;
import com.nashss.se.fitnice.activity.requests.DeleteWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.DeleteWorkoutRoutineResult;
import com.nashss.se.fitnice.converters.VModelConverter;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.models.WorkoutRoutineModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeleteWorkoutRoutineActivityTest {
    @Mock
    private WorkoutRoutineDao workoutRoutineDao;

    private DeleteWorkoutRoutineActivity deleteWorkoutRoutineActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteWorkoutRoutineActivity = new DeleteWorkoutRoutineActivity(workoutRoutineDao);
    }

    @Test
    void handleRequest_ValidRequest_ReturnsResult() {
        // Mock input request
        String expectedRoutineName = "SampleRoutine";
        DeleteWorkoutRoutineRequest request = new DeleteWorkoutRoutineRequest(expectedRoutineName);

        // Mock the returned WorkoutRoutine from the DAO
        WorkoutRoutine mockWorkoutRoutine = new WorkoutRoutine();
        mockWorkoutRoutine.setRoutineName("SampleRoutine");
        when(workoutRoutineDao.getWorkoutRoutine("SampleRoutine")).thenReturn(mockWorkoutRoutine);

        // Perform the delete operation
        DeleteWorkoutRoutineResult result = deleteWorkoutRoutineActivity.handleRequest(request);

        // Verify the interactions with the DAO
        verify(workoutRoutineDao).getWorkoutRoutine("SampleRoutine");
        verify(workoutRoutineDao).deleteWorkoutRoutine(mockWorkoutRoutine);

        // Assert the result
        WorkoutRoutineModel workoutRoutineModel = result.getWorkoutRoutine();
        assertEquals("SampleRoutine", workoutRoutineModel.getName());
        // Add additional assertions for other fields if needed
    }
}
