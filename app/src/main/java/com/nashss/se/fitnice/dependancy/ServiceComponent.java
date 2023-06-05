package com.nashss.se.fitnice.dependancy;

import com.nashss.se.fitnice.activity.*;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the VACANZA Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Provides the relevant activity.
     *
     * @return CreateWorkoutRoutineActivity
     */
    CreateWorkoutRoutineActivity provideCreateWorkoutRoutineActivity();

    /**
     * Provides the relevant activity.
     *
     * @return GetWorkoutRoutineActivity
     */
    GetWorkoutRoutineActivity provideGetWorkoutRoutineActivity();

//    /**
//     * Provides the relevant activity.
//     *
//     * @return SearchItinerariesActivity
//     */
//    SearchItinerariesActivity provideSearchItinerariesActivity();

    /**
     * Provides the relevant activity.
     *
     * @return CreateWorkoutActivity
     */
    CreateWorkoutActivity provideCreateWorkoutActivity();

    /**
     * Provides the relevant activity.
     *
     * @return GetWorkoutActivity
     */
    GetWorkoutActivity provideGetWorkoutActivity();

    /**
     * Provides the relevant activity.
     *
     * @return  UpdateWorkoutRoutineActivity
     */
    UpdateWorkoutRoutineActivity provideUpdateWorkoutRoutineActivity();

//    /**
//     * Provides the relevant activity.
//     *
//     * @return  RemoveWorkoutRoutineActivity
//     */
//    RemoveWorkoutRoutineActivity provideRemoveWorkoutRoutineActivity();

//    /**
//     * Provides the relevant activity.
//     *
//     * @return SearchActivitiesActivity
//     */
//    SearchActivitiesActivity provideSearchActivitiesActivity();
}
