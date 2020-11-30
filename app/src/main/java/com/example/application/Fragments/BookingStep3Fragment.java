package com.example.application.Fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.api.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.*;

import com.example.application.Adapter.*;
import com.example.application.Common.*;
import com.example.application.model.*;
import com.google.android.gms.stats.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.api.*;
import com.google.firebase.firestore.*;

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



BroadcastReceiver displayTimeSlot = new BroadcastReceiver() {
    @Override
    public void onReceive(android.content.Context context, Intent intent) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 0);
        loadAvailableTimeSlotOfBarber(Common.currentBarber.getBarberId(),
                simpleDateFormat.format(date.getTime()));
    }
};



    private void loadAvailableTimeSlotOfBarber(String barberId, java.lang.String bookDate){
        dialog.show();

        barberDoc= FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document(Common.city)
                .collection("Branch")
                .document(Common.currentSalon.getSalonId())
                .collection("Barber")
                .document(Common.currentBarber.getBarberId());

        barberDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot>task){
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if(documentSnapshot.exists())
                    {
                        CollectionReference date =FirebaseFirestore.getInstance()
                                .collection("AllSalon")
                                .document(Common.city)
                                .collection("Branch")
                                .document(Common.currentSalon.getSalonId())
                                .collection("Barber")
                                .document(Common.currentBarber.getBarberId())
                                .collection(bookDate);
                        date.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                            @Override
                            public  void onComplete(@NonNull Task<QuerySnapshot>task){
                                if(task.isSuccessful()){
                                    QuerySnapshot querySnapshot=task.getResult();
                                    if(querySnapshot.isEmpty())
                                        iTimeSlotLoadListener.onTimeSlotLoadEmpty();
                                    else{
                                        List<TimeSlot> timeSlots = new ArrayList<>();
                                        for(QueryDocumentSnapshot document:task.getResult())
                                            timeSlots.add(document.toObject(TimeSlot.class));
                                        iTimeSlotLoadListener.onTimeSlotLoadSuccess(timeSlots);
                                    }
                                }
                            }
                    }).addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e){
                            iTimeSlotLoadListener.onTimeSlotLoadFailed(e.getMessage());
                        }
                    });
                    }

                }
            }

        });
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
                    loadAvailableTimeSlotOfBarber(Common.currentBarber.getBarberId(),
                            simpleDateFormat.format(date.getTime()));

                }
            }
        });
    }

   public void onTimeSlotLoadSuccess(List<TimeSlot> timeSlotList){
       MyTimeSlotAdapter adapter= new MyTimeSlotAdapter(getContext(), timeSlotList);
       recycler_time_slot.setAdapter(adapter);

       dialog.dismiss();
    }

    public void onTimeSlotLoadFailed(java.lang.String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    public void onTimeSlotLoadEmpty(){
        MyTimeSlotAdapter adapter= new MyTimeSlotAdapter(getContext());
        recycler_time_slot.setAdapter(adapter);

        dialog.dismiss();
    }


}
