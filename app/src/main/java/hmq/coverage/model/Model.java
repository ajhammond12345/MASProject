package hmq.coverage.model;

/*
 * Model singleton that acts as backing store for all of the data for the app
 *
 * To get the reference to the model call "Model model = Model.getInstance();"
 * to add a user: "model.addUser(User user);"
 * to check login info: "model.login(String username, String password);"
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import hmq.coverage.interfaces.*;
import java.util.HashSet;
import java.util.Set;


public class Model {
    //the one and only instantiation of the class (making it a singleton)
    private static Model appModel = new Model();
    private final Set<Location> locations;
    private final Set<Request> requests;
    private User currentUser;

    /**
     * Default constructor for model
     * Initializes empty hashMap for the list of users
     */
    private Model() {
        locations = new HashSet<>();
        requests = new HashSet<>();
        currentUser = null;
    }

    /**
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Only changes the model
     * Only use immediately after login, DO NOT USE OTHERWISE
     *
     * @param user the new current user
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }


    /**
     * Logs a user into the app, handles both registering new users and logging in existing users
     * @param user the user to login
     * @param listener contains methods to run when data retrieved
     */
    public void login(final User user, final OnGetDataInterface listener) {
        final Database dc = new Database();
        dc.getUser(user.getUid(), new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                if (data.exists()) {
                    listener.onDataRetrieved(data);
                } else {
                    dc.addUser(user, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {
                            listener.onDataRetrieved(data);
                        }

                        @Override
                        public void onFailed() {
                            listener.onFailed();
                        }
                    });
                }
            }

            @Override
            public void onFailed() {
            }
        });
    }


    /**
     * Updates current user (who is logged in)
     * @param user the new current user
     */
    public void updateCurrentUser(final User user) {
        Database db = new Database();
        db.updateUser(user, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                User tmp = data.getValue(User.class);
                if (tmp != null) {
                    Model.getInstance().setCurrentUser(tmp);
                } else {
                    Model.getInstance().setCurrentUser(user);

                }
                Model.getInstance().setCurrentUser(tmp);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    /**
     * Updates a shelter in the model and database
     * @param location the updated shelter (identifies by key)
     * @param listener an interface to handle the response from the database
     */
    public void updateLocation(Location location, OnGetDataInterface listener) {
        locations.remove(location);
        locations.add(location);
        Database db = new Database();
        db.updateLocation(location, listener);
    }

    /**
     * Shelter to add to the model and database
     * @param location to add
     */
    public void addLocation(Location location) {
        locations.add(location);
        new Database().addLocation(location, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                //nothing needed
            }

            @Override
            public void onFailed() {
                //nothing needed
            }
        });
    }

    /**
     * Retrieves a set of all the shelters
     * @return a set of all the shelters stored in the model, and refreshes the list in the model
     * to match the database
     */
    public Set<Location> getLocations() {
        //should be changed to update the list of shelters from the database
        Database db = new Database();
        db.getLocations(new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                for (DataSnapshot dataSnapshot: data.getChildren()) {
                    locations.add(dataSnapshot.getValue(Location.class));
                    Log.d("LoadedShelter", dataSnapshot.getValue(Location.class).toString());
                }
            }

            @Override
            public void onFailed() {

            }
        });
        return locations;
    }

    //method used to access the singleton

    /**
     * Provides access to the model
     * @return a reference to the model
     */
    public static Model getInstance() {
        if (appModel == null) {
            appModel = new Model();
        }
        return appModel;
    }

}
