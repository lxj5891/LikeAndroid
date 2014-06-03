package com.starwall.like.app;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link FoodTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FoodTabFragment extends Fragment {




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodTabFragment newInstance(String param1, String param2) {
        FoodTabFragment fragment = new FoodTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FoodTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food_fragment, container, false);

        GridView gridView = (GridView)rootView.findViewById(R.id.foodGridView);

        ArrayList<HashMap<String, Object>>  foods = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < 20; i++){
            HashMap<String, Object> desk = new HashMap<String, Object>();
            desk.put("image", R.drawable.dinner);
            desk.put("title", "菜品" + i);
            foods.add(desk);
        }

        SimpleAdapter ad = new SimpleAdapter(getActivity(), foods, R.layout.food_item,
                new String[]{"image","title"},new int[]{R.id.foodImage,R.id.foodTitle});
        gridView.setAdapter(ad);




        gridView.setOnItemClickListener(new ItemClickListener());


        return rootView;
    }


    public class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setCancelable(true);

            LayoutInflater inflater = LayoutInflater.from(getActivity());

            View dialogView = inflater.inflate(R.layout.order_dialog,null);

            dialogBuilder.setView(dialogView);

            dialogBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            dialogBuilder.show();
        }
    }
}
