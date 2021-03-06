package com.example.application;


import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.application.Common.Common;
import com.example.application.Fragments.*;
import com.example.application.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.*;
import com.google.android.material.bottomsheet.*;
import com.google.android.material.textfield.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import butterknife.*;
import dmax.dialog.SpotsDialog;
import io.reactivex.annotations.*;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);


        //init
        userRef = FirebaseFirestore.getInstance().collection("User");
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        if(getIntent() != null) {
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if(isLogin){
                dialog.show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            DocumentReference currentUser =userRef.document(user.getPhoneNumber());
                            currentUser.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                                        @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                                    if(task.isSuccessful()) {

                                                        DocumentSnapshot userSnapshot = task.getResult();
                                                        if (!userSnapshot.exists()) {
                                                            showUpdateDialog(user.getPhoneNumber());
                                                        }else{
                                                            Common.currentUser = userSnapshot.toObject(User.class);
                                                            bottomNavigationView.setSelectedItemId(R.id.action_home);
                                                        }
                                                        if (dialog.isShowing()) {
                                                            dialog.dismiss();

                                                        }
                                                    }
                                                }
                                    });


                }
            }



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           Fragment fragment = null;

            @java.lang.Override
            public boolean onNavigationItemSelected(android.view.MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_home)
                    fragment = new HomeFragment();
                else if(menuItem.getItemId() == R.id.action_shopping)
                    fragment = new ShoppingFragment();
                return loadFragment(fragment);
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);



    }
    private boolean loadFragment(Fragment fragment){
        if(fragment !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }
    private void showUpdateDialog(final String phoneNumber){


        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setCancelable(false);
        View sheetView =getLayoutInflater().inflate(R.layout.layout_update_information,null);
        Button btn_update = (Button)sheetView.findViewById(R.id.btn_update);
        final TextInputEditText edt_name =(TextInputEditText)sheetView.findViewById(R.id.edt_name);
        final TextInputEditText edt_address =(TextInputEditText)sheetView.findViewById(R.id.edt_address);

        btn_update.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

              final User user = new User(edt_name.getText().toString(),edt_address.getText().toString(), phoneNumber);
              userRef.document(phoneNumber)
                      .set(user)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              bottomSheetDialog.dismiss();
                              if (dialog.isShowing())
                                  dialog.dismiss();

                              Common.currentUser = user;
                              bottomNavigationView.setSelectedItemId(R.id.action_home);
                              Toast.makeText(HomeActivity.this, " Gracias", Toast.LENGTH_SHORT).show();

                          }
                      }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@androidx.annotation.NonNull Exception e) {
                      bottomSheetDialog.dismiss();
                      Toast.makeText(HomeActivity.this,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                  }
              });

                }


        });
    bottomSheetDialog.setContentView(sheetView);
    bottomSheetDialog.dismiss();
    }

    }



