package hmq.coverage;

/**
 * Created by AlexanderHammond on 2/27/19.
 */

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    private final DatabaseReference database;

    public Database() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void addUser(User user) {
        changeUser(user);
    }

    public void updateUser(User user) {
        changeUser(user);
    }

    private void changeUser(User user) {
        DatabaseReference userRef = database.child("users").child(user.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //do nothing for now
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //do nothing for now
            }
        });
        userRef.setValue(user);
    }

    public void addRequest(Request request) {
        changeRequest(request);
    }

    public void updateRequest(Request request) {
        changeRequest(request);
    }

    private void changeRequest(Request request) {
        DatabaseReference requestRef = database.child("requests").child(request.getRid());
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //do nothing for now
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //do nothing for now
            }
        });
        requestRef.setValue(request);
    }

    public void addLocation(Location location) {
        changeLocation(location);
    }

    public void updateLocation(Location location) {
        changeLocation(location);
    }

    private void changeLocation(Location location) {
        DatabaseReference locRef = database.child("locations").child(location.getLid());
        locRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //do nothing for now
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //do nothing for now
            }
        });
        locRef.setValue(location);
    }
}
