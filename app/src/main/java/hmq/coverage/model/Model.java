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
    private Location currentLocation;
    private Request currentRequest;
    private User selectedUser;

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
    private void setCurrentUser(User user) {
        currentUser = user;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        currentLocation = location;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(Request request) {
        currentRequest = request;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User user) {
        selectedUser = user;
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


    public void getUser(String userID, OnGetDataInterface listener) {
        Database db = new Database();
        db.getUser(userID, listener);
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
     * Updates a location in the model and database
     * @param location the updated location (identified by key)
     * @param listener an interface to handle the response from the database
     */
    public void updateLocation(Location location, OnGetDataInterface listener) {
        locations.remove(location);
        locations.add(location);
        Database db = new Database();
        db.updateLocation(location, listener);
    }

    /**
     * Add Location to the model and database
     * @param location to add
     */
    public void addLocation(Location location, final OnGetDataInterface listener) {
        locations.add(location);
        new Database().addLocation(location, new OnGetDataInterface() {
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

    /**
     * Retrieves a set of all the Locations
     * @return a set of all the locations stored in the model, and refreshes the list in the model
     * to match the database
     */
    public Set<Location> getLocations() {
        Database db = new Database();
        db.getLocations(new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                for (DataSnapshot dataSnapshot: data.getChildren()) {
                    locations.remove(dataSnapshot.getValue(Location.class));
                    locations.add(dataSnapshot.getValue(Location.class));
                    Log.d("LoadedLocation", dataSnapshot.getValue(Location.class).toString());
                }
            }

            @Override
            public void onFailed() {

            }
        });
        return locations;
    }

    public Location getLocation(String lid) {
        for (Location location : locations) {
            if (location.getLid().equals(lid)) {
                return location;
            }
        }
        return null;
    }

    /**
     * Updates a request in the model and database
     * @param request the updated request (identifies by key)
     * @param listener an interface to handle the response from the database
     */
    public void updateRequest(Request request, OnGetDataInterface listener) {
        requests.remove(request);
        requests.add(request);
        Database db = new Database();
        db.updateRequest(request, listener);
    }

    /**
     * Add Request to the model and database
     * @param request to add
     */
    public void addRequest(final Request request, final OnGetDataInterface listener) {
        new Database().addRequest(request, new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                listener.onDataRetrieved(data);
            }

            @Override
            public void onFailed() {
                listener.onFailed();
            }
        });
        requests.add(request);


    }

    /**
     * Retrieves a set of all the requests
     * @return a set of all the requests stored in the model, and refreshes the set in the model
     * to match the database
     */
    public Set<Request> getRequests() {
        Database db = new Database();
        db.getRequests(new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                for (DataSnapshot dataSnapshot: data.getChildren()) {
                    requests.add(dataSnapshot.getValue(Request.class));
                    Log.d("LoadedRequest", dataSnapshot.getValue(Request.class).toString());
                }
            }

            @Override
            public void onFailed() {

            }
        });
        return requests;
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
