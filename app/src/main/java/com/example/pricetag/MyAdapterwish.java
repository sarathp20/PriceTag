package com.example.pricetag;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MyAdapterwish extends RecyclerView.Adapter<MyAdapterwish.MyAdapterViewHolder>{


    public static final String EXTRA_MESSAGE="MainActivityLabel";
    Productwish[] product;
    ViewGroup parent;
    public MyAdapterwish(Productwish[] product){
        this.product=product;
    }
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.product_list1,parent,false);
        return new MyAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        final Productwish p = product[position];
        holder.item.setText(p.getTitle());
        holder.price.setText("₹"+p.getPrice());
        // holder.site.setText(p.getsite());
        String site = p.getsite();
        if (site.equals("Flipkart")) {
            holder.logo.setImageResource(R.drawable.flipkartlogo);
        } else if (site.equals("Snapdeal")) {
            holder.logo.setImageResource(R.drawable.snapdeallogo);
        }
        holder.rating.setText(p.getRating());
        holder.itemid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),ItemDisplay.class);
                intent.putExtra("url",p.getlink());
                intent.putExtra("site",p.getsite());
                parent.getContext().startActivity(intent);
            }

        });
        if(!p.getImage().equals(""))
            Glide.with(parent.getContext()).load(p.getImage()).into(holder.image);

    }



    @Override
    public int getItemCount() {
        return product.length;
    }


    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{
        LinearLayout itemid;
        TextView item;
        TextView price;
        ImageView image;
        ImageView logo;
        TextView rating;
        public MyAdapterViewHolder(View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.textViewTitle);
            price = itemView.findViewById(R.id.textViewPrice);
            image = itemView.findViewById(R.id.imageView);
            logo = itemView.findViewById(R.id.logo);
            rating = itemView.findViewById(R.id.textViewRating);
            itemid = itemView.findViewById(R.id.itemid);
        }
    }

}
