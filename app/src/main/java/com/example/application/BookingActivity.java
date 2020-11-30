package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.example.application.Adapter.MyViewPagerAdapter;
import com.example.application.Common.Common;
import com.example.application.Common.NonSwipeViewPager;
import com.example.application.model.Table;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class BookingActivity extends AppCompatActivity {

    LocalBroadcastManager localBroadcastManager;
    AlertDialog dialog;
    CollectionReference tableRef;

    @BindView(R.id.step_view)
    StepView stepView;
    @BindView(R.id.view_pager)
    NonSwipeViewPager viewPager;
    @BindView(R.id.btn_previous_step)
    Button btn_previous_step;
    @BindView(R.id.btn_next_step)
    Button btn_next_step;

    @OnClick(R.id.btn_previous_step)
    void previousStep(){
        if(Common.step == 3 || Common.step > 0){}
        Common.step--;
        viewPager.setCurrentItem(Common.step);
        if(Common.step < 3){
            btn_next_step.setEnabled(true);
            setColorButton();
        }
    }

    @OnClick(R.id.btn_next_step)
    void nextClick(){
        if (Common.step < 3 || Common.step == 0){
            Common.step++;
            if(Common.step == 1){
                if(Common.currentRest != null){
                    loadTableByRest(Common.currentRest.getRestId());
                }
            }
            else if(Common.step == 2)
            {
                if(Common.currentTable !=null)
                    loadTimeSlotOfTable(Common.currentTable.getTableId());
            }
            else if(Common.step == 2)
            {
                if(Common.currentTimeSlot != -1)
                    confirmBooking();
            }
                viewPager.setCurrentItem(Common.step);

        }
    }

    private void confirmBooking() {
        Intent intent = new Intent(Common.KEY_CONFIRM_BOOKING);
        localBroadcastManager.sendBroadcast(intent);
     }
  private void loadTimeSlotOfTable(String tableId){
        Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void loadTableByRest(String restId) {
        dialog.show();

        if(!TextUtils.isEmpty(Common.city)){
            tableRef = FirebaseFirestore.getInstance()
                    .collection("AllRest")
                    .document(Common.city)
                    .collection("Branch")
                    .document(restId)
                    .collection("Table");

            tableRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            ArrayList<Table> tables = new ArrayList<>();
                            for(QueryDocumentSnapshot tableSnapshot:task.getResult()){
                                    Table table = tableSnapshot.toObject(Table.class);
                                    table.setPassword("");
                                    table.setTableId(tableSnapshot.getId());

                                    tables.add(table);
                            }
                            Intent intent = new Intent(Common.KEY_TABLE_LOAD_DONE);
                            intent.putParcelableArrayListExtra(Common.KEY_TABLE_LOAD_DONE, tables);
                            localBroadcastManager.sendBroadcast(intent);

                            dialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });
        }
    }
    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int step = intent.getIntExtra(Common.KEY_STEP,0);
            if (step ==1)
                Common.currentRest = intent.getParcelableExtra(Common.KEY_REST_STORE);
            else if(step == 2)
                Common.currentTable = intent.getParcelableExtra(Common.KEY_TABLE_SELECTED);
            else if(step == 3)
                Common.currentTimeSlot = intent.getIntExtra((String) Common.KEY_TIME_SLOT, -1);

            btn_next_step.setEnabled(true);
            setColorButton();
        }
    };



    @Override
    protected void onDestroy(){
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        ButterKnife.bind(BookingActivity.this);

        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver, new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));
        
        setupStepView();
        setColorButton();

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int il) {

            }

            @Override
            public void onPageSelected(int i) {

                stepView.go(i, true);

                if(i == 0)
                    btn_previous_step.setEnabled(false);
                else
                    btn_previous_step.setEnabled(true);
                btn_next_step.setEnabled(false);
                setColorButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setColorButton() {
        if(btn_next_step.isEnabled()){
            btn_next_step.setBackgroundResource(R.color.colorButton);
        }
        else {
            btn_next_step.setBackgroundResource(R.color.colorPrimaryDark);
        }
        if(btn_previous_step.isEnabled()){
            btn_previous_step.setBackgroundResource(R.color.colorButton);
        }
        else {
            btn_previous_step.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }

    private void setupStepView() {
        List<String>stepList = new ArrayList<>();
        stepList.add("Salon");
        stepList.add("Table");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }
}