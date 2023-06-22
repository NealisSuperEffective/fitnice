import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

const SEARCH_CRITERIA_KEY = 'routineName';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

class GetWorkoutRoutine extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults'], this);

        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
    }

    mount() {
        document.getElementById('search-workoutRoutines-form').addEventListener('submit', this.search);
        document.getElementById('search-button').addEventListener('click', this.search);

        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }

    async search(evt) {
        evt.preventDefault();

        const searchCriteria = document.getElementById('routineName').value;
        const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

        if (previousSearchCriteria === searchCriteria) {
            return;
        }

        if (searchCriteria) {
            const results = await this.client.getWorkoutRoutine(searchCriteria);

            this.dataStore.setState({
                [SEARCH_CRITERIA_KEY]: searchCriteria,
                [SEARCH_RESULTS_KEY]: results,
            });
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

    displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
        }
        document.getElementById("search-workoutRoutines-form").reset();
    }

    getHTMLForSearchResults(searchResults) {
        if (searchResults === undefined) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>RoutineName</th><th>Tags</th><th>Description</th><th>Exercises</th></tr>';
//        for (const result of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="getWorkoutRoutine.html?routineName=${searchResults.routineName}&tags=${searchResults.tags}&description=${searchResults.description}&exercises=${searchResults.exercises}">${searchResults.name}</a>
                </td>
                <td>${searchResults.tags}</td>
                <td>${searchResults.description}</td>
                <td>${searchResults.exercises}</td>
            </tr>`;
//        }
        html += '</table>';

        return html;
    }
}

const main = async () => {
    const getWorkoutRoutine = new GetWorkoutRoutine();
    getWorkoutRoutine.mount();
};

window.addEventListener('DOMContentLoaded', main);
