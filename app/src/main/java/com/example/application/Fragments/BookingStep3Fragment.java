package com.example.application.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.*;

import com.example.application.Common.*;
import com.google.android.gms.stats.*;
import com.google.api.*;
import com.google.firebase.firestore.DocumentReference;

import butterknife.*;
import butterknife.BindView;
import devs.mulham.horizontalcalendar.*;
import devs.mulham.horizontalcalendar.utils.*;
import dmax.dialog.*;

import com.example.application.R;
import com.example.application.Interface.ITimeSlotLoadListener;



import com.example.application.R;

public class BookingStep3Fragment extends Fragment {
    DocumentReference barberDoc;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    AlertDialog dialog;

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;



    @BindView(R.id.recycler_time_slot)
    RecyclerView recycler_time_slot;
    @BindView(R.id.calendarView)
    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;


        @Override
        public void onReceive(Context context, Intent intent){
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, 0);
            loadAvailableTimeSlotofBarber(Common.currentBarber.getBarberId(),
                    simpleDateFormat.format(date.getTime()));
        }



    private void loadAvailableTimeSlotofBarber(String barberID, String date){
        dialog.show();
    }


    static BookingStep3Fragment instance;
    public static BookingStep3Fragment getInstance(){
        if (instance == null)
            instance = new BookingStep3Fragment();
        return instance;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iTimeSlotLoadListener = (ITimeSlotLoadListener) this;

        localBroadcastManager= LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(displayTimeSlot, new IntentFilter(Common.KEY_DISPLAY_TIME_SLOT));
        simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(false).build();

    }
    @Override
    public void onDestroy(){
        localBroadcastManager.unregisterReceiver(displayTimeSlot);
        super.onDestroy();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View itemView = inflater.inflate(R.layout.fragment_booking_step_three,container,false);
        unbinder = ButterKnife.bind(this,itemView);
        init(itemView);

        return itemView;
    }

    private void init(View itemView) {
        recycler_time_slot.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recycler_time_slot.setLayoutManager(gridLayoutManager);
        recycler_time_slot.addItemDecoration(new SpacesItemDecoration(8));

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE,0);
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DATE,2);

        HorizontalCalendar horizontalCalendar= new HorizontalCalendar.Builder(itemView,R.id.calendarView)
                .range(startDate,endDate)
                .datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(startDate)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @java.lang.Override
            public void onDateSelected(java.util.Calendar date, int position) {
                if(Common.currentDate.getTimeInMillis() !=date.getTimeInMillis()){
                    Common.currentDate = date;
                    loadAvailableTimeSlotofBarber(Common.currentBarber.getBarberId(),
                            simpleDateFormat.format(date.getTime()));

                }
            }
        });
    }
}
