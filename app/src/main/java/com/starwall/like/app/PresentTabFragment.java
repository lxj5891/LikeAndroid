package com.starwall.like.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kita on 14-5-28.
 */
public class PresentTabFragment extends Fragment{
    ListView listView;
    PresentFoodAdapter adapter;
    ArrayList<HashMap<String, Object>> foods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.present_fragment, container, false);

        listView = (ListView)rootView.findViewById(R.id.presentListView);

        loadData();

        adapter = new PresentFoodAdapter(getActivity(), foods, R.layout.present_food_item,
                new String[]{"image", "title", "orderTime", "tableTitle"},
                new int[]{R.id.foodImage,R.id.foodTitle, R.id.orderTime, R.id.tableTitle});

        listView.setAdapter(adapter);

        return rootView;
    }

    private void loadData(){
        foods = new ArrayList<HashMap<String, Object>>();

        String data = new SimpleDateFormat("HH:mm").format(new Date());

        for (int i = 0; i < 10; i++){
            HashMap<String, Object> desk = new HashMap<String, Object>();
            desk.put("image", R.drawable.dinner);
            desk.put("title", "菜品" + i);
            desk.put("orderTime", data);
            desk.put("tableTitle", i%3 + "号桌");
            foods.add(desk);
        }
    }


    public class PresentFoodAdapter extends SimpleAdapter{

        public PresentFoodAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            Button dishUp = (Button)view.findViewById(R.id.dishUpButton);

            dishUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position<foods.size()){
                        foods.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });


            return view;
        }
    }
}
