package com.example.application.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.application.Common.Common;
import com.example.application.R;
import com.example.application.model.BookingInformation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.application.Common.Common.KEY_CONFIRM_BOOKING;

public class BookingStep4Fragment extends Fragment {

    SimpleDateFormat simpleDateFormat;
    LocalBroadcastManager localBroadcastManager;
    Unbinder unbinder;

    @BindView(R.id.txt_booking_barber_text)
    TextView txt_booking_barber_text;
    @BindView(R.id.txt_booking_time_text)
    TextView txt_booking_time_text;
    @BindView(R.id.txt_rest_name)
    TextView txt_rest_name;
    @BindView(R.id.txt_rest_address)
    TextView txt_rest_address;
    @BindView(R.id.txt_rest_open_hours)
    TextView txt_rest_open_hours;
    @BindView(R.id.txt_rest_phone)
    TextView txt_rest_phone;
    @BindView(R.id.txt_rest_website)
    TextView txt_rest_website;

    @OnClick(R.id.btn_confirm)
            void confirmBooking(){
        BookingInformation bookingInformation = new BookingInformation();

        bookingInformation.setBarberId(Common.currentBarber.getBarberId());
        bookingInformation.setBarberName(Common.currentBarber.getName());
        bookingInformation.setCustomerName(Common.currentUser.getName());
        bookingInformation.setCustomerPhone(Common.currentUser.getPhoneNumber());
        bookingInformation.setRestId(Common.currentRest.getRestId());
        bookingInformation.setRestName(Common.currentRest.getName());
        bookingInformation.setRestAddress(Common.currentRest.getAddress());
        bookingInformation.setTime(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
                .append(" at ")
                .append(simpleDateFormat.format(Common.currentDate.getTime())).toString());
        bookingInformation.setSlot(Long.valueOf(Common.currentTimeSlot));



        DocumentReference bookingDate =  FirebaseFirestore.getInstance()
                .collection("AllRest")
                .document(Common.city)
                .collection("Branch")
                .document(Common.currentRest.getRestId())
                .collection("Barber")
                .document(Common.currentBarber.getBarberId())
                .collection(Common.simpleDateFormat.format(Common.currentDate.getTime()))
                .document(String.valueOf(Common.currentTimeSlot));

        bookingDate.set(bookingInformation)
                .addOnSuccessListener(aVoid -> {

                    resetStaticData();
                    getActivity().finish();
                    Toast.makeText(getContext(),"Success!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void resetStaticData() {
        Common.step = 0;
        Common.currentTimeSlot = -1;
        Common.currentRest = null;
        Common.currentBarber = null;
        Common.currentDate.add(Calendar.DATE, 0);
    }

    BroadcastReceiver confirmBookingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setData();
        }
    };

    private void setData() {
        txt_booking_barber_text.setText(Common.currentBarber.getName());
        txt_booking_time_text.setText(new StringBuilder(Common.convertTimeSlotToString(Common.currentTimeSlot))
        .append(" at ")
        .append(simpleDateFormat.format(Common.currentDate.getTime())));

        txt_rest_address.setText(Common.currentRest.getAddress());
        txt_rest_website.setText(Common.currentRest.getWebsite());
        txt_rest_name.setText(Common.currentRest.getName());
        txt_rest_open_hours.setText(Common.currentRest.getOpenHours());
    }

    static BookingStep4Fragment instance;
    public static BookingStep4Fragment getInstance(){
        if (instance == null)
            instance = new BookingStep4Fragment();
        return instance;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(confirmBookingReceiver, new IntentFilter(KEY_CONFIRM_BOOKING));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(confirmBookingReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
       View itemView = inflater.inflate(R.layout.fragment_booking_step_four, container,false);
       unbinder = ButterKnife.bind(this, itemView);
        return itemView;
    }

}
