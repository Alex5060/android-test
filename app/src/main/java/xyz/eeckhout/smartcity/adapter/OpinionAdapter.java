package xyz.eeckhout.smartcity.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;

import java.util.List;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.controller.ServiceFragment;
import xyz.eeckhout.smartcity.model.ServiceOpinionDTO;

public class OpinionAdapter extends RecyclerView.Adapter<OpinionAdapter.MyViewHolder> {
    private List<ServiceOpinionDTO> mDataset;
    private String uid;
    private String userRoles;
    private ServiceFragment fragment;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mAuthorView;
        public TextView mTextView;
        public ImageButton mButtonSignalView;
        public ImageButton mButtonDeleteView;
        public MyViewHolder(View v) {
            super(v);
            mAuthorView = v.findViewById(R.id.opinion_author);
            mTextView = v.findViewById(R.id.opnion_text);
            mButtonDeleteView = v.findViewById(R.id.opinion_button_remove);
            mButtonSignalView = v.findViewById(R.id.opinion_button_lock);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OpinionAdapter(List<ServiceOpinionDTO> myDataset, String token, ServiceFragment fragment) {
        mDataset = myDataset;
        JWT jwt = new JWT(token);
        uid = jwt.getClaim("uid").asString();
        userRoles = jwt.getClaim("roles").asString();
        Log.i("erreur", uid);
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OpinionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.opinion_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mAuthorView.setText(mDataset.get(position).getWriter().getUserName());
        holder.mTextView.setText(mDataset.get(position).getComment());

        holder.mButtonSignalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateOpinion(mDataset.get(position));
            }
        });

        if(mDataset.get(position).getWriter().getId().equals(uid) ||
                userRoles.contains("Gestionnaire") ||
                userRoles.contains("Admin")) {
            holder.mButtonDeleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.deleteOpinion(mDataset.get(position));
                }
            });
            holder.mButtonSignalView.setVisibility(View.INVISIBLE);
        }
        else {
            holder.mButtonDeleteView.setVisibility(View.INVISIBLE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }
}