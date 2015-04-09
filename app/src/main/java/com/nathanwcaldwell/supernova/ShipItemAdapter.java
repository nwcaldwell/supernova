//package com.nathanwcaldwell.supernova;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class ShipItemAdapter extends ArrayAdapter<StoreItem> {
//    // declaring our ArrayList of items
//    private ArrayList<StoreItem> objects;
//
//    private static final int TYPE_PURCHASED = 0;
//    private static final int TYPE_ITEM = 1;
//    private static final int TYPE_LOCKED = 2;
//    private static final int TYPE_MAX_COUNT = 3;
//
//    Context context = getContext();
//
//    String spackage = "com.nathanwcaldwell.supernova";
//    private SharedPreferences prefs = context.getSharedPreferences(spackage, context.MODE_PRIVATE);
//
//    /* here we must override the constructor for ArrayAdapter
//    * the only variable we care about now is ArrayList<Item> objects,
//    * because it is the list of objects we want to display.
//    */
//    public ShipItemAdapter(Context context, int textViewResourceId, ArrayList<StoreItem> objects) {
//        super(context, textViewResourceId, objects);
//        this.objects = objects;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if () {
//            return TYPE_PURCHASED;
//        }
//
//        else if () {
//            return TYPE_ITEM;
//        }
//
//        return TYPE_LOCKED;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return TYPE_MAX_COUNT;
//    }
//
//    /*
//     * we are overriding the getView method here - this is what defines how each
//     * list item will look.
//     */
//    public View getView(int position, View convertView, ViewGroup parent){
//
//        // assign the view we are converting to a local variable
//        View v = convertView;
//
//        // first check to see if the view is null. if so, we have to inflate it.
//        // to inflate it basically means to render, or show, the view.
//        int type = getItemViewType(position);
//
//        if (v == null) {
//            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            v = inflater.inflate(R.layout.store_item, null);
//        }
//
//		/*
//		 * Recall that the variable position is sent in as an argument to this method.
//		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
//		 * iterates through the list we sent it)
//		 *
//		 * Therefore, i refers to the current Item object.
//		 */
//        StoreItem i = objects.get(position);
//
//        if (i != null) {
//
//            // This is how you obtain a reference to the TextViews.
//            // These TextViews are created in the XML files we defined.
//
//            final TextView name = (TextView) v.findViewById(R.id.name);
//            final TextView upgradeNumber = (TextView) v.findViewById(R.id.upgradeNumber);
//            final TextView price = (TextView) v.findViewById(R.id.price);
//            Button upgrade_button = (Button) v.findViewById(R.id.upgrade_button);
//
//            upgrade_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int currentUpgrade = prefs.getInt("shipUpgrade", 0);
//                    int cost = Integer.parseInt(price.getText().toString());
//                    int coinsAvailable = prefs.getInt("coinsAvailable", 0);
//
//                    if (Integer.parseInt(upgradeNumber.getText().toString()) == currentUpgrade+1 && coinsAvailable >= cost) {
//                        prefs.edit().putInt("shipUpgrade", currentUpgrade+1).commit();
//                        prefs.edit().putInt("coinsAvailable", coinsAvailable-cost).commit();
//                    }
//                }
//            });
//
//            // check to see if each individual textview is null.
//            // if not, assign some text!
//            if (name != null){
//                name.setText(i.getName());
//            }
//            if (upgradeNumber != null){
//                upgradeNumber.setText(String.valueOf(i.getUpgradeNumber()));
//            }
//            if (price != null){
//                price.setText(String.valueOf(i.getPrice()));
//            }
//        }
//
//        // the view must be returned to our activity
//        return v;
//
//    }
//}
