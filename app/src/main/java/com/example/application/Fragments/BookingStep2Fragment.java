package com.example.application.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.Adapter.MyTableAdapter;
import com.example.application.Common.Common;
import com.example.application.Common.SpacesItemDecoration;
import com.example.application.R;
import com.example.application.model.Table;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;

   // @BindView(R.id.recycler_table)
    RecyclerView recycler_table;

    private BroadcastReceiver tableDoneReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Table> tableArrayList = intent.getParcelableArrayListExtra(Common.KEY_TABLE_LOAD_DONE);

            MyTableAdapter adapter = new MyTableAdapter(getContext(), tableArrayList);
            recycler_table.setAdapter(adapter);
        }
    };

    static BookingStep2Fragment instance;
    public static BookingStep2Fragment getInstance(){
        if (instance == null)
            instance = new BookingStep2Fragment();
            return instance;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(tableDoneReceiver, new IntentFilter(Common.KEY_TABLE_LOAD_DONE));
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(tableDoneReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
       View itemView = inflater.inflate(R.layout.fragment_booking_step_two, container,false);

       unbinder = ButterKnife.bind(this, itemView);

       initView();

       return itemView;
    }

    private void initView() {
        recycler_table.setHasFixedSize(true);
        recycler_table.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recycler_table.addItemDecoration(new SpacesItemDecoration(4));
    }
}
