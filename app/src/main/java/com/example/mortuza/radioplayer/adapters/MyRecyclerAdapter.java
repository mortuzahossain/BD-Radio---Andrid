package com.example.mortuza.radioplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mortuza.radioplayer.R;
import com.example.mortuza.radioplayer.model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    Context context;
    List<DataModel> myDataModels;

    public MyRecyclerAdapter(Context context, List<DataModel> myDataModel) {
        this.context = context;
        this.myDataModels = myDataModel;
    }

    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {
        Picasso.get()
                .load(myDataModels.get(position).getImage())
                .into(holder.logo);
        holder.name.setText(myDataModels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return myDataModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView logo;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            logo = itemView.findViewById(R.id.logo);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
