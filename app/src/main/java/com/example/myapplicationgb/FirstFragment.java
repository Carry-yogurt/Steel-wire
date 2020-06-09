package com.example.myapplicationgb;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    private String context;
    private TextView mTextView;

    private List<CostBean> mCostBeanList ;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter mAdapter;
    private ImageView alldelete;



    public FirstFragment(String context) {
        this.context = context;
    }

    public FirstFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mingxi, container, false);

        mDatabaseHelper = new DatabaseHelper(getActivity());
        mCostBeanList = new ArrayList<>();
        final ListView costList = view.findViewById(R.id.listview);
        //数据来源
        initCostData();

        mAdapter = new CostListAdapter(getActivity(), mCostBeanList);
        costList.setAdapter(mAdapter);

        ///单击每一个item实现删除功能
        costList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("删除");
                builder.setMessage("是否删除这笔账单?");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.deleteOne(mCostBeanList.get(position));
                        mCostBeanList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.create().show();
            }
        });

        //全部删除
       ImageView alldelete=view.findViewById(R.id.alldelete);
        //alldelete.setAdapter(mAdapter);
        alldelete.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View view) {
                CostBean costBean = new CostBean();
                mDatabaseHelper.deleteAllData();
                costList.setAdapter(null);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }




    //Cursor cursor= mDatabaseHelper.getAllCostData();
    private void initCostData() {
        Cursor cursor = mDatabaseHelper.getAllCostData();
        if (cursor != null){
            while (cursor.moveToNext()){
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }


}
