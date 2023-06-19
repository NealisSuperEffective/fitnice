package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.GetWorkoutRequest;
import com.nashss.se.fitnice.activity.results.GetWorkoutResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWorkoutLambda
    extends LambdaActivityRunner<GetWorkoutRequest, GetWorkoutResult>
    implements RequestHandler<LambdaRequest<GetWorkoutRequest>, LambdaResponse> {

        private final Logger log = LogManager.getLogger();

        @Override
        public LambdaResponse handleRequest(LambdaRequest<GetWorkoutRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetWorkoutRequest.builder()
                                .withWorkoutDate(path.get("date"))
                                .withWorkoutDate(path.get("workoutName"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetWorkoutActivity().handleRequest(request)
        );
    }
}
