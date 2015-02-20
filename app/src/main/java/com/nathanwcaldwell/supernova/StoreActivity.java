package com.nathanwcaldwell.supernova;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class StoreActivity extends ActionBarActivity {

    // declare class variables
    private ArrayList<StoreItem> m_parts = new ArrayList<StoreItem>();
    private Runnable viewParts;
    private StoreItemAdapter m_adapter;

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Ship","Time","Worp"};
    int Numboftabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        // instantiate our ItemAdapter class
        m_adapter = new StoreItemAdapter(this, R.layout.store_item, m_parts);
       // setListAdapter(m_adapter);

        // here we are defining our runnable thread.
        viewParts = new Runnable(){
            public void run(){
                handler.sendEmptyMessage(0);
            }
        };

        // here we call the thread we just defined - it is sent to the handler below.
        Thread thread =  new Thread(null, viewParts, "StoreItemThread");
        thread.start();
    }

    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            // create some objects
            // here is where you could also request data from a server
            // and then create objects from that data.
            m_parts.add(new StoreItem("Bronze Shield", 0, 10000));
            m_parts.add(new StoreItem("Silver Shield", 1, 30000));
            m_parts.add(new StoreItem("Gold Shield", 2, 50000));
            m_parts.add(new StoreItem("Platinum Shield", 3, 100000));
            m_parts.add(new StoreItem("Force Field", 4, 250000));
            m_parts.add(new StoreItem("Brick Wall", 5, 1000000));

            m_adapter = new StoreItemAdapter(StoreActivity.this, R.layout.store_item, m_parts);

            // display the list.
            //setListAdapter(m_adapter);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToMainActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToGetMoreCoinsActivity(View v) {
        Intent intent = new Intent(this, GetMoreCoinsActivity.class);
        startActivity(intent);
    }
}