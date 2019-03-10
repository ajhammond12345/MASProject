package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import hmq.coverage.R;
import hmq.coverage.interfaces.OnGetDataInterface;
import hmq.coverage.model.Location;
import hmq.coverage.model.Model;

public class CheckInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        location = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        final Model model = Model.getInstance();
        Spinner locationSpinner = findViewById(R.id.select_courthouse);
        List<Location> locations = new ArrayList<>(model.getLocations());
        /*locations.sort(new Comparator<Location>() {
            @Override
            public int compare(Location location, Location t1) {
                return location.toString().compareToIgnoreCase(t1.toString());
            }
        });*/
        ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(this);

        Button checkIn = findViewById(R.id.check_in);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location != null) {
                    location.checkIn(model.getCurrentUser());
                    model.setCurrentLocation(location);
                    model.updateLocation(location, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {
                            startActivity( new Intent( getApplicationContext(), RequestListActivity.class ) );
                        }

                        @Override
                        public void onFailed() {
                            //nothing
                        }
                    });
                } else {
                    Toast.makeText(CheckInActivity.this, "Select Location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        location = (Location) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        location = null;
    }
}
