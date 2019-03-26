package hmq.coverage.views;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.widget.TimePicker;
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
import java.util.Date;
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
    DatePickerDialog picker;
    TimePickerDialog picker1;
    EditText eText;
    EditText eText1;
    Location location;
    private final Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        location = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        dateText = findViewById(R.id.time);
        timeText = findViewById(R.id.time);
        locationSpinner = findViewById(R.id.location_select);
        List<Location> locations = new ArrayList<>(model.getLocations());
        /*locations.sort(new Comparator<Location>() {
            @Override
            public int compare(Location location, Location t1) {
                return location.toString().compareToIgnoreCase(t1.toString());
            }
        });*/
        eText=(EditText) findViewById(R.id.date1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CreateRequestActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        eText1=(EditText) findViewById(R.id.time1);
        eText1.setInputType(InputType.TYPE_NULL);
        eText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker1 = new TimePickerDialog(CreateRequestActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eText1.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker1.show();
            }
        });
        ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(this);
        moneyText = findViewById(R.id.amount);
        createRequest = findViewById(R.id.create_request);


        createRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String date = eText.getText().toString();
                String time = eText1.getText().toString();
                String money = moneyText.getText().toString();
                if (location != null && !date.equals("") && !time.equals("") && !money.equals("")) {
                    final Request request = new Request(model.getCurrentUser().getUid(), date, time, location.getLid(), money);
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
                    model.setCurrentLocation(location);
                    model.updateLocation(location, new OnGetDataInterface() {
                        @Override
                        public void onDataRetrieved(DataSnapshot data) {
                            Date today = new Date();
                            DateFormat format = new SimpleDateFormat("dd/M/yyyy");
                            Log.d("Today's date", format.format(today));
                            Log.d("Date passed in", date);
                            if (date.equals(format.format(today))) {
                                startActivity( new Intent( getApplicationContext(), UserListActivity.class ) );
                            } else {
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
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
