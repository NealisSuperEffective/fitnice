import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_DATE = 'date';
const SEARCH_CRITERIA_NAME = 'name';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_DATE]: '',
    [SEARCH_CRITERIA_NAME]: '',
    [SEARCH_RESULTS_KEY]: [],
};


/**
 * Logic needed for the view workout page of the website.
 */
class GetWorkout extends BindingClass {
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
        document.getElementById('search-workouts-form').addEventListener('submit', this.search);
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
        // Prevent submitting the from from reloading the page.
        evt.preventDefault();

        const date = document.getElementById('date').value;
        const name = document.getElementById('name').value;

        if (date && name) {
            const results = await this.client.getWorkout(date, name);

            this.dataStore.setState({
                [SEARCH_CRITERIA_DATE]: date,
                [SEARCH_CRITERIA_NAME]: name,
                [SEARCH_RESULTS_KEY]: results,
            });
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    displaySearchResults() {
        const date = this.dataStore.get(SEARCH_CRITERIA_DATE);
        const name = this.dataStore.get(SEARCH_CRITERIA_NAME);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (date === '' && name === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${date}"`;
            searchCriteriaDisplay.innerHTML = `"${name}"`;
            console.log(searchResults)
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
        }
        document.getElementById("search-workouts-form").reset();
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of playlists objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults(searchResults) {
        if (searchResults === undefined) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Date</th><th>Name</th><th>Tags</th><th>Description</th><th>Exercises</th></tr>';
            if((searchResults.date != date) || (searchResults.name != name)) {
            console.log(searchResults.tags)
//        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="getWorkout.html?date=${searchResults.date}&name=${searchResults.name}&tags=${searchResults.tags}&description=${searchResults.description}&exercises=${searchResults.exercises}">${searchResults.date}</a>
                </td>
                <td>${searchResults.name}</td>
                <td>${searchResults.tags}</td>
                <td>${searchResults.description}</td>
                <td>${searchResults.exercises}</td>

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
    const getWorkout = new GetWorkout();
    getWorkout.mount();
};

window.addEventListener('DOMContentLoaded', main);