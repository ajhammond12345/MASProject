/**
 * @author Stuart Malone
 */

package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hmq.coverage.R;
import hmq.coverage.model.Model;
import hmq.coverage.model.User;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private User user;
    private TextView Email;
    private TextView PhoneText;
    private TextView NameText;
//    private TextView Uid;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Email = (TextView) findViewById( R.id.profileEmail );
        PhoneText = (TextView) findViewById( R.id.profile_phone );
        NameText = (TextView) findViewById( R.id.profile_name );
//        Uid = (TextView) findViewById( R.id.profileUid );
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById( R.id.button_logout );
        user = Model.getInstance().getCurrentUser();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( view == logout ) {
                    if ( user != null ) {
                        mAuth.signOut();
                        startActivity( new Intent( getApplicationContext(), LoginActivity.class ) );
                    }
                }
            }
        });

        if ( user != null ) {
            String email = user.getEmail();
            Email.setText( email );
            String name = user.getName();
            NameText.setText( name );
            String phone = user.getPhone();
            PhoneText.setText( phone );
        }
    }
}
