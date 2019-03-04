package hmq.coverage.views;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hmq.coverage.R;
import hmq.coverage.interfaces.OnGetDataInterface;
import hmq.coverage.model.Location;
import hmq.coverage.model.Model;
import hmq.coverage.model.Request;

public class CreateRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText dateText;
    EditText timeText;
    Spinner locationSpinner;
    EditText moneyText;
    Button createRequest;
    Location location;
    private final Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        location = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        dateText = findViewById(R.id.date);
        timeText = findViewById(R.id.time);
        locationSpinner = findViewById(R.id.location_select);
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
        moneyText = findViewById(R.id.amount);
        createRequest = findViewById(R.id.create_request);


        createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dateText.getText().toString();
                String time = timeText.getText().toString();
                String money = moneyText.getText().toString();
                if (location != null && !date.equals("") && !time.equals("") && !money.equals("")) {
                    final Request request = new Request(model.getCurrentUser(), date, time, location.getLid(), money);
                    model.addRequest(request, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {

                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(CreateRequestActivity.this, "Unable to Create Request", Toast.LENGTH_SHORT).show();
                        }
                    });
                    location.addRequest(request);
                    model.updateLocation(location, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {
                            startActivity( new Intent( getApplicationContext(), HomeActivity.class ) );
                        }

                        @Override
                        public void onFailed() {
                            Toast.makeText(CreateRequestActivity.this, "Error Creating Request", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(CreateRequestActivity.this, "Missing Data", Toast.LENGTH_SHORT).show();
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
