package com.starwall.like.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import com.starwall.like.AppContext;
import com.starwall.like.AppException;
import com.starwall.like.R;
import com.starwall.like.api.DeskModule;
import com.starwall.like.bean.Desk;
import com.starwall.like.bean.DeskList;

import java.util.ArrayList;
import java.util.HashMap;
import android.os.Handler;

public class DeskTabFragment extends Fragment {


    AlertDialog.Builder dialogBuilder;

    private AppContext mContext;
    private Handler mHandler;
    private DeskList deskList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initData(rootView);
        dialogBuilder = new AlertDialog.Builder(getActivity());
        return rootView;
    }

    private void initData(final View rootView) {

        mHandler = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    GridView gridView = (GridView) rootView.findViewById(R.id.deskGridView);

                    ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
                    ArrayList<Desk> items = deskList.getItems();

                    for (int i = 0; i < items.size(); i++) {
                        Desk desk = items.get(i);

                        HashMap<String, Object> deskHash = new HashMap<String, Object>();
                        deskHash.put("image", R.drawable.table);
                        deskHash.put("text", desk.getName());
                        listMap.add(deskHash);
                    }

                    SimpleAdapter tableItems = new SimpleAdapter(getActivity(), listMap, R.layout.desk_item,
                            new String[]{"image", "text"}, new int[]{R.id.deskItemImage, R.id.deskItemText});

                    gridView.setAdapter(tableItems);
                    gridView.setOnItemClickListener(new ItemClickListener());
                }
            }
        };

        new Thread(){
            @Override
            public void run() {

                Message msg = new Message();

                try {
                    deskList = DeskModule.getDeskList(mContext);
                    msg.what = 1;
                } catch (AppException e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(msg);
            }
        }.start();
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

    public void setContext(AppContext mContext) {
        this.mContext = mContext;
    }
}
