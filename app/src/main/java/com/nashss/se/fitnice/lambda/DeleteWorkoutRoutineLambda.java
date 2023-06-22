package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.DeleteWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.requests.GetWorkoutRequest;
import com.nashss.se.fitnice.activity.results.DeleteWorkoutRoutineResult;

public class DeleteWorkoutRoutineLambda
        extends LambdaActivityRunner<DeleteWorkoutRoutineRequest, DeleteWorkoutRoutineResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteWorkoutRoutineRequest>, LambdaResponse>

{
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteWorkoutRoutineRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        DeleteWorkoutRoutineRequest.builder()
                                .withRoutineName(path.get("routineName"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteWorkoutRoutineActivity().handleRequest(request)
        );
    }
}