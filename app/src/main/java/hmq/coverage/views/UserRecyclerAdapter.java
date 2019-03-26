package hmq.coverage.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hmq.coverage.R;
import hmq.coverage.model.Request;
import hmq.coverage.model.User;

//Most code from Firebase official docs
//onClickListener code from: https://github.com/RohitSurwase/RvClickListenerExample/blob/master/app/src/main/java/com/rohitss/rvclick/MyRecyclerAdapter.java

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.MyViewHolder> {
    private List<User> mDataset;
    private View.OnClickListener mOnItemClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
            textView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    public void setmOnItemClickListener(View.OnClickListener listener) {
        mOnItemClickListener = listener;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserRecyclerAdapter(List<User> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position).toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
