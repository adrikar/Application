package com.example.application;


import android.view.View.*;
import android.widget.*;

import com.example.application.Fragments.*;
import com.google.android.material.bottomnavigation.*;
import com.google.android.material.bottomsheet.*;
import com.google.android.material.textfield.*;
import com.google.firebase.firestore.*;

import butterknife.*;
import io.reactivex.annotations.*;

public class HomeActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    BottomSheetDialog bottomSheetDialog;

    CollectionReference userRef;

    @java.lang.Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(HomeActivity.this);


        //init
        userRef = FireBaseFireStore.getInstance().collection("User");

        if(getIntent() != null) {
            boolean isLogin = getIntent().getBooleanExtra(Common.IS_LOGIN, false);
            if(isLogin){

                AccountKit.getCurrentAccount(new AccountKitCallback<Account>(){
                    @java.lang.Override
                    public void onSuccess(Account account){
                        if(account !=null){
                            DocumentReference currentUser =userRef.document(account.getPhoneNumber().toString());
                            currentUser.get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                                        @java.lang.Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot>task){
                                                    if(task.isSuccessful()){
                                                        DocumentSnapshot userSnapshot = task.getResult();
                                                        if(!userSnapshot.exists())
                                                            showUpdateDialog(account.getPhoneNumber().toString());

                                                    }
                                                }
                                    });

                        }
                    }
                    public void onError(AccountKitError accountKitError){

                        Toast.makeText(HomeActivity.this,""+accountKitError.getErrorType().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
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
              User user = new User(edt_name.getText().toString(),edt_address.getText().toString(), phoneNumber);
              userRef.document(phoneNumber)
                      .set(user)
                      .addOnSuccessListener(new OnSuccessListener<Void>(){
                          @Override
                          public void onSuccess(Void aVoid){
                              Toast.makeText(HomeActivity.this," Gracias", Toast.LENGTH.SHORT)
                          }
                      })

            }
        })

    }

    }



