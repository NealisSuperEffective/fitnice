import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";



/**
 * Logic needed for the view playlist page of the website.
 */
class ViewItinerary extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addItineraryToPage', 'addActivitiesToPage', 'addCitiesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addItineraryToPage);
        this.dataStore.addChangeListener(this.addActivitiesToPage);
        this.dataStore.addChangeListener(this.addCitiesToPage);
        this.header = new Header(this.dataStore);
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get("email");
        const tripName = urlParams.get("tripName");
        const itinerary = await this.client.getItinerary(email, tripName);
        const cities = urlParams.get("cities");
        const activities= urlParams.get("activities");
        this.dataStore.set('itinerary', itinerary);
      // const activities = await this.client.getItineraryActivities(email, tripName)
       this.dataStore.set('activities', activities);
        //const cities = itinerary.cities;
        this.dataStore.set('cities', cities);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('viewItinerary')
        this.header.addHeaderToPage();
        this.client = new MusicPlaylistClient();
        this.clientLoaded();
    }

    /**
     * When the itinerary  is updated in the datastore, update the itinerary metadata on the page.
     */
    addItineraryToPage() {
        const itinerary = this.dataStore.get('itinerary');

        document.getElementById('tripName').innerHTML = itinerary.tripName
    }

    /**
     * When the activities are updated in the datastore, update the list of activities on the page.
     */
    addActivitiesToPage() {
       const itinerary = this.dataStore.get('itinerary');
       const activities = itinerary.activities;
       console.log(activities)

        let activitiesHtml = '';
        let activity;
        for (activity of activities) {
            activitiesHtml += `
                <li class="activity">
                    <span class="name">${activity.name}</span>
                    <span class="cityCountry">${activity.cityCountry}</span>
                </li>
            `;
        }
        document.getElementById("activities").innerHTML = activitiesHtml;
    }

    async addCitiesToPage() {
            const cities = this.dataStore.get('cities');

            let cityHtml = '';
            let city;
                cityHtml += `
                    <li class="city">
                        <span class="city">${cities}</span>
                    </li>
                `;
            document.getElementById("cities").innerHTML = cityHtml;
        }
}



/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewItinerary = new ViewItinerary();
    viewItinerary.mount();
};

window.addEventListener('DOMContentLoaded', main);
