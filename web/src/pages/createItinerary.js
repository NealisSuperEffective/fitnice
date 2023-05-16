import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create itinerary page of the website.
 */
class CreateItinerary extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }
    /**
     * Add the header to the page and load the Client.
     */
    mount() {

        document.getElementById('create-itinerary').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }
        /**
         * Method to run when the create itinerary submit button is pressed. Call the VacanzaService to create the
         * itinerary.
         */
        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const createButton = document.getElementById('create-itinerary');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const tripName = document.getElementById('itinerary-name').value;
            const tagsText = document.getElementById('tags').value;
            const usersText = document.getElementById('users').value;
            const citiesText = document.getElementById('cities').value;

            let tags;
            if (tagsText.length < 1) {
                tags = null;
            } else {
                tags = tagsText.split(/\s*,\s*/);
            }
            let users;
            if (usersText.length < 1) {
                users = null;
            } else {
                users = usersText.split(/\s*,\s*/);
            }
            let cities;
            if (citiesText.length < 1) {
                cities = null;
            } else {
                cities = citiesText.split(/\s*,\s*/);
            }

            const itinerary = await this.client.createItinerary(tripName, tags, users, cities, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
            //This can be used to trigger another event, a redirect, for example.
            this.dataStore.set('itinerary', itinerary);

            createButton.innerText = 'Complete';

            //Set up for another itinerary creation
            setTimeout(function(){
                 createButton.innerText = 'Create New Vacation Plan';
                 let itineraryInput = document.getElementById('create-playlist-form');
                 itineraryInput.reset();
                        }, 800);


        }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createItinerary = new CreateItinerary();
    createItinerary.mount();
};

window.addEventListener('DOMContentLoaded', main);