package com.starwall.like.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.starwall.like.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kita on 14-5-29.
 */
public class SubmitOrderActivity extends Activity {

    private static final int MENU_ITEM_COUNTER = Menu.FIRST;
    FoodAdapter adapter;
    ArrayList<HashMap<String, Object>> foods;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.submit_order_activity);

        ListView listView = (ListView)findViewById(R.id.orderFoodListView);

        foods = new ArrayList<HashMap<String, Object>>();

        loadDate();

        adapter = new FoodAdapter(this,foods,R.layout.submit_food_item,
                new String[]{"foodImage","foodTitle","foodInfo","foodTotal"},
                new int[]{R.id.foodImage,R.id.foodTitle,R.id.foodInfo,R.id.foodTotal});

        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,MENU_ITEM_COUNTER,0,"下单").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0,MENU_ITEM_COUNTER+1,0,"结账").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    private void loadDate(){
        for (int i = 0; i < 10; i++){
            HashMap<String, Object> desk = new HashMap<String, Object>();
            desk.put("foodImage", R.drawable.dinner);
            desk.put("foodTitle", "菜品" + i);
            desk.put("foodInfo", "价格:" + i + "元");
            desk.put("foodTotal", "1份:" + i + "元");
            foods.add(desk);
        }
    }

    public class FoodAdapter extends SimpleAdapter{

        public FoodAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            Button cancel = (Button)view.findViewById(R.id.dishCancelButton);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position < foods.size()){
                        foods.remove(position);
                        notifyDataSetChanged();
                    }

                }
            });

            return view;
        }
    }

}
