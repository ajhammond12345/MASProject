package hmq.coverage.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;

import hmq.coverage.R;
import hmq.coverage.interfaces.OnGetDataInterface;
import hmq.coverage.model.Location;
import hmq.coverage.model.Model;
import hmq.coverage.model.Request;
import hmq.coverage.model.User;

public class UserListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserRecyclerAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;
            User selected = Model.getInstance().getCurrentLocation().getCheckedIn().get(position);
            Model.getInstance().setSelectedUser(selected);
            startActivity( new Intent( getApplicationContext(), UserDetailActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        recyclerView = (RecyclerView) findViewById(R.id.user_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mAdapter = new UserRecyclerAdapter(Model.getInstance().getCurrentLocation().getCheckedIn());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(onItemClickListener);






        Button exit = findViewById(R.id.finished);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent( getApplicationContext(), HomeActivity.class));
            }
        });
    }
}
