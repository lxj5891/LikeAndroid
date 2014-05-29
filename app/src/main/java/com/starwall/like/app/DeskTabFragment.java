package com.starwall.like.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DeskTabFragment extends Fragment {

    AlertDialog.Builder dialogBuilder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView)rootView.findViewById(R.id.deskGridView);

        ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < 20; i++){
            HashMap<String, Object> desk = new HashMap<String, Object>();
            desk.put("image", R.drawable.table);
            desk.put("text", "No." + i);
            listMap.add(desk);
        }

        SimpleAdapter tableItems = new SimpleAdapter(getActivity(), listMap, R.layout.desk_item,
                new String[]{"image","text"},new int[]{R.id.deskItemImage,R.id.deskItemText});

        gridView.setAdapter(tableItems);

        dialogBuilder = new AlertDialog.Builder(getActivity());

        gridView.setOnItemClickListener(new ItemClickListener());

        return rootView;
    }

    public class ItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            dialogBuilder.setTitle("操作");

            dialogBuilder.setCancelable(true);

            if (i<5){
                dialogBuilder.setPositiveButton("清台",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(DeskTabFragment.class.getName(),i+"");
                    }
                });
            }else {
                dialogBuilder.setPositiveButton("开台",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(DeskTabFragment.class.getName(),i+"");
                    }
                });
            }

            dialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.e(DeskTabFragment.class.getName(),i+"") ;
                }
            });

            dialogBuilder.show();

        }
    }

}
