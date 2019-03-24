/**
 * @author Stuart Malone
 */
package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hmq.coverage.R;
import hmq.coverage.model.Model;

public class HomeActivity extends AppCompatActivity {

    Button getCoverage;
    Button getCoverage1;
    Button cover;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Model.getInstance().getLocations();

        getCoverage = findViewById(R.id.getCoverage);
        getCoverage1 = findViewById(R.id.getCoverage1);
        cover = findViewById(R.id.cover);
        profile = findViewById(R.id.profile_button);

        getCoverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == getCoverage) {
                    startActivity( new Intent( getApplicationContext(), CreateRequestActivity.class ) );
                }
            }
        });

        getCoverage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == getCoverage1) {
                    startActivity( new Intent( getApplicationContext(), CreateRequestActivity.class ) );
                }
            }
        });

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == cover) {
                    startActivity( new Intent( getApplicationContext(), CheckInActivity.class ) );
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == profile) {
                    startActivity( new Intent( getApplicationContext(), ProfileActivity.class ) );
                }
            }
        });
    }
}
