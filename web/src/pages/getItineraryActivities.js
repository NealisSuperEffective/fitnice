import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed display the page of the website which displays the details of an itinerary.
 */
class GetItineraryActivities extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'addItineraryToPage','submitItinerarySearch','addItinerariesToPage'], this);
        this.dataStore = new DataStore();

        this.dataStore.addChangeListener(this.addItineraryToPage);
        this.dataStoreSearch = new DataStore;
        this.dataStoreSearch.addChangeListener(this.addItinerariesToPage);
        this.header = new Header(this.dataStore);
    }
    /**
     * Add the header to the page and load the Client.
     */
    mount() {
        document.getElementById('activities-of-itinerary').addEventListener('click', this.submit);
        document.getElementById('search-itineraries').addEventListener('click', this.submitItinerarySearch);
        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }
        /**
         * Method to run when the submit button is pressed. Call the VacanzaService to see the
         * details of an itinerary.
         */
        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const getButton = document.getElementById('activities-of-itinerary');
            const origButtonText = getButton.innerText;
            getButton.innerText = 'Loading...';
            //user input
            const tripName = document.getElementById('tripName').value;
            const email = document.getElementById('email').value;
            //get itinerary from database
            const itinerary = await this.client.getItinerary(email, tripName, (error) => {
                getButton.innerText = origButtonText;
                const errorMessageDisplay = document.getElementById('error-message');
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
            this.dataStore.set('itinerary', itinerary);

            getButton.innerText = 'Complete';
            getButton.innerText = 'Submit to View';
        }
    /**
     * Displays the details of an itinerary by embedding a list of all activities, as well as other attributes.
      */
    addItineraryToPage() {
           const itinerary = this.dataStore.get('itinerary');
                if (itinerary == null) {
                    return;
                }
           document.getElementById('tripName').innerText = itinerary.tripName;
           const submitResultsContainer = document.getElementById('submit-results-container');
           const submitCriteriaDisplay  =   document.getElementById('city-title-display');
           const cityResultsDisplay  =   document.getElementById('city-results-display');

           const submitUsersDisplay  =   document.getElementById('users-display');
           const activityTableContainer = document.getElementById('activities-table-container');
           const activitiesContainer = document.getElementById('activities-container');
           const tagsResultsDisplay = document.getElementById('tags-results-display');

           activitiesContainer.classList.remove('hidden');
           submitResultsContainer.classList.remove('hidden');

           cityResultsDisplay.innerHTML = itinerary.cities;

           submitUsersDisplay.innerHTML = itinerary.users;
           tagsResultsDisplay.innerHTML = itinerary.tags;


           const activities = itinerary.activities

           if (activities == null) {
                return '<h4>No activities found</h4>';
           }
           let activityHtml = '';
           let activity;
                for (activity of activities) {

                     const isKidFriendly = activity.kidFriendly === "Yes";
                     const isWeatherPermitting = activity.weatherPermitting === "Yes";

                     if(isKidFriendly == true) { var kidFriendlyText = 'child-friendly'} else {var kidFriendlyText = 'not child-friendly'}
                     if(isWeatherPermitting == true) { var weatherPermittingText = 'weather-permitting'} else {var weatherPermittingText = 'rain-or-shine'}
                     if(activity.address != null) {
                          var addressString = activity.address;
                     } else {var addressString = ' ';}

                     activityHtml += `
                           <li class="activity">
                                        <span class="name">${activity.name}</span>
                                        <span class="space">${" : "}</span>
                                        <span class="place">${activity.cityCountry}</span>
                                        <span class="space">${"   :   "}</span>
                                        <span class="type">${activity.type_OF_ACTIVITY}</span>
                                        <span class="space">${"   :   "}</span>
                                        <span class="kidFriendly">${kidFriendlyText}</span>

                                        <span class="space">${"    :    "}</span>
                                        <span class="weatherPermitting">${weatherPermittingText}</span>

                                        <span class="space">${"   :   "}</span>

                                        <span class="address"><a href= "https://www.google.com/maps/place/${addressString}"> ${addressString}</a></span>

                           </li>
                            <br>
                      `;

                }
                document.getElementById('activities').innerHTML = activityHtml;

                 document.getElementById('view-itinerary-activities-form').reset;
            }
            async submitItinerarySearch(evt) {
                        evt.preventDefault();

                        const errorMessageDisplay = document.getElementById('error-message');
                        errorMessageDisplay.innerText = ``;
                        errorMessageDisplay.classList.add('hidden');

                        const getButton = document.getElementById('search-itineraries');
                        const origButtonText = getButton.innerText;
                        getButton.innerText = 'Loading...';
                        //user input

                        const email = document.getElementById('email').value;
                        //get itinerary from database
                        const itinerariesFound = await this.client.search(email, (error) => {
                            getButton.innerText = origButtonText;
                            const errorMessageDisplay = document.getElementById('error-message');
                            errorMessageDisplay.innerText = `Error: ${error.message}`;
                            errorMessageDisplay.classList.remove('hidden');
                        });
                        this.dataStoreSearch.set('itineraries', itinerariesFound);
console.log(itinerariesFound);
                        }
addItinerariesToPage() {
           const itineraries = this.dataStoreSearch.get('itineraries');
console.log(itineraries);
           const searchResultsContainer = document.getElementById('search-results-container');

            searchResultsContainer.classList.remove('hidden');
           if (itineraries == null) {
                return '<h4>No itineraries found</h4>';
           }
           let itinerariesHtml = '';
           let itinerary;
                for (itinerary of itineraries) {

                     itinerariesHtml += `
                           <li class="itinerary">
                                        <span class="name">${itinerary.tripName}</span>
                                        <span class="space">${" : "}</span>
                                        <span class="cities">${itinerary.cities}</span>

                           </li>
                            <br>
                      `;

                }
                document.getElementById('itineraries').innerHTML = itinerariesHtml;
                const getButton = document.getElementById('search-itineraries');
                getButton.innerText = "Search for Trips";
                document.getElementById('view-itinerary-activities-form').reset;
            }

}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getItineraryActivities = new GetItineraryActivities();
    getItineraryActivities.mount();
};

window.addEventListener('DOMContentLoaded', main);