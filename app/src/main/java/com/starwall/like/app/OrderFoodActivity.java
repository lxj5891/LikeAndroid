package com.starwall.like.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.starwall.like.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kita on 14-5-27.
 */
public class OrderFoodActivity extends Activity implements SearchView.OnQueryTextListener{

    ListView listView;
    OrderFoodAdapter foodAdapter;
    ArrayList<HashMap<String, Object>> foods;
    ArrayList<HashMap<String, Object>> foodsForSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.order_food_activity);

        listView = (ListView)findViewById(R.id.orderFoodListView);
        listView.setTextFilterEnabled(true);

        foodsForSearch = new ArrayList<HashMap<String, Object>>();
        foods = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < 20; i++){
            HashMap<String, Object> desk = new HashMap<String, Object>();
            desk.put("image", R.drawable.dinner);
            desk.put("title", "菜品" + i);
            desk.put("info", "价格:" + i + "元");
            foodsForSearch.add(desk);
            foods.add(desk);
        }

        foodAdapter = new OrderFoodAdapter(this, foods, R.layout.order_food_item,
                new String[]{"image","title", "info"},new int[]{R.id.foodImage,R.id.foodTitle, R.id.foodInfo});

        listView.setAdapter(foodAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_food_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.food_search);
        SearchView searchView  = (SearchView)searchItem.getActionView();
        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        switch (item.getItemId()){
//            case android.R.id.home:
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        foodAdapter.getFilter().filter(s);
        return false;
    }

    public class OrderFoodAdapter extends SimpleAdapter{

        public OrderFoodAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            final EditText total = (EditText)view.findViewById(R.id.foodTotal);

            Button minusButton = (Button)view.findViewById(R.id.minusButton);
            Button plusButton = (Button)view.findViewById(R.id.plusButton);

            Button order = (Button)view.findViewById(R.id.addFoodButton);

            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int t = Integer.parseInt(total.getText().toString());

                    if (t > 0){
                        t--;
                        total.setText(t+"");
                    }

                }
            });

            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int t = Integer.parseInt(total.getText().toString());
                    t++;
                    total.setText(t+"");
                }
            });

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap<String, Object> item = (HashMap<String, Object>)getItem(position);

                    Toast toast = Toast.makeText(getApplicationContext(), item.get("title") + " 点了 " + total.getText().toString() + "份", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    Log.w("filter Called",charSequence.toString());
                    FilterResults results = new FilterResults();

                    ArrayList<HashMap<String, Object>> filteredList = new ArrayList<HashMap<String, Object>>();

                    if (charSequence == null || charSequence.length() == 0){
                        results.count = foodsForSearch.size();
                        results.values = foodsForSearch;
                    } else {
                        String sequence = charSequence.toString().toLowerCase();
//                        Pattern pattern = Pattern.compile(sequence);

                        for (int i = 0; i < foodsForSearch.size(); i++){
                            HashMap<String, Object> item = foodsForSearch.get(i);
                            String title = (String)item.get("title");
//                            Matcher matcher = pattern.matcher(title);

                            if (title.toLowerCase().contains(sequence)){
                                filteredList.add(item);
                            }

//                            if (matcher.find()){
//                                filteredList.add(item);
//                            }
                        }
                        results.count = filteredList.size();
                        results.values = filteredList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    Log.w("filtered",String.valueOf(filterResults.count));
                    foods.clear();
                    foods.addAll((java.util.Collection<? extends HashMap<String, Object>>) filterResults.values);

                    notifyDataSetChanged();
                }
            };

            return filter;
        }
    }
}
