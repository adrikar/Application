package com.example.application.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.Common.Common;
import com.example.application.Interface.IRecyclerItemSelectedListener;
import com.example.application.R;
import com.example.application.model.*;
import com.example.application.model.Rest;

import java.util.ArrayList;
import java.util.List;

public class MyRestAdapter extends RecyclerView.Adapter<MyRestAdapter.MyViewHolder> {

    Context context;
    List<Rest> restList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyRestAdapter(Context context, List<Rest> restList) {
        this.context = context;
        this.restList = restList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_rest, viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_rest_name.setText(restList.get(i).getName());
        myViewHolder.txt_rest_address.setText(restList.get(i).getAddress());
        if(!cardViewList.contains(myViewHolder.card_rest))
            cardViewList.add(myViewHolder.card_rest);
        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                for (CardView cardView:cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                myViewHolder.card_rest.setCardBackgroundColor(context.getResources()
                .getColor(android.R.color.holo_orange_dark));


                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_REST_STORE, restList.get(pos));
                intent.putExtra(Common.KEY_STEP,1);
                localBroadcastManager.sendBroadcast(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return restList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_rest_name, txt_rest_address;
        CardView card_rest;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           card_rest= (CardView)itemView.findViewById(R.id.card_rest);
            txt_rest_address = (TextView)itemView.findViewById(R.id.txt_rest_address);
            txt_rest_name = (TextView)itemView.findViewById(R.id.txt_rest_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelectedListener(view, getAdapterPosition());
        }
    }
}