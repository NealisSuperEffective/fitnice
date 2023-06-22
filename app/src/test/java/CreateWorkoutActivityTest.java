import com.nashss.se.fitnice.activity.CreateWorkoutActivity;
import com.nashss.se.fitnice.activity.requests.CreateWorkoutRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutResult;
import com.nashss.se.fitnice.dynamodb.WorkoutDao;
import com.nashss.se.fitnice.dynamodb.models.Workout;
import com.nashss.se.fitnice.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class CreateWorkoutActivityTest {
    @Mock
    private WorkoutDao workoutDao;
    @InjectMocks
    private CreateWorkoutActivity createWorkoutActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createWorkoutActivity = new CreateWorkoutActivity(workoutDao);
    }

    @Test
    public void handleRequest_withTags_createsAndSavesWorkoutWithTags() {
        // GIVEN
        String expectedDate = "Date";
        String expectedName = "Name";
        Set<String> expectedTags = new HashSet<>();
        String expectedDescription = "Description";
        List<String> expectedExercises = Arrays.asList("Exercise1", "Exercise2");
        expectedTags.add("Tag1");


        CreateWorkoutRequest request = CreateWorkoutRequest.builder()
                .withDate(expectedDate)
                .withName(expectedName)
                .withTags(expectedTags)
                .withDescription(expectedDescription)
                .withExercises(expectedExercises)
                .build();

        // WHEN
        CreateWorkoutResult result = createWorkoutActivity.handleRequest(request);

        // THEN
        verify(workoutDao).saveWorkout(any(Workout.class));

        assertEquals(expectedDate, result.getWorkout().getDate());
        assertEquals(expectedName, result.getWorkout().getName());
        assertEquals(expectedTags, result.getWorkout().getTags());
        assertEquals(expectedDescription, result.getWorkout().getDescription());
        assertEquals(expectedExercises, result.getWorkout().getExercises());
    }

    @Test
    public void handleRequest_noTags_createsAndSavesWorkoutWithoutTags() {
        // GIVEN
        String expectedName = "expectedName";
        String expectedDate = "expectedDate";

        CreateWorkoutRequest request = CreateWorkoutRequest.builder()
                .withName(expectedName)
                .withDate(expectedDate)
                .build();

        // WHEN
        CreateWorkoutResult result = createWorkoutActivity.handleRequest(request);

        // THEN
        verify(workoutDao).saveWorkout(any(Workout.class));

        assertEquals(expectedName, result.getWorkout().getName());
        assertEquals(expectedDate, result.getWorkout().getDate());
        assertNull(result.getWorkout().getTags());
        assertNull(result.getWorkout().getDescription());
        assertNull(result.getWorkout().getExercises());
    }


    @Test
    public void handleRequest_invalidName_throwsInvalidAttributeValueException() {
        // GIVEN
        CreateWorkoutRequest request = CreateWorkoutRequest.builder()
                .withDate("AllOK")
                .withName("I'm illegal")
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> createWorkoutActivity.handleRequest(request));
    }
}
