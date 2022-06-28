package com.example.food;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Restro_Adapter extends RecyclerView.Adapter<Restro_Adapter.MyViewHolder>{
    Activity context;
    List<DataModal> list;

    public Restro_Adapter(Activity context, List<DataModal> dataList){
        list = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.restro_details_new,parent,false);
        return new MyViewHolder(view);
    }

    public void updateList(List<DataModal> data) {
        list = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataModal model = list.get(position);
        holder.restro_name.setText(model.getName());
        holder.restro_address.setText(model.getAddress());
        holder.restro_rating.setText(model.getRating());
        holder.restro_time.setText(model.getTime());
        //holder.restro_description.setText(model.getDescription());
        Glide.with(holder.restro_image.getContext()).load(model.getUrl()).into(holder.restro_image);
        holder.myCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Menu_Page.class);
                intent.putExtra("ID",model.getID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView restro_name,restro_address,restro_rating,restro_time,restro_description;
        ImageView restro_image;
        //ConstraintLayout restro_frame;
        CardView myCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            restro_name=itemView.findViewById(R.id.restro_name);
            restro_address=itemView.findViewById(R.id.restro_address);
            restro_rating=itemView.findViewById(R.id.restro_rating);
            restro_time=itemView.findViewById(R.id.restro_time);
            restro_description=itemView.findViewById(R.id.restro_description);
            restro_image=itemView.findViewById(R.id.restro_image);
            //restro_frame=itemView.findViewById(R.id.restro_frame);
            myCard=itemView.findViewById(R.id.myCard);
        }
    }
}
