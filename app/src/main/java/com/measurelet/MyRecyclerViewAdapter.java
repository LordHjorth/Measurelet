package com.measurelet;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hjorth.measurelet.R;
import com.measurelet.Model.Intake;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;
    private List<Intake> mdata;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<Intake> mdata) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mdata = mdata;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.registration_standard_proeve, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextView.setText(mdata.get(position).getType());
        holder.myPicture_thumbnail.setImageResource(mdata.get(position).getThumbnail());
        holder.myMaengde.setText(mdata.get(position).getSize()+ " ml");
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mdata.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView myMaengde;
        ImageView myPicture_thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item_label);
            myPicture_thumbnail = itemView.findViewById(R.id.item_picture);
            myMaengde = itemView.findViewById(R.id.itemmaengde);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}