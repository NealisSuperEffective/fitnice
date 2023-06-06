package com.nashss.se.fitnice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.fitnice.activity.requests.CreateWorkoutRequest;
import com.nashss.se.fitnice.activity.results.CreateWorkoutResult;

public class CreateWorkoutLambda
        extends LambdaActivityRunner<CreateWorkoutRequest, CreateWorkoutResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateWorkoutRequest>, LambdaResponse>

    {

        @Override
        public LambdaResponse handleRequest (AuthenticatedLambdaRequest < CreateWorkoutRequest > input, Context context)
        {
            return super.runActivity(
                    () -> {
                        CreateWorkoutRequest unauthenticatedRequest = input.fromBody(CreateWorkoutRequest.class);
                        return input.fromUserClaims(claims ->
                                CreateWorkoutRequest.builder()
                                        .withDate(unauthenticatedRequest.getDate())
                                        .withName(unauthenticatedRequest.getName())
                                        .withTags(unauthenticatedRequest.getTags())
                                        .withDescription(unauthenticatedRequest.getDescription())
                                        .withExercises(unauthenticatedRequest.getExercises())
                                        .build());
                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideCreateWorkoutActivity().handleRequest(request)
            );
        }
    }
