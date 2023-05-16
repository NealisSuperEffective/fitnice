import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the AddActivityToItinerary page of the website.
 */
class AddActivityToItinerary extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit','submitRemove', 'searchActivities', 'addActivitiesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStoreSearch = new DataStore();
        this.dataStoreSearch.addChangeListener(this.addActivitiesToPage);
        this.header = new Header(this.dataStore);
    }
    /**
     * Add the header to the page and load the Client.
     */
    mount() {
        document.getElementById('add-activity').addEventListener('click', this.submit);
        document.getElementById('remove-activity').addEventListener('click', this.submitRemove);
        document.getElementById('search-activity').addEventListener('click', this.searchActivities);
        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }
        /**
         * Method to run when the add activity submit button is pressed. Calls the VacanzaService to add the
         * activity.
         */
        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const createButton = document.getElementById('add-activity');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const cityCountry = document.getElementById('activity-cityCountry').value;
            const name = document.getElementById('activity-name').value;

            const email = document.getElementById("email").value;
            const tripName = document.getElementById("itinerary-name").value;

            const activities = await this.client.addActivityToItinerary(email, tripName, cityCountry, name, (error) => {
                createButton.innerText = origButtonText;
                const errorMessageDisplay = document.getElementById('error-message');
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });

            this.dataStore.set('activityList', activities);
            const itinerary = await this.client.getItinerary(email, tripName);
            this.dataStore.set('itinerary', itinerary);
            let activityInput1 = document.getElementById('activity-cityCountry');
            activityInput1.value = "";
            let activityInput2 = document.getElementById('activity-name');
            activityInput2.value = "";
            createButton.innerText = 'Complete';
            createButton.innerText = 'Add Another Activity';
        }
        /**
         * Method to run when the remove activity submit button is pressed. Calls the VacanzaService to remove the
         * activity.
         */
        async submitRemove(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const createButton = document.getElementById('remove-activity');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const cityCountry = document.getElementById('activity-cityCountry').value;
            const name = document.getElementById('activity-name').value;

            const email = document.getElementById("email").value;
            const tripName = document.getElementById("itinerary-name").value;

            const activities = await this.client.removeActivityFromItinerary(email, tripName, cityCountry, name, (error) => {
                createButton.innerText = origButtonText;
                const errorMessageDisplay = document.getElementById('error-message');
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });

            this.dataStore.set('activityList', activities);
            const itinerary = await this.client.getItinerary(email, tripName);
            this.dataStore.set('itinerary', itinerary);
            let activityInput1 = document.getElementById('activity-cityCountry');
            activityInput1.value = "";
            let activityInput2 = document.getElementById('activity-name');
            activityInput2.value = "";
            createButton.innerText = 'Complete';
            createButton.innerText = 'Remove Another Activity';
        }
        async searchActivities(evt) {
           evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const createButton = document.getElementById('search-activity');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const cityCountry = document.getElementById('search-activity-cityCountry').value;
                if (cityCountry == null || cityCountry === '') {
                    createButton.innerText = origButtonText;
                    return;
                }
            const activities = await this.client.searchActivities(cityCountry, (error) => {
                  createButton.innerText = origButtonText;
                  const errorMessageDisplay = document.getElementById('error-message');
                  errorMessageDisplay.innerText = `Error: ${error.message}`;
                  errorMessageDisplay.classList.remove('hidden');
                  });

            this.dataStoreSearch.set('activities', activities);
        }

        addActivitiesToPage() {

            const activities = this.dataStoreSearch.get('activities');
                if (activities == null) {
                    return;
                }

            const activitiesContainer = document.getElementById('activities-search-container');

            activitiesContainer.classList.remove('hidden');

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

                                                <span class="address">${addressString}</span>

                                   </li>
                                    <br>
                              `;

                        }
                    document.getElementById('activities').innerHTML = activityHtml;
                    document.getElementById('search-activity-cityCountry').reset;
                    const createButton = document.getElementById('search-activity');

                    let activityInput1 = document.getElementById('search-activity-cityCountry');
                    activityInput1.value = "";
                    createButton.innerText = 'Complete';
                    createButton.innerText = 'Search Another City';
                        }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const addActivityToItinerary = new AddActivityToItinerary();
    addActivityToItinerary.mount();
};

window.addEventListener('DOMContentLoaded', main);