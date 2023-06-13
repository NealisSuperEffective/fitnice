import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create itinerary page of the website.
 */
class CreateWorkoutRoutine extends BindingClass {
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

        document.getElementById('create-workoutRoutine').addEventListener('click', this.submit);

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

            const createButton = document.getElementById('create-workoutRoutine');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const routineName = document.getElementById('name').value;
            const tagsText = document.getElementById('tags').value;
            const descriptionText = document.getElementById('description').value;
            const exercisesText = document.getElementById('exercises').value;

            let tags;
            if (tagsText.length < 1) {
                tags = null;
            } else {
                tags = tagsText.split(/\s*,\s*/);
            }
            let description;
            if (descriptionText.length < 1) {
                description = null;
            } else {
                description = descriptionText.split(/\s*,\s*/);
            }
            let exercises;
            if (exercisesText.length < 1) {
                exercises = null;
            } else {
                exercises = exercisesText.split(/\s*,\s*/);
            }

            const workoutRoutine = await this.client.createWorkoutRoutine(routineName, tags, description, exercises, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
            //This can be used to trigger another event, a redirect, for example.
            this.dataStore.set('workoutRoutine', workoutRoutine);

            createButton.innerText = 'Complete';

            //Set up for another itinerary creation
            setTimeout(function(){
                 createButton.innerText = 'Create New Workout Routine';
                 let workoutRoutineInput = document.getElementById('create-playlist-form');
                 workoutRoutineInput.reset();
                        }, 800);


        }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createWorkoutRoutine = new CreateWorkoutRoutine();
    createWorkoutRoutine.mount();
};

window.addEventListener('DOMContentLoaded', main);