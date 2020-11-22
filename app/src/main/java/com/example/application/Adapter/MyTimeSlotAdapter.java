package com.example.application.Adapter;

import androidx.recyclerview.widget.*;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;

import androidx.cardview.widget.*;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.*;
import com.example.application.Common.*;
import com.example.application.model.*;
import com.example.application.R;
import com.google.api.*;

import java.util.*;


import io.reactivex.annotations.*;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {

    Context context;
    List<TimeSlot> timeSlotList;

    public MyTimeSlotAdapter(Context context){
        this.context=context;
        this.timeSlotList= new ArrayList<>();
    }

    public MyTimeSlotAdapter(Context context, List<TimeSlot> timeSlotList){
        this.context =context;
        this.timeSlotList= timeSlotList;
    }
    @NonNull
    @java.lang.Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_time_slot,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @java.lang.Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(i)).toString());
        if(timeSlotList.size() == 0) //si hay lugar disponible
        {
            myViewHolder.txt_time_slot_description.setText("Available");
            myViewHolder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.black));
            myViewHolder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.black));
            myViewHolder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
        else //no esta disponible
        {
            for(TimeSlot slotValue:timeSlotList){
                int slot =Integer.parseInt(slotValue.getSlot().toString());
                if(slot ==i)
                {
                    myViewHolder.card_time_slot.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));

                    myViewHolder.txt_time_slot_description.setText("Lleno");
                    myViewHolder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.white));
                    myViewHolder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.white));
                }
            }
        }
    }

    @java.lang.Override
    public int getItemCount() {
        return Common.TIME_SLOT_TOTAL;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_time_slot,txt_time_slot_description;
        CardView card_time_slot;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            card_time_slot=(CardView)itemView.findViewById(R.id.card_time_slot);
            txt_time_slot=(TextView)itemView.findViewById(R.id.txt_time_slot);
            txt_time_slot_description=(TextView)itemView.findViewById(R.id.txt_time_slot_description);


        }
    }
}
