package com.starwall.like.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import com.starwall.like.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kita on 14-5-27.
 */
public class OrderTableTabFragment extends Fragment {


    public enum FragmentType{
        ORDER_FOOD,
        SUBMIT_ORDER
    }

    private FragmentType fragmentType;

    public OrderTableTabFragment(FragmentType type) {
        fragmentType = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView)rootView.findViewById(R.id.deskGridView);

        ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < 3; i++){
            HashMap<String, Object> desk = new HashMap<String, Object>();
            desk.put("image", R.drawable.table);
            desk.put("text", "No." + i);
            listMap.add(desk);
        }

        SimpleAdapter tableItems = new SimpleAdapter(getActivity(), listMap, R.layout.desk_item,
                new String[]{"image","text"},new int[]{R.id.deskItemImage,R.id.deskItemText});

        gridView.setAdapter(tableItems);

        gridView.setOnItemClickListener(new ItemClickListener());

        return rootView;
    }

    public class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();

            switch (fragmentType){
                case SUBMIT_ORDER:
                    intent.setClass(getActivity(),SubmitOrderActivity.class);
                    break;

                default:
                    intent.setClass(getActivity(),OrderFoodActivity.class);
                    break;
            }


            startActivity(intent);
        }
    }
}
