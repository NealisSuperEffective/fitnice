import com.nashss.se.fitnice.activity.CreateWorkoutRoutineActivity;
import com.nashss.se.fitnice.activity.requests.CreateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutRoutineResult;
import com.nashss.se.fitnice.dynamodb.WorkoutRoutineDao;
import com.nashss.se.fitnice.dynamodb.models.WorkoutRoutine;
import com.nashss.se.fitnice.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateWorkoutRoutineActivityTest {
    @Mock
    private WorkoutRoutineDao workoutRoutineDao;
    @InjectMocks
    private CreateWorkoutRoutineActivity createWorkoutRoutineActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createWorkoutRoutineActivity = new CreateWorkoutRoutineActivity(workoutRoutineDao);
    }

    @Test
    public void handleRequest_withTags_createsAndSavesWorkoutRoutineWithTags() {
        // GIVEN
        String expectedName = "Name";
        Set<String> expectedTags = new HashSet<>();
        String expectedDescription = "Description";
        List<String> expectedExercises = Arrays.asList("Exercise1", "Exercise2");
        expectedTags.add("Tag1");


        CreateWorkoutRoutineRequest request = CreateWorkoutRoutineRequest.builder()
                .withRoutineName(expectedName)
                .withTags(expectedTags)
                .withDescription(expectedDescription)
                .withExercises(expectedExercises)
                .build();

        // WHEN
        CreateWorkoutRoutineResult result = createWorkoutRoutineActivity.handleRequest(request);

        // THEN
        verify(workoutRoutineDao).saveWorkoutRoutine(any(WorkoutRoutine.class));

        assertEquals(expectedName, result.getWorkoutRoutine().getName());
        assertEquals(expectedTags, result.getWorkoutRoutine().getTags());
        assertEquals(expectedDescription, result.getWorkoutRoutine().getDescription());
        assertEquals(expectedExercises, result.getWorkoutRoutine().getExercises());
    }

    @Test
    public void handleRequest_noTags_createsAndSavesWorkoutRoutineWithoutTags() {
        // GIVEN
        String expectedName = "expectedName";

        CreateWorkoutRoutineRequest request = CreateWorkoutRoutineRequest.builder()
                .withRoutineName(expectedName)
                .build();

        // WHEN
        CreateWorkoutRoutineResult result = createWorkoutRoutineActivity.handleRequest(request);

        // THEN
        verify(workoutRoutineDao).saveWorkoutRoutine(any(WorkoutRoutine.class));

        assertEquals(expectedName, result.getWorkoutRoutine().getName());
        assertNull(result.getWorkoutRoutine().getTags());
        assertNull(result.getWorkoutRoutine().getDescription());
        assertNull(result.getWorkoutRoutine().getExercises());
    }

    @Test
    public void handleRequest_invalidName_throwsInvalidAttributeValueException() {
        // GIVEN
        CreateWorkoutRoutineRequest request = CreateWorkoutRoutineRequest.builder()
                .withRoutineName("I'm illegal")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createWorkoutRoutineActivity.handleRequest(request));
    }

}
