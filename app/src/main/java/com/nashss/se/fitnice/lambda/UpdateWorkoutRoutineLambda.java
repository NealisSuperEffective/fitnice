package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.UpdateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.UpdateWorkoutRoutineResult;

public class UpdateWorkoutRoutineLambda
        extends LambdaActivityRunner<UpdateWorkoutRoutineRequest, UpdateWorkoutRoutineResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateWorkoutRoutineRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateWorkoutRoutineRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateWorkoutRoutineRequest unauthenticatedRequest = input.fromBody(UpdateWorkoutRoutineRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateWorkoutRoutineRequest.builder()
                                    .withRoutineName(unauthenticatedRequest.getRoutineName())
                                    .withTags(unauthenticatedRequest.getTags())
                                    .withDescription(unauthenticatedRequest.getDescription())
                                    .withExercises(unauthenticatedRequest.getExercises())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateWorkoutRoutineActivity().handleRequest(request)
        );
    }
}
