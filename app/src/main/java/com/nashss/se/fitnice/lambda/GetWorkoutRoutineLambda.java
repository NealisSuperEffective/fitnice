package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.GetWorkoutRoutineRequest;
import com.nashss.se.fitnice.activity.results.GetWorkoutRoutineResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWorkoutRoutineLambda
        extends LambdaActivityRunner<GetWorkoutRoutineRequest, GetWorkoutRoutineResult>
        implements RequestHandler<LambdaRequest<GetWorkoutRoutineRequest>, LambdaResponse> {

        private final Logger log = LogManager.getLogger();

    @Override
     public LambdaResponse handleRequest(LambdaRequest<GetWorkoutRoutineRequest> input, Context context) {
            log.info("handleRequest");
            return super.runActivity(
                    () -> input.fromPath(path ->
                            GetWorkoutRoutineRequest.builder()
                                    .withRoutineName(path.get("routineName"))
                                    .build()),
                    (request, serviceComponent) ->
                            serviceComponent.provideGetWorkoutRoutineActivity().handleRequest(request)
            );
     }
}
