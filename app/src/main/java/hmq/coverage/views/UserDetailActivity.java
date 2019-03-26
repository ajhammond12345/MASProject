package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hmq.coverage.R;
import hmq.coverage.model.Model;
import hmq.coverage.model.User;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        User user = Model.getInstance().getSelectedUser();

        TextView name = findViewById(R.id.user_detail_name);
        TextView email = findViewById(R.id.user_detail_email);

        name.setText(user.getFname() + " " + user.getLname());
        email.setText(user.getEmail());

        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getApplicationContext(), HomeActivity.class));
            }
        });

    }
}
