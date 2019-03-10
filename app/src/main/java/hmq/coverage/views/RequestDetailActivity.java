package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;

import hmq.coverage.R;
import hmq.coverage.interfaces.OnGetDataInterface;
import hmq.coverage.model.Database;
import hmq.coverage.model.Location;
import hmq.coverage.model.Model;
import hmq.coverage.model.Request;
import hmq.coverage.model.User;

public class RequestDetailActivity extends AppCompatActivity {

    TextView name;
    TextView date;
    TextView time;
    TextView location;
    TextView amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        final Request request = Model.getInstance().getCurrentRequest();

        name = findViewById(R.id.req_name);
        date = findViewById(R.id.req_date);
        time = findViewById(R.id.req_time);
        location = findViewById(R.id.req_location);
        amount = findViewById(R.id.req_amount);

        final User user = new User();
        Database database = new Database();
        database.getUser(request.getUid(), new OnGetDataInterface() {
            @Override
            public void onDataRetrieved(DataSnapshot data) {
                User tmp = data.getValue(User.class);
                if (tmp != null) {
                    user.updateUser(tmp.getUid(), tmp.getFname(), tmp.getLname(), tmp.getEmail());
                }
            }

            @Override
            public void onFailed() {

            }
        });
        Location loc = Model.getInstance().getLocation(request.getLocationID());
        name.setText(user.toString());
        date.setText(request.getDate());
        time.setText(request.getTime());
        location.setText(loc.toString());
        amount.setText(request.getAmount());


        Button button = findViewById(R.id.cover);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Model model = Model.getInstance();
                final Location tmp = model.getLocation(request.getLocationID());
                tmp.removeRequest(request);
                request.setFinalCover(Model.getInstance().getCurrentUser().getUid());
                model.updateRequest(request, new OnGetDataInterface() {
                    @Override
                    public void onDataRetrieved(DataSnapshot data) {
                        model.updateLocation(tmp, new OnGetDataInterface() {
                            @Override
                            public void onDataRetrieved(DataSnapshot data) {
                                Toast.makeText(RequestDetailActivity.this, "Success, You are covering for them.", Toast.LENGTH_SHORT).show();
                                startActivity( new Intent( getApplicationContext(), HomeActivity.class ) );
                            }

                            @Override
                            public void onFailed() {
                                Toast.makeText(RequestDetailActivity.this, "Failed, Network Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailed() {
                        Toast.makeText(RequestDetailActivity.this, "Failed, Network Error", Toast.LENGTH_SHORT).show();
                    }
                });



                //Save current user as the coverer, remove request from location list
            }
        });


    }
}
