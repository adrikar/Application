package com.example.application.Common;


import com.example.application.model.*;
import com.example.application.model.Rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Common {
    public static final java.lang.String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final java.lang.String KEY_REST_STORE = "REST_SAVE";
    public static final java.lang.String KEY_TABLE_LOAD_DONE = "TABLE_LOAD_DONE" ;
    public static final java.lang.String KEY_TABLE_SELECTED ="KEY_TABLE_SELECTED" ;
    public static final java.lang.String KEY_STEP ="KEY_STEP" ;
    public static final java.lang.String KEY_DISPLAY_TIME_SLOT = "DISPLAY_TIME_SLOT";
    public static final java.lang.String KEY_CONFIRM_BOOKING ="CONFIRM_BOOKING" ;
    public static final java.lang.String KEY_TIME_SLOT = "TIME_SLOT";
    public static final java.lang.String DISABLE_TAG = "DISABLE" ;
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static final int TIME_SLOT_TOTAL = 6;
    public static Rest currentRest;
    public static int step = 0;
    public static java.lang.String city ="";
    public static Table currentTable;
    public static int currentTimeSlot = -1;
    public static Calendar currentDate = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy");

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
