package hmq.coverage.interfaces;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by AlexanderHammond on 3/12/18.
 */


public interface OnGetDataInterface {
    /**
     * Method for successful data retrieval, parameter data will hold all retrieved data
     * @param data all the data retrieved
     */
    void onDataRetrieved(DataSnapshot data);

    /**
     * Method to run on database failure
     */
    void onFailed();
}
