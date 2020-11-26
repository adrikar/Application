package com.example.application.Common;


import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.application.model.Salon;
import com.example.application.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import io.paperdb.Paper;

public class Common {
    public static final String KEY_ENABLE_BUTTON_NEXT = "ENABLE_BUTTON_NEXT";
    public static final String KEY_SALON_STORE = "SALON_SAVE";
    public static final String KEY_BARBER_LOAD_DONE = "BARBER_LOAD_DONE" ;
    public static final Object DISABLE_TAG = "DISABLE" ;
    public static final String KEY_TIME_SLOT = "TIME_SLOT" ;
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
    public static void updateToken(Context context, final String s){
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            MyToken myToken = MyToken();
            myToken.setToken(s);
            myToken.setTokenType(TOKEN_TYPE.CLIENT);
            myToken.setUserPhone(user.getPhoneNumber());

            FirebaseFirestore.getInstance()
                    .collection("Tokens")
                    .document(user.getPhoneNumber().toString())
                    .set(myToken)
                    .addOnCompleteListener(task -> {

                    });
        }else{
            Paper.init(context);
            String localUser = Paper.book().read(Common.LOGGED_KEY);
            if(localUser != null){
                if(!TextUtils.isEmpty(localUser)){
                    MyToken myToken = MyToken();
                    myToken.setToken(s);
                    myToken.setTokenType(TOKEN_TYPE.CLIENT);
                    myToken.setUserPhone(localUser.getPhoneNumber());

                    FirebaseFirestore.getInstance()
                            .collection("Tokens")
                            .document(localUser.getPhoneNumber().toString())
                            .set(myToken)
                            .addOnCompleteListener(task -> {

                            });
                }
            }
        }
    }


}
