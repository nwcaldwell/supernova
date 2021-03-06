package com.nathanwcaldwell.supernova;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Tab2 extends ListFragment {

    // declare class variables
    private ArrayList<StoreItem> m_parts = new ArrayList<StoreItem>();
    private Runnable viewParts;
    private ShieldItemAdapter m_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab, container, false);

        // here we are defining our runnable thread.
        viewParts = new Runnable() {
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        // here we call the thread we just defined - it is sent to the handler below.
        Thread thread = new Thread(null, viewParts, "StoreItemThread");
        thread.start();

        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // create some objects
            // here is where you could also request data from a server
            // and then create objects from that data.
            m_parts.add(new StoreItem("Bronze Shield", 0, 10000));
            m_parts.add(new StoreItem("Silver Shield", 1, 30000));
            m_parts.add(new StoreItem("Gold Shield", 2, 50000));
            m_parts.add(new StoreItem("Platinum Shield", 3, 100000));
            m_parts.add(new StoreItem("Force Field", 4, 250000));
            m_parts.add(new StoreItem("Brick Wall", 5, 1000000));

            m_adapter = new ShieldItemAdapter(getActivity(), R.layout.store_item, m_parts);

//            display the list.
            setListAdapter(m_adapter);
        }
    };
}