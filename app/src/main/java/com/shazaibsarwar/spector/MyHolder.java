package com.shazaibsarwar.spector;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder  extends RecyclerView.ViewHolder  implements View.OnClickListener{

    // Our View

    ImageView img;
    TextView nameText;

    ItemClickListner itemClickListner;


    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.img = itemView.findViewById(R.id.modelImage);
        this.nameText = itemView.findViewById(R.id.modelText);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        this.itemClickListner.onItemClicked(v, getLayoutPosition());
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}
