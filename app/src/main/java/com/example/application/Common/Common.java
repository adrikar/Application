package com.example.application.Common;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.application.model.Barber;
import com.example.application.model.Salon;
import com.example.application.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

import io.paperdb.Paper;

public class Common {
    public static final java.lang.String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final java.lang.String KEY_SALON_STORE = "SALON_SAVE";
    public static final java.lang.String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE" ;
    public static final java.lang.String KEY_BARBER_SELECTED ="KEY_BARBER_SELECTED" ;
    public static final java.lang.String KEY_STEP ="KEY_STEP" ;
    public static final java.lang.String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final java.lang.String KEY_CONFIRM_BOOKING ="CONFIRM_BOOKING" ;
    public static final java.lang.String KEY_TIME_SLOT = "TIME_SLOT";

    public static java.lang.String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static final int TIME_SLOT_TOTAL = 6;
    public static Salon currentSalon;
    public static int step = 0;
    public static java.lang.String city ="";
    public static Barber currentBarber;
    public static int currentTimeSlot = -1;
    public static Calendar currentDate = Calendar.getInstance();

    public static java.lang.String convertTimeSlotToString(int slot) {
        switch(slot)
        {
            case 0:
                return "10:00-12:00";
            case 1:
                return "12:00-2:00";
            case 2:
                return "2:00-4:00";
            case 3:
                return "4:00-6:00";
            case 4:
                return "6:00-8:00";
            case 5:
                return "8:00-10:00";
            default:
                return "Closed";
        }
    }



}
