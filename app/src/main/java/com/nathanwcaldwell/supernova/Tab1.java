package com.nathanwcaldwell.supernova;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Tab1 extends ListFragment {

    // declare class variables
    private ArrayList<StoreItem> m_parts = new ArrayList<StoreItem>();
    private Runnable viewParts;
    private StoreItemAdapter m_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab,container,false);

        // here we are defining our runnable thread.
        viewParts = new Runnable(){
            public void run(){
                handler.sendEmptyMessage(0);
            }
        };

        // here we call the thread we just defined - it is sent to the handler below.
        Thread thread =  new Thread(null, viewParts, "StoreItemThread");
        thread.start();

        return v;
    }

    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            // create some objects
            // here is where you could also request data from a server
            // and then create objects from that data.
            m_parts.add(new StoreItem("UFO", 0, 10000, false));
            m_parts.add(new StoreItem("Jet Pack", 1, 30000, false));
            m_parts.add(new StoreItem("Fighter Jet", 2, 50000, false));
            m_parts.add(new StoreItem("Rocket", 3, 100000, false));
            m_parts.add(new StoreItem("Space Party Bus", 4, 250000, false));
            m_parts.add(new StoreItem("Space Shuttle", 5, 500000, false));
            m_parts.add(new StoreItem("Space Camel", 6, 1000000, false));


            m_adapter = new StoreItemAdapter(getActivity(), R.layout.store_item, m_parts);

            // display the list.
            setListAdapter(m_adapter);
        }
    };
}
