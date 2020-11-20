package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.application.Common.Common;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.bottom_Navegation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);

        userRef = FirebaseFirestore.getInstance().collection("Users");

        if(getIntent() != null){
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if(isLogin){
                AccountKit.getCurrentAccount(new Accon¿utnKitCallback<Account>()
                @Override
                public void onSuccess(Account account){
                    DocumentReference currentUser = userRef.document(account.getPhoneNumber().toString());
                    currentUser
                }
                @Override
                public void onError(AccountKitError accountKitError){
                    Toast.makeText(HomeActivity.this,""+accountKitError.getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

}