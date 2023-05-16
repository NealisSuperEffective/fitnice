import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from "../api/authenticator";

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_EMAIL = 'email';
const SEARCH_CRITERIA_TRIP_NAME = 'tripName';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_EMAIL]: '',
    [SEARCH_CRITERIA_TRIP_NAME]: '',
   [SEARCH_RESULTS_KEY]: '',
};

/**
 * Logic needed for the view itinerary page of the website.
 */
class SearchItineraries extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('search-itineraries-form').addEventListener('submit', this.search);
        document.getElementById('search-button').addEventListener('click', this.search);

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }

    /**
     * Uses the client to perform the search,
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async search(evt) {
        // Prevent submitting the form from reloading the page.
        evt.preventDefault();

        const email = document.getElementById('email').value;
        const tripName = document.getElementById('tripName').value;

        if (email && tripName) {
            const results = await this.client.getItinerary(email, tripName);
            this.dataStore.setState({
                [SEARCH_CRITERIA_EMAIL]: email,
                  [SEARCH_CRITERIA_TRIP_NAME]: tripName,
                [SEARCH_RESULTS_KEY]: results,
            });
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    async displaySearchResults() {
        const email = await this.dataStore.get(SEARCH_CRITERIA_EMAIL);
        const tripName = await this.dataStore.get(SEARCH_CRITERIA_TRIP_NAME);
        const searchResult = await this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (email === '' && tripName === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${email}"`;
            searchCriteriaDisplay.innerHTML = `"${tripName}"`;
            searchResultsDisplay.innerHTML = await this.getHTMLForSearchResults(searchResult);
        }
        document.getElementById("search-itineraries-form").reset();
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of playlists objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    async getHTMLForSearchResults(searchResult) {
            if (searchResult === undefined){
                         return '<h4>No results found</h4>';
             }

     let html = '<table><tr><th>TripName</th><th>Email</th></tr>';
                if ((searchResult.email != email) || (searchResult.tripName != tripName)) {
                                    html += `
                                    <tr>
                                        <p> Click on Trip Name to View Cities and Activities </p>
                                        <td>
                                            <a href="viewItinerary.html?email=${searchResult.email}&tripName=${searchResult.tripName}&cities=${searchResult.cities}&activities=${searchResult.activities}">${searchResult.tripName}</a>
                                        </td>
                                         <td>${searchResult.email}</td>
                                          </tr>`;
                                }
                                html += '</table>';

                                return html;
                }
                            }

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const searchItineraries = new SearchItineraries();
    searchItineraries.mount();
};

window.addEventListener('DOMContentLoaded', main);
