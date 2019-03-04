package hmq.coverage.model;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hmq.coverage.interfaces.OnGetDataInterface;

public class Database {
    private final DatabaseReference database;

    public Database() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User user, final OnGetDataInterface listener) {
        changeUser(user, listener);
    }

    public void updateUser(User user, final OnGetDataInterface listener) {
        changeUser(user, listener);
    }

    private void changeUser(User user, final OnGetDataInterface listener) {
        DatabaseReference userRef = database.child("users").child(user.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed();
            }
        });
        userRef.setValue(user);
    }

    public void getUser(final String uID, final OnGetDataInterface listener) {
        Log.e("getUser:uID", "" + uID);
        DatabaseReference userRef = database.child("users").child(uID);
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
                Log.d("Database:getUser", databaseError.getMessage());
            }
        };
        userRef.addListenerForSingleValueEvent(valueListener);
    }

    public void addRequest(Request request, final OnGetDataInterface listener) {
        String newID = database.child("requests").push().getKey();
        request.setRid(newID);
        changeRequest(request, listener);
    }

    public void updateRequest(Request request, final OnGetDataInterface listener) {
        changeRequest(request, listener);
    }

    private void changeRequest(Request request, final OnGetDataInterface listener) {
        DatabaseReference requestRef = database.child("requests").child(request.getRid());
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed();
            }
        });
        requestRef.setValue(request);
    }

    /**
     * Gets data for all the shelters from the database
     * @param listener an interface to handle the response from the database
     */
    public void getRequests(final OnGetDataInterface listener) {
        DatabaseReference requestRef = database.child("requests");
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //the entire list of shelters will be in this snapshot
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
                Log.d("Database:getLocations", databaseError.getMessage());
            }
        });
    }

    public void addLocation(Location location, final OnGetDataInterface listener) {
        changeLocation(location, listener);
    }

    public void updateLocation(Location location, final OnGetDataInterface listener) {
        changeLocation(location, listener);
    }

    private void changeLocation(Location location, final OnGetDataInterface listener) {
        DatabaseReference locRef = database.child("locations").child(location.getLid());
        locRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed();
            }
        });
        locRef.setValue(location);
    }

    /**
     * Gets data for all the shelters from the database
     * @param listener an interface to handle the response from the database
     */
    public void getLocations(final OnGetDataInterface listener) {
        DatabaseReference locationRef = database.child("locations");
        locationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //the entire list of shelters will be in this snapshot
                listener.onDataRetrieved(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailed();
                Log.d("Database:getLocations", databaseError.getMessage());
            }
        });
    }
}
