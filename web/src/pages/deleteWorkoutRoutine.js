import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
/**
 * Logic needed for the view table page of the website.
 */
class DeleteWorkoutRoutine extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'deleteWorkoutRoutine', 'delete'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.client = new MusicPlaylistClient(this.dataStore);
    }

    /**
         * Add the table to the page and load the MusicPlaylistClient.
         */
    mount() {
        console.log('deleteWorkoutRoutine.js mounting...');
        var deleteButton = document.getElementById("delete-workoutRoutine");
        deleteButton.addEventListener("click", (event) => this.deleteWorkoutRoutine(event));
//        this.client = new MusicPlaylistClient();
}



    /**
 * Method to run when the update goal update button is pressed. Call the BeefyClient to create the
 * playlist.
 */
    async delete(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const deleteButton = document.getElementById('delete-workoutRoutine');
        const origButtonText = deleteButton.innerText;
        deleteButton.innerText = 'Loading...';

        const routineName = document.getElementById('routineName').value;

        const goal = await this.client.deleteWorkoutRoutine(routineName, (error) => {
            deleteButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('workoutRoutine', workoutRoutine);
    }


    /**
         * Method to run when the update button is pressed. Call the BeefyServiceCleint to update the inventory.
         */
    async deleteWorkoutRoutine(event) {
        event.preventDefault();
        console.log("howdy from deleteWorkoutRoutine method")
        const routineName = document.getElementById('routineName').value;
//        const form = document.getElementById("delete-workoutRoutine-form");
//        const routineName = form.elements["routineName"].value;

        try {
            const updateRequest = await this.client.deleteWorkoutRoutine(routineName);
            alert('Routine deleted successfully!');
            window.location.reload();
        } catch (error) {
            console.error(error);
            alert('Error deleting Routine. See console for details.');
        }
    }

}
const main = async () => {
    const deleteWorkoutRoutine = new DeleteWorkoutRoutine();
    deleteWorkoutRoutine.mount();
};

window.addEventListener('DOMContentLoaded', main);