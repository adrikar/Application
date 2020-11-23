package com.example.application.Common;


import android.os.Parcelable;

import com.example.application.model.Salon;
import com.example.application.model.User;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE" ;
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static final int TIME_SLOT_TOTAL = 6;
    public static Salon currentSalon;
    public static int step = 0;
    public static String city ="";

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
