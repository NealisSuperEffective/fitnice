package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.CreateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutRoutineResult;

public class CreateWorkoutRoutineLambda
        extends LambdaActivityRunner<CreateWorkoutRoutineRequest, CreateWorkoutRoutineResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateWorkoutRoutineRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateWorkoutRoutineRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateWorkoutRoutineRequest unauthenticatedRequest = input.fromBody(CreateWorkoutRoutineRequest.class);
                    return input.fromUserClaims(claims ->
                                    CreateWorkoutRoutineRequest.builder()
                                        .withRoutineName(unauthenticatedRequest.getRoutineName())
                                        .withTags(unauthenticatedRequest.getTags())
                                        .withDescription(unauthenticatedRequest.getDescription())
                                        .withExercises(unauthenticatedRequest.getExercises())
                                        .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateWorkoutRoutineActivity().handleRequest(request)
        );
    }
}
