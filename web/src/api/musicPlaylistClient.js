import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class MusicPlaylistClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getTokenOrThrow',
        'getWorkout', 'getWorkoutRoutine', 'createWorkout', 'createWorkoutRoutine', 'deleteWorkoutRoutine'];

        this.bindClassMethods(methodsToBind, this);
        this.authenticator = new Authenticator();
        this.props = props;
        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the playlist for the given ID.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist's metadata.
     */
    async getWorkout(date, name, errorCallback) {

          try {
              const response = await this.axiosClient.get(`workouts/${date}/${name}`);
              return response.data.workout;
          } catch (error) {
              this.handleError(error, errorCallback)
          }
      }

    /**
     * Get the songs on a given playlist by the playlist's identifier.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of activities in an itinerary.
     */
    async getWorkoutRoutine(routineName, errorCallback) {

        try {
           // const response = await this.axiosClient.get(`itineraries/${id}/activities`);
           const response = await this.axiosClient.get(`workoutRoutines/${routineName}`);
            return response.data.workoutRoutine;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

        /**
         * Create a new itinerary owned by the current user.
         * @param tripName String The name of the itinerary to create.
         * @param tags Metadata tags to associate with a itinerary.
         * @param users Metadata users to associate with a itinerary.
         * @param users Metadata cities to associate with a itinerary.
         * @param errorCallback (Optional) A function to execute if the call fails.
         * @returns The itinerary that has been created.
         */
    async createWorkout(date, name, tags, description, exercises, errorCallback) {

        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create workouts.");
            const response = await this.axiosClient.post(`workouts`, {
                date: date,
                name: name,
                tags: tags,
                description: description,
                exercises: exercises,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            console.log(response)
            return response.data.workout;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

        async createWorkoutRoutine(routineName, tags, description, exercises, errorCallback) {

            try {
                const token = await this.getTokenOrThrow("Only authenticated users can create workoutRoutines.");

                const response = await this.axiosClient.post(`workoutRoutines`, {
                    routineName: routineName,
                    tags: tags,
                    description: description,
                    exercises: exercises,
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                return response.data.workoutRoutine;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
        }

        async deleteWorkoutRoutine(routineName, errorCallback) {
        try{
            const token = await this.getTokenOrThrow("Only authenticated users can delete workoutRoutines.");
            await this.axiosClient.delete(`workoutRoutines/${routineName}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
            });
            return true;
        } catch (error) {
            this.handleError(error, errorCallback)
            return false;
        }
        }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
//    async updateWorkoutRoutine(, errorCallback) {
//        try {
//            const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
//            const response = await this.axiosClient.post(`playlists/${id}/songs`, {
//                id: id,
//                asin: asin,
//                trackNumber: trackNumber
//            }, {
//                headers: {
//                    Authorization: `Bearer ${token}`
//                }
//            });
//            return response.data.songList;
//        } catch (error) {
//            this.handleError(error, errorCallback)
//        }
//    }
//        /**
//         * Adds requested activity to requested itinerary's list of activities.
//         * @param email A string containing partition key for itinerary to pass to the API.
//         * @param tripName A string containing sort key for itinerary to pass to the API.
//         * @param cityCountry A string containing partition key for activity to pass to the API.
//         * @param name A string containing sort key for activity to pass to the API.
//         * @param errorCallback (Optional) A function to execute if the call fails.
//         * @returns The list of activities that have been updated in the itinerary.
//         */
//    async addActivityToItinerary(email, tripName, cityCountry, name, errorCallback) {
//            try {
//                const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
//                const response = await this.axiosClient.post(`itineraries/${email}/${tripName}/activities`, {
//                    email: email,
//                    tripName: tripName,
//                    cityCountry: cityCountry,
//                    name: name
//              }, {
//                  headers: {
//                      Authorization: `Bearer ${token}`
//                  }
//              });
//                return response.data.activityList;
//            } catch (error) {
//                this.handleError(error, errorCallback)
//            }
//        }
//    async removeActivityFromItinerary(email, tripName, cityCountry, name, errorCallback) {
//            try {
//                const token = await this.getTokenOrThrow("Only authenticated users can add a song to a playlist.");
//                const response = await this.axiosClient.put(`itineraries/${email}/${tripName}/activities`, {
//                    email: email,
//                    tripName: tripName,
//                    cityCountry: cityCountry,
//                    name: name
//              }, {
//                  headers: {
//                      Authorization: `Bearer ${token}`
//                  }
//              });
//                return response.data.activityList;
//            } catch (error) {
//                this.handleError(error, errorCallback)
//            }
//        }
//
//    /**
//     * Search for itineraries that match the search criteria.
//     * @param criteria A string containing search criteria to pass to the API.
//     * @returns The itineraries that match the search criteria.
//     */
//    async search(criteria, errorCallback) {
//        try {
//            const queryParams = new URLSearchParams({ q: criteria })
//            const queryString = queryParams.toString();
//console.log(queryString);
//            const response = await this.axiosClient.get(`itineraries/search?${queryString}`);
//
//            return response.data.itineraries;
//        } catch (error) {
//            this.handleError(error, errorCallback)
//        }
//
//    }
//    async searchActivities(cityCountry, errorCallback) {
//        try{
//            const response = await this.axiosClient.get(`activities/search?cityCountry=${cityCountry}`);
//            return response.data.activityModelsList;
//        } catch (error) {
//            this.handleError(error, errorCallback);
//        }
//    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}