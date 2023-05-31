package com.nashss.se.fitnice.dependancy;

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
     * @return CreateItineraryActivity
     */
    CreateItineraryActivity provideCreateItineraryActivity();

    /**
     * Provides the relevant activity.
     *
     * @return GetItineraryActivity
     */
    GetItineraryActivity provideGetItineraryActivity();

    /**
     * Provides the relevant activity.
     *
     * @return SearchItinerariesActivity
     */
    SearchItinerariesActivity provideSearchItinerariesActivity();

    /**
     * Provides the relevant activity.
     *
     * @return CreateActivityActivity
     */
    CreateActivityActivity provideCreateActivityActivity();

    /**
     * Provides the relevant activity.
     *
     * @return GetItineraryActivitiesActivity
     */
    GetItineraryActivitiesActivity provideGetItineraryActivitiesActivity();

    /**
     * Provides the relevant activity.
     *
     * @return  AddActivityToItineraryActivity
     */
    AddActivityToItineraryActivity provideAddActivityToItineraryActivity();

    /**
     * Provides the relevant activity.
     *
     * @return  RemoveActivityFromItineraryActivity
     */
    RemoveActivityFromItineraryActivity provideRemoveActivityFromItineraryActivity();

    /**
     * Provides the relevant activity.
     *
     * @return SearchActivitiesActivity
     */
    SearchActivitiesActivity provideSearchActivitiesActivity();
}
