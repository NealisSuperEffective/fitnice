import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create playlist page of the website.
 */
class CreateActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPlaylist'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewPlaylist);
        this.header = new Header(this.dataStore);
    }
    /**
     * Add the header to the page and load the MusicPlaylistClient.
     //do we need another client??
     */
    mount() {
        document.getElementById('create-activity').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }
        /**
         * Method to run when the create activity submit button is pressed. Call the MusicPlaylistService to create the
         * activity.
         */
        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const createButton = document.getElementById('create-activity');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const cityCountry = document.getElementById('activity-cityCountry').value;
            const name = document.getElementById('activity-name').value;
            const address = document.getElementById('activity-address').value;
            const type = document.getElementById("activity-type").value;
            const kidFriendly = document.getElementById("activity-kidFriendly").value;
            const weatherPermitting = document.getElementById("activity-weatherPermitting").value;

            const activity = await this.client.createActivity(cityCountry, name, address, type, kidFriendly, weatherPermitting);
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');

            this.dataStore.set('activity', activity);
            createButton.innerText = 'Complete';
            createButton.innerText = 'Create New Vacation Activity';
        }
   /**
     * When the playlist is updated in the datastore, redirect to the view playlist page.
     //this will be two redirects to activities and view itinerary pages
     */
    redirectToViewPlaylist() {
        const playlist = this.dataStore.get('playlist');
        if (playlist != null) {
            window.location.href = `/playlist.html?id=${playlist.id}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createActivity = new CreateActivity();
    createActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);