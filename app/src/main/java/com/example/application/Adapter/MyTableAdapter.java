package com.example.application.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.*;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.Common.*;
import com.example.application.Interface.*;
import com.example.application.R;
import com.example.application.model.Table;

import java.util.ArrayList;
import java.util.List;

public class MyTableAdapter extends RecyclerView.Adapter<MyTableAdapter.MyViewHolder> {
    Context context;
    List<Table>tableList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyTableAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.tableList = tableList;
        cardViewList = new ArrayList<>();
        localBroadcastManager =LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_table, viewGroup,false);
        return new MyTableAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_table_name.setText(tableList.get(i).getName());
        myViewHolder.ratingBar.setRating((float)tableList.get(i).getRating());
        if(!cardViewList.contains(myViewHolder.card_table))
            cardViewList.add(myViewHolder.card_table);

        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @java.lang.Override
            public void onItemSelectedListener(View view, int pos) {
                for(CardView cardView :cardViewList)
                {
                    cardView.setCardBackgroundColor(context.getResources()
                            .getColor(android.R.color.white));
                }
                myViewHolder.card_table.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));

                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_TABLE_SELECTED, tableList.get(pos));
                intent.putExtra(Common.KEY_STEP, 2);
                localBroadcastManager.sendBroadcast(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_table_name;
        RatingBar ratingBar;
        CardView card_table;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_table=(CardView)itemView.findViewById(R.id.card_table);
            txt_table_name = (TextView)itemView.findViewById(R.id.txt_table_name);
            ratingBar = (RatingBar)itemView.findViewById(R.id.rtb_table);
            itemView.setOnClickListener(this::onCLick);
        }

        public void onCLick(View view){
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }
    }
}
