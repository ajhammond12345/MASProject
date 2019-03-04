package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;


import hmq.coverage.R;
import hmq.coverage.interfaces.OnGetDataInterface;
import hmq.coverage.model.Location;
import hmq.coverage.model.Model;

public class RequestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);



        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;

        setContentView(R.layout.activity_request_list);
        recyclerView = (RecyclerView) findViewById(R.id.request_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mAdapter = new RecyclerAdapter(Model.getInstance().getCurrentLocation().getRequests());
        recyclerView.setAdapter(mAdapter);






        Button checkOut = findViewById(R.id.check_out);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = Model.getInstance();
                Location location = model.getCurrentLocation();
                location.checkOut(model.getCurrentUser());
                model.updateLocation(location, new OnGetDataInterface() {
                    @Override
                    public void onDataRetrieved(DataSnapshot data) {
                        startActivity( new Intent( getApplicationContext(), HomeActivity.class));
                    }

                    @Override
                    public void onFailed() {
                        //nothing
                    }
                });
            }
        });
    }
}
