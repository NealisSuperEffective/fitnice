package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.DeleteWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.DeleteWorkoutRoutineResult;

public class DeleteWorkoutRoutineLambda
        extends LambdaActivityRunner<DeleteWorkoutRoutineRequest, DeleteWorkoutRoutineResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteWorkoutRoutineRequest>, LambdaResponse>

{

        @Override
        public LambdaResponse handleRequest (AuthenticatedLambdaRequest < DeleteWorkoutRoutineRequest > input, Context context)
        {
            return super.runActivity(
                    () -> {
                            DeleteWorkoutRoutineRequest unauthenticatedRequest = input.fromBody(DeleteWorkoutRoutineRequest.class);
                            return input.fromUserClaims(claims ->
                                DeleteWorkoutRoutineRequest.builder()
                                .withRoutineName(unauthenticatedRequest.getRoutineName())
                                .build());
        },
                    (request, serviceComponent) ->
                            serviceComponent.provideDeleteWorkoutRoutineActivity().handleRequest(request)
            );
        }
}
