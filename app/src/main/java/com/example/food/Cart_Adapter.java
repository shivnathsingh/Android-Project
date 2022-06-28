package com.example.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.MyViewHolder> {
    ArrayList<OrderModal> arr;
    Context context;
    public Cart_Adapter(Context context,ArrayList<OrderModal> arr) {
        this.context=context;
        this.arr=arr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.cart_details,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_name.setText(arr.get(position).getName());
        holder.item_quantity.setText("quantity "+arr.get(position).getQuant());
        holder.totalPrice.setText("cost "+arr.get(position).getTotalPrice());
        Glide.with(holder.item_image.getContext()).load(arr.get(position).getUrl()).into(holder.item_image);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,item_quantity,totalPrice;
        ImageView item_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=itemView.findViewById(R.id.item_name);
            item_quantity=itemView.findViewById(R.id.item_quantity);
            totalPrice=itemView.findViewById(R.id.totalPrice);
            item_image=itemView.findViewById(R.id.item_image);
        }
    }
}

