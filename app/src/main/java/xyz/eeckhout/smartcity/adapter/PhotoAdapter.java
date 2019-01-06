package xyz.eeckhout.smartcity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import xyz.eeckhout.smartcity.R;
import xyz.eeckhout.smartcity.controller.ServiceFragment;
import xyz.eeckhout.smartcity.model.PhotoDTO;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private List<PhotoDTO> mDataset;
    private ServiceFragment fragment;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mTextView;
        public MyViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.photo_layout_photo);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhotoAdapter(List<PhotoDTO> myDataset, ServiceFragment fragment) {
        mDataset = myDataset;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Glide.with(fragment.getContext())
                .load(mDataset.get(position).getUrl())
                .into(holder.mTextView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }
}