/**
 * @author Stuart Malone
 */

package hmq.coverage.views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

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
    private SignInButton googleSignInBtn;
    private final static int RC_SIGN_IN = 2;
    private GoogleSignInClient mGoogleSignInClient;

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
        googleSignInBtn = (SignInButton) findViewById(R.id.google_sign_in_button);

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

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("944297071608-s3peqvousrbic0konnk4072nnqrf6bqm.apps.googleusercontent.com")//(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // TODO: NEED TO REMOVE THE HARDCODED IdToken from google.json
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent googleSignInIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                            startActivity(googleSignInIntent);
                            finish();
                            Toast.makeText(getApplicationContext(), "User logged in successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                            Toast.makeText(getApplicationContext(), "Could not log in user.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
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
