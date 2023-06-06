package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.UpdateWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.UpdateWorkoutRoutineResult;

public class UpdateWorkoutRoutineLambda
        extends LambdaActivityRunner<UpdateWorkoutRoutineRequest, UpdateWorkoutRoutineResult>
        implements RequestHandler<LambdaRequest<UpdateWorkoutRoutineRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<UpdateWorkoutRoutineRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        UpdateWorkoutRoutineRequest.builder()
                                .withRoutineName(path.get("routineName"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateWorkoutRoutineActivity().handleRequest(request)
        );
    }
}
