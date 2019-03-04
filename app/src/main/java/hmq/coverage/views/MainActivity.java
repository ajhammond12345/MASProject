package hmq.coverage.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import hmq.coverage.R;
import hmq.coverage.model.Model;
import hmq.coverage.model.User;

public class MainActivity extends AppCompatActivity {

    // Class members
    private FirebaseAuth mAuth;
    private EditText password;
    private EditText email;
    private Button button_register;
    private Button button_login;

    // Class methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Model model = Model.getInstance();
        model.getLocations();
        model.getRequests();
        FirebaseApp.initializeApp(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.signup_email_input);
        password = (EditText) findViewById(R.id.signup_password_input);
        button_register = (Button) findViewById(R.id.button_register);
        button_login = (Button) findViewById(R.id.button_login);
        mAuth = FirebaseAuth.getInstance();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( view == button_register ) {
                    registerUser();
                }
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( view == button_login ) {
                    startActivity( new Intent( getApplicationContext(), LoginActivity.class));
                }
            }
        });


    }

    public void registerUser() {
        final String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        if( TextUtils.isEmpty( userEmail ) ) {
            Toast.makeText(this, "Email field is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if( TextUtils.isEmpty( userPassword ) ) {
            Toast.makeText(this, "Password field is empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword( userEmail, userPassword )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    // Check if successful
                    if ( task.isSuccessful() ) {
                        // User is successfully registered and logged in; begin profile activity
                        Toast.makeText( MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        User user = new User(userEmail);
                        Model.getInstance().setCurrentUser(user);
                        finish();
                        startActivity( new Intent( getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText( MainActivity.this, "Registration Unsuccessful. Try Again", Toast.LENGTH_SHORT).show();
                    }
                } catch ( Exception e ) {
                    e.printStackTrace();
                }

            }
        });


    }
}
