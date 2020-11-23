package com.example.application.Common;


public class Common {
    public static String IS_LOGIN = "IsLogin";
    public static User currentUser;
    public static final int TIME_SLOT_TOTAL = 6;

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
